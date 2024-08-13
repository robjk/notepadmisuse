package com.sigmaworks.notepadmisuse.ffm.bindings.psapi;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static com.sigmaworks.notepadmisuse.ffm.Kernel32Common.*;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class K32EnumProcessModulesBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            C_INT,
            C_POINTER,
            C_POINTER,
            C_LONG,
            C_POINTER
    );

    public static final MemorySegment ADDR = findOrThrow("K32EnumProcessModules");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);


    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * BOOL K32EnumProcessModules(HANDLE hProcess, HMODULE *lphModule, DWORD cb, LPDWORD lpcbNeeded)
     *}
     */
    public static FunctionDescriptor K32EnumProcessModules$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * BOOL K32EnumProcessModules(HANDLE hProcess, HMODULE *lphModule, DWORD cb, LPDWORD lpcbNeeded)
     *}
     */
    public static MethodHandle K32EnumProcessModules$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * BOOL K32EnumProcessModules(HANDLE hProcess, HMODULE *lphModule, DWORD cb, LPDWORD lpcbNeeded)*}
     */
    public static MemorySegment K32EnumProcessModules$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * BOOL K32EnumProcessModules(HANDLE hProcess, HMODULE *lphModule, DWORD cb, LPDWORD lpcbNeeded)
     *}
     */
    public static int K32EnumProcessModules(MemorySegment hProcess, MemorySegment lphModule, int cb, MemorySegment lpcbNeeded) {
        var mh$ = K32EnumProcessModulesBinding.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("K32EnumProcessModules", hProcess, lphModule, cb, lpcbNeeded);
            }
            return (int) mh$.invokeExact(hProcess, lphModule, cb, lpcbNeeded);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}
