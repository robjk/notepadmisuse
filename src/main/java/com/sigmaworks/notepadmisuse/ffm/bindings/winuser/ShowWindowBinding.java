package com.sigmaworks.notepadmisuse.ffm.bindings.winuser;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static com.sigmaworks.notepadmisuse.ffm.Kernel32Common.*;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class ShowWindowBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            C_INT,
            C_POINTER,
            C_INT
    );

    public static final MemorySegment ADDR = findOrThrow("ShowWindow");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);

    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * BOOL ShowWindow(HWND hWnd, int nCmdShow)
     *}
     */
    public static FunctionDescriptor ShowWindow$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * BOOL ShowWindow(HWND hWnd, int nCmdShow)
     *}
     */
    public static MethodHandle ShowWindow$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * BOOL ShowWindow(HWND hWnd, int nCmdShow)
     *}
     */
    public static MemorySegment ShowWindow$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * BOOL ShowWindow(HWND hWnd, int nCmdShow)
     *}
     */
    public static int ShowWindow(MemorySegment hWnd, int nCmdShow) {
        var mh$ = HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("ShowWindow", hWnd, nCmdShow);
            }
            return (int) mh$.invokeExact(hWnd, nCmdShow);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}
