package com.sigmaworks.notepadmisuse.ffm;

import com.sigmaworks.notepadmisuse.ffm.bindings.errhandlingapi.GetLastErrorBinding;
import com.sigmaworks.notepadmisuse.ffm.bindings.handleapi.CloseHandleBinding;
import com.sigmaworks.notepadmisuse.ffm.bindings.memoryapi.ReadProcessMemoryBinding;
import com.sigmaworks.notepadmisuse.ffm.bindings.memoryapi.VirtualProtectExBinding;
import com.sigmaworks.notepadmisuse.ffm.bindings.memoryapi.VirtualQueryExBinding;
import com.sigmaworks.notepadmisuse.ffm.bindings.memoryapi.WriteProcessMemoryBinding;
import com.sigmaworks.notepadmisuse.ffm.bindings.msvcrt.memcmpBinding;
import com.sigmaworks.notepadmisuse.ffm.bindings.processthreadsapi.OpenProcessBinding;
import com.sigmaworks.notepadmisuse.ffm.bindings.psapi.*;
import com.sigmaworks.notepadmisuse.ffm.bindings.sysinfo.GetSystemInfoBinding;
import com.sigmaworks.notepadmisuse.ffm.bindings.sysinfo.GetVersionExABinding;
import com.sigmaworks.notepadmisuse.ffm.bindings.sysinfo.SystemInfoBinding;
import com.sigmaworks.notepadmisuse.ffm.bindings.winnt.MemoryBasicInformationBinding;
import com.sigmaworks.notepadmisuse.ffm.bindings.winnt.OsVersionInfoExABinding;
import com.sigmaworks.notepadmisuse.ffm.bindings.winuser.*;
import com.sigmaworks.notepadmisuse.ffm.mappings.MemoryBasicInformationMapper.MemoryBasicInformationRecord;
import com.sigmaworks.notepadmisuse.ffm.mappings.ModuleInfoMapper;
import com.sigmaworks.notepadmisuse.ffm.mappings.OsVersionInfoExAMapper;
import com.sigmaworks.notepadmisuse.ffm.mappings.RectMapper;
import com.sigmaworks.notepadmisuse.ffm.mappings.RectMapper.RectRecord;
import com.sigmaworks.notepadmisuse.ffm.mappings.SystemInfoMapper;
import com.sigmaworks.notepadmisuse.ffm.mappings.SystemInfoMapper.SystemInfoRecord;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import static com.sigmaworks.notepadmisuse.ffm.mappings.MemoryBasicInformationMapper.MEMORY_BASIC_INFORMATION_MAPPER;

/**
 * A collection of static methods for interacting with FFM exposed APIs and structs.  Makes for easier to read code when
 * used by consumers vs those produced by vanilla jextract.
 * <p>
 * Were this for a real project the javadoc would be completed, but it's not, and it's not.
 * <p>
 * todo each method obtaining its own Arena is <i>probably</i> a poor design decision, not a problem in this use-case
 * <p>
 * <a href="https://learn.microsoft.com/en-us/windows/win32/api/memoryapi/nf-memoryapi-readprocessmemory">Windows API documentation</a>
 */
public class WinOS {

    private static void throwOnError(int returnCode) {
        throwOnError(returnCode, "");
    }

    private static void throwOnError(int returnCode, String message) {
        if (returnCode == 0) {
            throw new RuntimeException("Unexpected error during system call, lastError=%d %s".formatted(GetLastError(), message));
        }
    }

    public static int GetLastError() {
        return GetLastErrorBinding.GetLastError();
    }

    public static OsVersionInfoExAMapper.OsVersionInfoExARecord GetVersionExA() {
        try (Arena offHeap = Arena.ofConfined()) {

            MemorySegment osVersionExA = offHeap.allocate(OsVersionInfoExABinding.layout());
            OsVersionInfoExABinding.dwOSVersionInfoSize(osVersionExA, (int) OsVersionInfoExABinding.sizeof());

            throwOnError(GetVersionExABinding.GetVersionExA(osVersionExA));

            return OsVersionInfoExAMapper.OS_VERSION_INFO_EX_A_MAPPER.get(osVersionExA);
        }
    }

    public static SystemInfoRecord GetSystemInfo() {
        try (Arena offHeap = Arena.ofConfined()) {
            MemorySegment systemInfo = offHeap.allocate(SystemInfoBinding.layout());
            GetSystemInfoBinding.GetSystemInfo(systemInfo);
            return SystemInfoMapper.SYSTEM_INFO_MAPPER.get(systemInfo);
        }
    }

    public static MemoryBasicInformationRecord VirtualQueryEx(MemorySegment hProc, long baseAddress) {
        try (Arena offHeap = Arena.ofConfined()) {
            MemorySegment baseAddr = MemorySegment.ofAddress(baseAddress);
            MemorySegment basicInfo = offHeap.allocate(MemoryBasicInformationBinding.layout());
            long bytesRead = VirtualQueryExBinding.VirtualQueryEx(hProc, baseAddr, basicInfo, basicInfo.byteSize());
            throwOnError((int) bytesRead);
            return MEMORY_BASIC_INFORMATION_MAPPER.get(basicInfo);
        }
    }

    // use this to change page protection attributes
    public static int VirtualProtectEx(MemorySegment hProc, long baseAddress, long regionSize, int protectionFlags) {
        try (Arena offHeap = Arena.ofConfined()) {
            MemorySegment previousFlags = offHeap.allocate(Kernel32Common.C_LONG);
            int returnCode = VirtualProtectExBinding.VirtualProtectEx(hProc,
                    MemorySegment.ofAddress(baseAddress),
                    regionSize,
                    protectionFlags,
                    previousFlags);
            int error = GetLastError();
//            throwOnError(returnCode);
            return previousFlags.get(Kernel32Common.C_INT, 0);
        }
    }

    public static int[] K32EnumProcesses() {
        try (Arena offHeap = Arena.ofConfined()) {
            // get list of process
            MemorySegment pids = offHeap.allocate(Kernel32Common.C_CHAR, 2_048);
            MemorySegment arrayBytesNeeded = offHeap.allocate(Kernel32Common.C_INT);

            throwOnError(K32EnumProcessesBinding.K32EnumProcesses(pids, (int) pids.byteSize(), arrayBytesNeeded));

            int pidArrayLength = arrayBytesNeeded.get(Kernel32Common.C_INT, 0);
            return pids.asSlice(0L, pidArrayLength).toArray(Kernel32Common.C_INT);
        }
    }

    public static long[] K32EnumProcessModules(MemorySegment hProc, boolean ignoreFailure) {
        try (Arena offHeap = Arena.ofConfined()) {
            var hModule = offHeap.allocate(Kernel32Common.HMODULE, 1_000);
            var bytesReturned = offHeap.allocate(Kernel32Common.C_INT);
            if (ignoreFailure) {
                K32EnumProcessModulesBinding.K32EnumProcessModules(hProc, hModule, (int) hModule.byteSize(), bytesReturned);
            } else {
                throwOnError(K32EnumProcessModulesBinding.K32EnumProcessModules(hProc, hModule, (int) hModule.byteSize(), bytesReturned), "bytesReturned=" + bytesReturned.get(Kernel32Common.C_INT, 0));
            }

            int moduleHandlesArrayLength = bytesReturned.get(Kernel32Common.C_INT, 0);
            // if bytesReturned is truncaed by hModule.byteSize then this can fail
            return hModule.asSlice(0L, moduleHandlesArrayLength).toArray(Kernel32Common.C_LONG_LONG);
        }
    }

    public static String K32GetModuleBaseNameA(MemorySegment hProc, long hModule) {
        try (Arena offHeap = Arena.ofConfined()) {
            MemorySegment hModulePtr = MemorySegment.ofAddress(hModule);

            var szProcessName = offHeap.allocate(Kernel32Common.C_CHAR, 1_024);
            int baseNameStringLength = K32GetModuleBaseNameABinding.K32GetModuleBaseNameA(hProc, hModulePtr, szProcessName, (int) szProcessName.byteSize());
            throwOnError(baseNameStringLength);

            szProcessName = szProcessName.reinterpret(baseNameStringLength);
            return new String(szProcessName.toArray(Kernel32Common.C_CHAR));
        }
    }

    public static ModuleInfoMapper.ModuleInfoRecord K32GetModuleInformation(MemorySegment hProc, long hModule) {
        try (Arena offHeap = Arena.ofConfined()) {

            MemorySegment hModulePtr = MemorySegment.ofAddress(hModule);
            MemorySegment moduleInfo = offHeap.allocate(ModuleInfoBinding.layout());

            throwOnError(K32GetModuleInformationBinding.K32GetModuleInformation(hProc,
                    hModulePtr,
                    moduleInfo,
                    (int) moduleInfo.byteSize()));

            return ModuleInfoMapper.MODULE_INFO_MAPPER.get(moduleInfo);
        }
    }

    /**
     * Open the supplied pid with the desired access properties
     * <p>
     * Caller is responsible for calling close on the returned handle when finished
     *
     * @param pid           pid to be opened
     * @param desiredAccess access rights desired on the process being opened
     * @return MemorySegment containing a process HANDLE, MemorySegment.NULL if process could not be opened
     * @see #CloseHandle(MemorySegment)
     *
     * <a href="https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/nf-processthreadsapi-openprocess">OpenProcess</a>
     * <a href="https://learn.microsoft.com/en-us/windows/win32/procthread/process-security-and-access-rights">Access Rights</a>
     */
    public static MemorySegment OpenProcess(int pid, int desiredAccess) {
        return OpenProcessBinding.OpenProcess(desiredAccess, Kernel32Common.FALSE(), pid);
    }

    public static MemorySegment ReadProcessMemory(Arena offHeap, MemorySegment hProc, long src, long destSize) {
        MemorySegment srcAddress = MemorySegment.ofAddress(src);
        MemorySegment destBuffer = offHeap.allocate(destSize);
        MemorySegment bytesRead = offHeap.allocate(Kernel32Common.C_INT);

        int returnValue = ReadProcessMemoryBinding.ReadProcessMemory(hProc,
                srcAddress,
                destBuffer,
                destBuffer.byteSize(),
                bytesRead);

        int bytesReadValue = bytesRead.get(Kernel32Common.C_INT, 0);

        // The function fails (==0) if the requested read operation crosses into an area of the process that is inaccessible.
        if (returnValue == 0) {
            int lastError = GetLastError();
            System.out.printf("ReadProcessMemory@%x failure lastError=%d, bytesRead=%d%n", src, lastError, bytesReadValue);
        }

        return destBuffer.asSlice(0, bytesReadValue);
    }

    public static byte[] ReadProcessMemory(MemorySegment hProc, long src, long destSize) {
        try (Arena offHeap = Arena.ofConfined()) {
            MemorySegment destBuffer = ReadProcessMemory(offHeap, hProc, src, destSize);
            return destBuffer.toArray(ValueLayout.JAVA_BYTE);
        }
    }

    public static long WriteProcessMemory(MemorySegment hProc,
                                          MemorySegment destination,
                                          MemorySegment source,
                                          long size) {
        try (Arena offHeap = Arena.ofConfined()) {
            MemorySegment bytesWritten = offHeap.allocate(Kernel32Common.C_LONG);

            throwOnError(WriteProcessMemoryBinding.WriteProcessMemory(hProc,
                    destination,
                    source,
                    size,
                    bytesWritten));
            return bytesWritten.get(Kernel32Common.C_LONG, 0);
        }
    }

    public static long WriteProcessMemory(MemorySegment hProc, long address, byte[] srcBuffer, long size) {
        try (Arena offHeap = Arena.ofConfined()) {
            MemorySegment addressPointer = MemorySegment.ofAddress(address);
            MemorySegment src = offHeap.allocate(srcBuffer.length);
            MemorySegment.copy(MemorySegment.ofArray(srcBuffer), 0, src, 0, srcBuffer.length);

            MemorySegment bytesWritten = offHeap.allocate(Kernel32Common.C_LONG);

            throwOnError(WriteProcessMemoryBinding.WriteProcessMemory(hProc,
                    addressPointer,
                    src,
                    size,
                    bytesWritten));
            return bytesWritten.get(Kernel32Common.C_LONG, 0);
        }
    }

    public static void CloseHandle(MemorySegment hProc) {
        throwOnError(CloseHandleBinding.CloseHandle(hProc));
    }

    // std::memcmp flavours
    public static boolean memcmp(long lpBuffer1, long lpBuffer2, long count) {
        return memcmp(MemorySegment.ofAddress(lpBuffer1), MemorySegment.ofAddress(lpBuffer2), count);
    }

    public static boolean memcmp(MemorySegment buffer1, MemorySegment buffer2, long count) {
        return memcmpBinding.memcmp(buffer1, buffer2, count) == 0;
    }

    public static long FindWindowExA(Long parent, Long childAfter) {
        MemorySegment hWndParent = parent == null ? MemorySegment.NULL : MemorySegment.ofAddress(parent);
        MemorySegment hWndChildAfter = childAfter == null ? MemorySegment.NULL : MemorySegment.ofAddress(childAfter);

        MemorySegment hWnd = FindWindowExABinding.FindWindowExA(hWndParent, hWndChildAfter, MemorySegment.NULL, MemorySegment.NULL);
        if (hWnd == MemorySegment.NULL) {
            throwOnError(0, "FindWindowExA failed to return an hWnd");
        }
        return hWnd.address();
    }

    public static long GetWindowThreadProcessId(long hWnd) {
        try (Arena offHeap = Arena.ofConfined()) {
            MemorySegment lpdwProcessId = offHeap.allocate(Kernel32Common.C_LONG);
            throwOnError(GetWindowThreadProcessIdBinding.GetWindowThreadProcessId(MemorySegment.ofAddress(hWnd), lpdwProcessId));
            return lpdwProcessId.get(Kernel32Common.C_LONG, 0);
        }
    }

    public static RectRecord GetWindowRect(long hWnd) {
        try (Arena offHeap = Arena.ofConfined()) {
            MemorySegment rect = offHeap.allocate(RectBinding.layout());
            int returnCode = GetWindowRectBinding.GetWindowRect(MemorySegment.ofAddress(hWnd), rect);
            return RectMapper.RECT_MAPPER.get(rect);
        }
    }

    public static void InvalidateRect(long hWnd, RectRecord rect, boolean eraseBackground) {
        int bErase = eraseBackground ? 1 : 0;

        if (rect == null) {
            throwOnError(InvalidateRectBinding.InvalidateRect(MemorySegment.ofAddress(hWnd), MemorySegment.NULL, bErase));
        } else {
            try (Arena offHeap = Arena.ofConfined()) {
                MemorySegment rectMS = offHeap.allocate(RectBinding.layout());
                RectMapper.RECT_MAPPER.set(rectMS, rect);
                throwOnError(InvalidateRectBinding.InvalidateRect(MemorySegment.ofAddress(hWnd), rectMS, bErase));
            }
        }
    }

    public static void UpdateWindow(long hWnd) {
        throwOnError(UpdateWindowBinding.UpdateWindow(MemorySegment.ofAddress(hWnd)));
    }

    public static void SetFocus(long hWnd) {
        MemorySegment memorySegment = SetFocusBinding.SetFocus(MemorySegment.ofAddress(hWnd));
    }

    public static int ShowWindow(long hWnd, int cmdShow) {
        return ShowWindowBinding.ShowWindow(MemorySegment.ofAddress(hWnd), cmdShow);
    }
}
