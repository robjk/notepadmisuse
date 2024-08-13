package com.sigmaworks.notepadmisuse.ffm.bindings.sysinfo;


import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static com.sigmaworks.notepadmisuse.ffm.Kernel32Common.*;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class GetVersionExABinding {

    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            C_INT,
            C_POINTER
    );

    public static final MemorySegment ADDR = findOrThrow("GetVersionExA");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);

    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * BOOL GetVersionExA(LPOSVERSIONINFOA lpVersionInformation)
     * }
     */
    public static FunctionDescriptor GetVersionExA$descriptor() {
        return GetVersionExABinding.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * BOOL GetVersionExA(LPOSVERSIONINFOA lpVersionInformation)
     * }
     */
    public static MethodHandle GetVersionExA$handle() {
        return GetVersionExABinding.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * BOOL GetVersionExA(LPOSVERSIONINFOA lpVersionInformation)* }
     */
    public static MemorySegment GetVersionExA$address() {
        return GetVersionExABinding.ADDR;
    }

    /**
     * {@snippet lang = c:
     * BOOL GetVersionExA(LPOSVERSIONINFOA lpVersionInformation)
     * }
     */
    public static int GetVersionExA(MemorySegment lpVersionInformation) {
        var mh$ = GetVersionExABinding.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("GetVersionExA", lpVersionInformation);
            }
            return (int) mh$.invokeExact(lpVersionInformation);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}