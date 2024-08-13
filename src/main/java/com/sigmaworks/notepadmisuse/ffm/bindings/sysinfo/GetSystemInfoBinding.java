package com.sigmaworks.notepadmisuse.ffm.bindings.sysinfo;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static com.sigmaworks.notepadmisuse.ffm.Kernel32Common.*;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class GetSystemInfoBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.ofVoid(C_POINTER);

    public static final MemorySegment ADDR = findOrThrow("GetSystemInfo");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);


    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * void GetSystemInfo(LPSYSTEM_INFO lpSystemInfo)
     *}
     */
    public static FunctionDescriptor GetSystemInfo$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * void GetSystemInfo(LPSYSTEM_INFO lpSystemInfo)
     *}
     */
    public static MethodHandle GetSystemInfo$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * void GetSystemInfo(LPSYSTEM_INFO lpSystemInfo)*}
     */
    public static MemorySegment GetSystemInfo$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * void GetSystemInfo(LPSYSTEM_INFO lpSystemInfo)
     *}
     */
    public static void GetSystemInfo(MemorySegment lpSystemInfo) {
        var mh$ = HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("GetSystemInfo", lpSystemInfo);
            }
            mh$.invokeExact(lpSystemInfo);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}