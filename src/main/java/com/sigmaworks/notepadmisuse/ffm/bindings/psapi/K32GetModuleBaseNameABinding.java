package com.sigmaworks.notepadmisuse.ffm.bindings.psapi;

import com.sigmaworks.notepadmisuse.ffm.Kernel32Common;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class K32GetModuleBaseNameABinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            Kernel32Common.C_LONG,
            Kernel32Common.C_POINTER,
            Kernel32Common.C_POINTER,
            Kernel32Common.C_POINTER,
            Kernel32Common.C_LONG
    );

    public static final MemorySegment ADDR = Kernel32Common.findOrThrow("K32GetModuleBaseNameA");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);


    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * DWORD K32GetModuleBaseNameA(HANDLE hProcess, HMODULE hModule, LPSTR lpBaseName, DWORD nSize)
     * }
     */
    public static FunctionDescriptor K32GetModuleBaseNameA$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * DWORD K32GetModuleBaseNameA(HANDLE hProcess, HMODULE hModule, LPSTR lpBaseName, DWORD nSize)
     * }
     */
    public static MethodHandle K32GetModuleBaseNameA$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * DWORD K32GetModuleBaseNameA(HANDLE hProcess, HMODULE hModule, LPSTR lpBaseName, DWORD nSize)* }
     */
    public static MemorySegment K32GetModuleBaseNameA$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * DWORD K32GetModuleBaseNameA(HANDLE hProcess, HMODULE hModule, LPSTR lpBaseName, DWORD nSize)
     * }
     */
    public static int K32GetModuleBaseNameA(MemorySegment hProcess, MemorySegment hModule, MemorySegment lpBaseName, int nSize) {
        var mh$ = HANDLE;
        try {
            if (Kernel32Common.TRACE_DOWNCALLS) {
                Kernel32Common.traceDowncall("K32GetModuleBaseNameA", hProcess, hModule, lpBaseName, nSize);
            }
            return (int) mh$.invokeExact(hProcess, hModule, lpBaseName, nSize);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}