package com.sigmaworks.notepadmisuse.ffm.bindings.winuser;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static com.sigmaworks.notepadmisuse.ffm.Kernel32Common.*;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class SetFocusBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            C_POINTER,
            C_POINTER
    );

    public static final MemorySegment ADDR = findOrThrow("SetFocus");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);

    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * HWND SetFocus(HWND hWnd)
     *}
     */
    public static FunctionDescriptor SetFocus$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * HWND SetFocus(HWND hWnd)
     *}
     */
    public static MethodHandle SetFocus$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * HWND SetFocus(HWND hWnd)* }
     */
    public static MemorySegment SetFocus$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * HWND SetFocus(HWND hWnd)
     *}
     */
    public static MemorySegment SetFocus(MemorySegment hWnd) {
        var mh$ = HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("SetFocus", hWnd);
            }
            return (MemorySegment) mh$.invokeExact(hWnd);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}
