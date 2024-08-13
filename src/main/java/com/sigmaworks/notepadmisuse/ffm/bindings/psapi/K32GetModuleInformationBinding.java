package com.sigmaworks.notepadmisuse.ffm.bindings.psapi;

import com.sigmaworks.notepadmisuse.ffm.Kernel32Common;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

/**
 * {@snippet lang = c:
 * typedef struct _MODULEINFO {
 *     LPVOID lpBaseOfDll;
 *     DWORD SizeOfImage;
 *     LPVOID EntryPoint;
 * } *LPMODULEINFO
 *}
 */

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class K32GetModuleInformationBinding {
    public static final AddressLayout LPMODULEINFO = Kernel32Common.C_POINTER;

    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            Kernel32Common.C_INT,
            Kernel32Common.C_POINTER,
            Kernel32Common.C_POINTER,
            Kernel32Common.C_POINTER,
            Kernel32Common.C_LONG
    );

    public static final MemorySegment ADDR = Kernel32Common.findOrThrow("K32GetModuleInformation");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);


    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * BOOL K32GetModuleInformation(HANDLE hProcess, HMODULE hModule, LPMODULEINFO lpmodinfo, DWORD cb)
     *}
     */
    public static FunctionDescriptor K32GetModuleInformation$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * BOOL K32GetModuleInformation(HANDLE hProcess, HMODULE hModule, LPMODULEINFO lpmodinfo, DWORD cb)
     *}
     */
    public static MethodHandle K32GetModuleInformation$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * BOOL K32GetModuleInformation(HANDLE hProcess, HMODULE hModule, LPMODULEINFO lpmodinfo, DWORD cb)
     *}
     */
    public static MemorySegment K32GetModuleInformation$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * BOOL K32GetModuleInformation(HANDLE hProcess, HMODULE hModule, LPMODULEINFO lpmodinfo, DWORD cb)
     *}
     */
    public static int K32GetModuleInformation(MemorySegment hProcess, MemorySegment hModule, MemorySegment lpmodinfo, int cb) {
        var mh$ = HANDLE;
        try {
            if (Kernel32Common.TRACE_DOWNCALLS) {
                Kernel32Common.traceDowncall("K32GetModuleInformation", hProcess, hModule, lpmodinfo, cb);
            }
            return (int) mh$.invokeExact(hProcess, hModule, lpmodinfo, cb);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}
