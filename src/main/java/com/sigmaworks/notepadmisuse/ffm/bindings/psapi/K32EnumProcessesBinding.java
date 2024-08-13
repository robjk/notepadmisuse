package com.sigmaworks.notepadmisuse.ffm.bindings.psapi;


import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static com.sigmaworks.notepadmisuse.ffm.Kernel32Common.*;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class K32EnumProcessesBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            C_INT,
            C_POINTER,
            C_LONG,
            C_POINTER
    );

    public static final MemorySegment ADDR = findOrThrow("K32EnumProcesses");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);


    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * BOOL K32EnumProcesses(DWORD *lpidProcess, DWORD cb, LPDWORD lpcbNeeded)
     *}
     */
    public static FunctionDescriptor K32EnumProcesses$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * BOOL K32EnumProcesses(DWORD *lpidProcess, DWORD cb, LPDWORD lpcbNeeded)
     *}
     */
    public static MethodHandle K32EnumProcesses$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * BOOL K32EnumProcesses(DWORD *lpidProcess, DWORD cb, LPDWORD lpcbNeeded)*}
     */
    public static MemorySegment K32EnumProcesses$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * BOOL K32EnumProcesses(DWORD *lpidProcess, DWORD cb, LPDWORD lpcbNeeded)
     *}
     */
    public static int K32EnumProcesses(MemorySegment lpidProcess, int cb, MemorySegment lpcbNeeded) {
        var mh$ = HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("K32EnumProcesses", lpidProcess, cb, lpcbNeeded);
            }
            return (int) mh$.invokeExact(lpidProcess, cb, lpcbNeeded);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}
