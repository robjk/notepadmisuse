package com.sigmaworks.notepadmisuse.ffm.bindings.msvcrt;

import com.sigmaworks.notepadmisuse.ffm.Kernel32Common;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class memcmpBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            Kernel32Common.C_INT,
            Kernel32Common.C_POINTER,
            Kernel32Common.C_POINTER,
            Kernel32Common.C_LONG_LONG
    );

    public static final MemorySegment ADDR = Kernel32Common.findOrThrow("memcmp");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);


    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * int memcmp(const void *_Buf1, const void *_Buf2, size_t _Size)
     *}
     */
    public static FunctionDescriptor memcmp$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * int memcmp(const void *_Buf1, const void *_Buf2, size_t _Size)
     *}
     */
    public static MethodHandle memcmp$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * int memcmp(const void *_Buf1, const void *_Buf2, size_t _Size)*}
     */
    public static MemorySegment memcmp$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * int memcmp(const void *_Buf1, const void *_Buf2, size_t _Size)
     *}
     */
    public static int memcmp(MemorySegment _Buf1, MemorySegment _Buf2, long _Size) {
        var mh$ = HANDLE;
        try {
            if (Kernel32Common.TRACE_DOWNCALLS) {
                Kernel32Common.traceDowncall("memcmp", _Buf1, _Buf2, _Size);
            }
            return (int) mh$.invokeExact(_Buf1, _Buf2, _Size);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}