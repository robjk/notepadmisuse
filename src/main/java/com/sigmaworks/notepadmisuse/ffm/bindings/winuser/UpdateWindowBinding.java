package com.sigmaworks.notepadmisuse.ffm.bindings.winuser;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static com.sigmaworks.notepadmisuse.ffm.Kernel32Common.*;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class UpdateWindowBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            C_INT,
            C_POINTER
    );

    public static final MemorySegment ADDR = findOrThrow("UpdateWindow");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);

    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * BOOL UpdateWindow(HWND hWnd)
     *}
     */
    public static FunctionDescriptor UpdateWindow$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * BOOL UpdateWindow(HWND hWnd)
     *}
     */
    public static MethodHandle UpdateWindow$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * BOOL UpdateWindow(HWND hWnd)
     *}
     */
    public static MemorySegment UpdateWindow$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * BOOL UpdateWindow(HWND hWnd)
     *}
     */
    public static int UpdateWindow(MemorySegment hWnd) {
        var mh$ = HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("UpdateWindow", hWnd);
            }
            return (int) mh$.invokeExact(hWnd);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}
