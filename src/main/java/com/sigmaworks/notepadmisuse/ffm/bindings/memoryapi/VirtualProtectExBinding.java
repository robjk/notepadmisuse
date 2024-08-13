package com.sigmaworks.notepadmisuse.ffm.bindings.memoryapi;

import com.sigmaworks.notepadmisuse.ffm.Kernel32Common;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class VirtualProtectExBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            Kernel32Common.C_INT,
            Kernel32Common.C_POINTER,
            Kernel32Common.C_POINTER,
            Kernel32Common.C_LONG_LONG,
            Kernel32Common.C_LONG,
            Kernel32Common.C_POINTER
    );

    public static final MemorySegment ADDR = Kernel32Common.findOrThrow("VirtualProtectEx");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);


    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * BOOL VirtualProtectEx(HANDLE hProcess, LPVOID lpAddress, SIZE_T dwSize, DWORD flNewProtect, PDWORD lpflOldProtect)
     *}
     */
    public static FunctionDescriptor VirtualProtectEx$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * BOOL VirtualProtectEx(HANDLE hProcess, LPVOID lpAddress, SIZE_T dwSize, DWORD flNewProtect, PDWORD lpflOldProtect)
     *}
     */
    public static MethodHandle VirtualProtectEx$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * BOOL VirtualProtectEx(HANDLE hProcess, LPVOID lpAddress, SIZE_T dwSize, DWORD flNewProtect, PDWORD lpflOldProtect)*}
     */
    public static MemorySegment VirtualProtectEx$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * BOOL VirtualProtectEx(HANDLE hProcess, LPVOID lpAddress, SIZE_T dwSize, DWORD flNewProtect, PDWORD lpflOldProtect)
     *}
     */
    public static int VirtualProtectEx(MemorySegment hProcess, MemorySegment lpAddress, long dwSize, int flNewProtect, MemorySegment lpflOldProtect) {
        var mh$ = HANDLE;
        try {
            if (Kernel32Common.TRACE_DOWNCALLS) {
                Kernel32Common.traceDowncall("VirtualProtectEx", hProcess, lpAddress, dwSize, flNewProtect, lpflOldProtect);
            }
            return (int) mh$.invokeExact(hProcess, lpAddress, dwSize, flNewProtect, lpflOldProtect);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}
