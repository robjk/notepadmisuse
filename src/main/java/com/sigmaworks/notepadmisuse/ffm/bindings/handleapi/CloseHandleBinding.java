package com.sigmaworks.notepadmisuse.ffm.bindings.handleapi;

import com.sigmaworks.notepadmisuse.ffm.Kernel32Common;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class CloseHandleBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            Kernel32Common.C_INT,
            Kernel32Common.C_POINTER
    );

    public static final MemorySegment ADDR = Kernel32Common.findOrThrow("CloseHandle");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);


    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * BOOL CloseHandle(HANDLE hObject)
     * }
     */
    public static FunctionDescriptor CloseHandle$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * BOOL CloseHandle(HANDLE hObject)
     * }
     */
    public static MethodHandle CloseHandle$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * BOOL CloseHandle(HANDLE hObject)* }
     */
    public static MemorySegment CloseHandle$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * BOOL CloseHandle(HANDLE hObject)
     * }
     */
    public static int CloseHandle(MemorySegment hObject) {
        var mh$ = HANDLE;
        try {
            if (Kernel32Common.TRACE_DOWNCALLS) {
                Kernel32Common.traceDowncall("CloseHandle", hObject);
            }
            return (int) mh$.invokeExact(hObject);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}

