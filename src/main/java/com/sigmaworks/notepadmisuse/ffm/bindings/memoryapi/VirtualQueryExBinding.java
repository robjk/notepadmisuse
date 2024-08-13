package com.sigmaworks.notepadmisuse.ffm.bindings.memoryapi;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static com.sigmaworks.notepadmisuse.ffm.Kernel32Common.*;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class VirtualQueryExBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            C_LONG_LONG,
            C_POINTER,
            C_POINTER,
            C_POINTER,
            C_LONG_LONG
    );

    public static final MemorySegment ADDR = findOrThrow("VirtualQueryEx");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);


    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * SIZE_T VirtualQueryEx(HANDLE hProcess, LPCVOID lpAddress, PMEMORY_BASIC_INFORMATION lpBuffer, SIZE_T dwLength)
     *}
     */
    public static FunctionDescriptor VirtualQueryEx$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * SIZE_T VirtualQueryEx(HANDLE hProcess, LPCVOID lpAddress, PMEMORY_BASIC_INFORMATION lpBuffer, SIZE_T dwLength)
     *}
     */
    public static MethodHandle VirtualQueryEx$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * SIZE_T VirtualQueryEx(HANDLE hProcess, LPCVOID lpAddress, PMEMORY_BASIC_INFORMATION lpBuffer, SIZE_T dwLength)
     *}
     */
    public static MemorySegment VirtualQueryEx$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * SIZE_T VirtualQueryEx(HANDLE hProcess, LPCVOID lpAddress, PMEMORY_BASIC_INFORMATION lpBuffer, SIZE_T dwLength)
     *}
     */
    public static long VirtualQueryEx(MemorySegment hProcess, MemorySegment lpAddress, MemorySegment lpBuffer, long dwLength) {
        var mh$ = HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("VirtualQueryEx", hProcess, lpAddress, lpBuffer, dwLength);
            }
            return (long) mh$.invokeExact(hProcess, lpAddress, lpBuffer, dwLength);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}