package com.sigmaworks.notepadmisuse.ffm.bindings.winuser;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static com.sigmaworks.notepadmisuse.ffm.Kernel32Common.*;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class GetWindowRectBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            C_INT,
            C_POINTER,
            C_POINTER
    );

    public static final MemorySegment ADDR = findOrThrow("GetWindowRect");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);

    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * BOOL GetWindowRect(HWND hWnd, LPRECT lpRect)
     *}
     */
    public static FunctionDescriptor GetWindowRect$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * BOOL GetWindowRect(HWND hWnd, LPRECT lpRect)
     *}
     */
    public static MethodHandle GetWindowRect$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * BOOL GetWindowRect(HWND hWnd, LPRECT lpRect)
     *}
     */
    public static MemorySegment GetWindowRect$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * BOOL GetWindowRect(HWND hWnd, LPRECT lpRect)
     *}
     */
    public static int GetWindowRect(MemorySegment hWnd, MemorySegment lpRect) {
        var mh$ = HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("GetWindowRect", hWnd, lpRect);
            }
            return (int) mh$.invokeExact(hWnd, lpRect);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}
