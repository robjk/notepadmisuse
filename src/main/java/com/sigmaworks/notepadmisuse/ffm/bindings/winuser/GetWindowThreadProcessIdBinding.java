package com.sigmaworks.notepadmisuse.ffm.bindings.winuser;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static com.sigmaworks.notepadmisuse.ffm.Kernel32Common.*;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class GetWindowThreadProcessIdBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            C_LONG,
            C_POINTER,
            C_POINTER
    );

    public static final MemorySegment ADDR = findOrThrow("GetWindowThreadProcessId");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);

    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * DWORD GetWindowThreadProcessId(HWND hWnd, LPDWORD lpdwProcessId)
     *}
     */
    public static FunctionDescriptor GetWindowThreadProcessId$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * DWORD GetWindowThreadProcessId(HWND hWnd, LPDWORD lpdwProcessId)
     *}
     */
    public static MethodHandle GetWindowThreadProcessId$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * DWORD GetWindowThreadProcessId(HWND hWnd, LPDWORD lpdwProcessId)* }
     */
    public static MemorySegment GetWindowThreadProcessId$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * DWORD GetWindowThreadProcessId(HWND hWnd, LPDWORD lpdwProcessId)
     *}
     */
    public static int GetWindowThreadProcessId(MemorySegment hWnd, MemorySegment lpdwProcessId) {
        var mh$ = HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("GetWindowThreadProcessId", hWnd, lpdwProcessId);
            }
            return (int) mh$.invokeExact(hWnd, lpdwProcessId);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}
