package com.sigmaworks.notepadmisuse.ffm.bindings.errhandlingapi;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static com.sigmaworks.notepadmisuse.ffm.Kernel32Common.*;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class GetLastErrorBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(C_LONG);

    public static final MemorySegment ADDR = findOrThrow("GetLastError");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);

    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * DWORD GetLastError()
     *}
     */
    public static FunctionDescriptor GetLastError$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * DWORD GetLastError()
     *}
     */
    public static MethodHandle GetLastError$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * DWORD GetLastError()
     *}
     */
    public static MemorySegment GetLastError$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * DWORD GetLastError()
     *}
     */
    public static int GetLastError() {
        var mh$ = HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("GetLastError");
            }
            return (int) mh$.invokeExact();
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}
