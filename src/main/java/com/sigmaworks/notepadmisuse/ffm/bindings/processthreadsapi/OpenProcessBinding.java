package com.sigmaworks.notepadmisuse.ffm.bindings.processthreadsapi;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static com.sigmaworks.notepadmisuse.ffm.Kernel32Common.*;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class OpenProcessBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            C_POINTER,
            C_LONG,
            C_INT,
            C_LONG
    );

    public static final MemorySegment ADDR = findOrThrow("OpenProcess");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);


    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * HANDLE OpenProcess(DWORD dwDesiredAccess, BOOL bInheritHandle, DWORD dwProcessId)
     *}
     */
    public static FunctionDescriptor OpenProcess$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * HANDLE OpenProcess(DWORD dwDesiredAccess, BOOL bInheritHandle, DWORD dwProcessId)
     *}
     */
    public static MethodHandle OpenProcess$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * HANDLE OpenProcess(DWORD dwDesiredAccess, BOOL bInheritHandle, DWORD dwProcessId)*}
     */
    public static MemorySegment OpenProcess$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * HANDLE OpenProcess(DWORD dwDesiredAccess, BOOL bInheritHandle, DWORD dwProcessId)
     *}
     */
    public static MemorySegment OpenProcess(int dwDesiredAccess, int bInheritHandle, int dwProcessId) {
        var mh$ = HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("OpenProcess", dwDesiredAccess, bInheritHandle, dwProcessId);
            }
            return (MemorySegment) mh$.invokeExact(dwDesiredAccess, bInheritHandle, dwProcessId);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}