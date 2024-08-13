package com.sigmaworks.notepadmisuse.ffm.bindings.winuser;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static com.sigmaworks.notepadmisuse.ffm.Kernel32Common.*;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class InvalidateRectBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            C_INT,
            C_POINTER,
            C_POINTER,
            C_INT
    );

    public static final MemorySegment ADDR = findOrThrow("InvalidateRect");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);


    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * BOOL InvalidateRect(HWND hWnd, const RECT *lpRect, BOOL bErase)
     *}
     */
    public static FunctionDescriptor InvalidateRect$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * BOOL InvalidateRect(HWND hWnd, const RECT *lpRect, BOOL bErase)
     *}
     */
    public static MethodHandle InvalidateRect$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * BOOL InvalidateRect(HWND hWnd, const RECT *lpRect, BOOL bErase)
     *}
     */
    public static MemorySegment InvalidateRect$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * BOOL InvalidateRect(HWND hWnd, const RECT *lpRect, BOOL bErase)
     *}
     */
    public static int InvalidateRect(MemorySegment hWnd, MemorySegment lpRect, int bErase) {
        var mh$ = HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("InvalidateRect", hWnd, lpRect, bErase);
            }
            return (int) mh$.invokeExact(hWnd, lpRect, bErase);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}
