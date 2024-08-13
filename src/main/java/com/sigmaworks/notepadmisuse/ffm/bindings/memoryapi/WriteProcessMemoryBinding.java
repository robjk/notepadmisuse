package com.sigmaworks.notepadmisuse.ffm.bindings.memoryapi;

import com.sigmaworks.notepadmisuse.ffm.Kernel32Common;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class WriteProcessMemoryBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            Kernel32Common.C_INT,
            Kernel32Common.C_POINTER,
            Kernel32Common.C_POINTER,
            Kernel32Common.C_POINTER,
            Kernel32Common.C_LONG_LONG,
            Kernel32Common.C_POINTER
    );

    public static final MemorySegment ADDR = Kernel32Common.findOrThrow("WriteProcessMemory");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);

    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * BOOL WriteProcessMemory(HANDLE hProcess, LPVOID lpBaseAddress, LPCVOID lpBuffer, SIZE_T nSize, SIZE_T *lpNumberOfBytesWritten)
     *}
     */
    public static FunctionDescriptor WriteProcessMemory$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * BOOL WriteProcessMemory(HANDLE hProcess, LPVOID lpBaseAddress, LPCVOID lpBuffer, SIZE_T nSize, SIZE_T *lpNumberOfBytesWritten)
     *}
     */
    public static MethodHandle WriteProcessMemory$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * BOOL WriteProcessMemory(HANDLE hProcess, LPVOID lpBaseAddress, LPCVOID lpBuffer, SIZE_T nSize, SIZE_T *lpNumberOfBytesWritten)
     *}
     */
    public static MemorySegment WriteProcessMemory$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * BOOL WriteProcessMemory(HANDLE hProcess, LPVOID lpBaseAddress, LPCVOID lpBuffer, SIZE_T nSize, SIZE_T *lpNumberOfBytesWritten)
     *}
     */
    public static int WriteProcessMemory(MemorySegment hProcess, MemorySegment lpBaseAddress, MemorySegment lpBuffer, long nSize, MemorySegment lpNumberOfBytesWritten) {
        var mh$ = HANDLE;
        try {
            if (Kernel32Common.TRACE_DOWNCALLS) {
                Kernel32Common.traceDowncall("WriteProcessMemory", hProcess, lpBaseAddress, lpBuffer, nSize, lpNumberOfBytesWritten);
            }
            return (int) mh$.invokeExact(hProcess, lpBaseAddress, lpBuffer, nSize, lpNumberOfBytesWritten);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}
