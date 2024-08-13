package com.sigmaworks.notepadmisuse.ffm.bindings.winuser;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static com.sigmaworks.notepadmisuse.ffm.Kernel32Common.*;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class FindWindowExABinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            C_POINTER,
            C_POINTER,
            C_POINTER,
            C_POINTER,
            C_POINTER
    );

    public static final MemorySegment ADDR = findOrThrow("FindWindowExA");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);

    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * HWND FindWindowExA(HWND hWndParent, HWND hWndChildAfter, LPCSTR lpszClass, LPCSTR lpszWindow)
     *}
     */
    public static FunctionDescriptor FindWindowExA$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * HWND FindWindowExA(HWND hWndParent, HWND hWndChildAfter, LPCSTR lpszClass, LPCSTR lpszWindow)
     *}
     */
    public static MethodHandle FindWindowExA$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * HWND FindWindowExA(HWND hWndParent, HWND hWndChildAfter, LPCSTR lpszClass, LPCSTR lpszWindow)
     *}
     */
    public static MemorySegment FindWindowExA$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * HWND FindWindowExA(HWND hWndParent, HWND hWndChildAfter, LPCSTR lpszClass, LPCSTR lpszWindow)
     *}
     */
    public static MemorySegment FindWindowExA(MemorySegment hWndParent, MemorySegment hWndChildAfter, MemorySegment lpszClass, MemorySegment lpszWindow) {
        var mh$ = HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("FindWindowExA", hWndParent, hWndChildAfter, lpszClass, lpszWindow);
            }
            return (MemorySegment) mh$.invokeExact(hWndParent, hWndChildAfter, lpszClass, lpszWindow);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}
