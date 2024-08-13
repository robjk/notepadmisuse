package com.sigmaworks.notepadmisuse.ffm.bindings.memoryapi;

import com.sigmaworks.notepadmisuse.ffm.Kernel32Common;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

@SuppressWarnings({"UnnecessaryLocalVariable", "SameReturnValue", "unused"})
public class ReadProcessMemoryBinding {
    public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            Kernel32Common.C_INT,
            Kernel32Common.C_POINTER,
            Kernel32Common.C_POINTER,
            Kernel32Common.C_POINTER,
            Kernel32Common.C_LONG_LONG,
            Kernel32Common.C_POINTER
    );

    public static final MemorySegment ADDR = Kernel32Common.findOrThrow("ReadProcessMemory");

    public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);


    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * BOOL ReadProcessMemory(HANDLE hProcess, LPCVOID lpBaseAddress, LPVOID lpBuffer, SIZE_T nSize, SIZE_T *lpNumberOfBytesRead)
     *}
     */
    public static FunctionDescriptor ReadProcessMemory$descriptor() {
        return DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * BOOL ReadProcessMemory(HANDLE hProcess, LPCVOID lpBaseAddress, LPVOID lpBuffer, SIZE_T nSize, SIZE_T *lpNumberOfBytesRead)
     *}
     */
    public static MethodHandle ReadProcessMemory$handle() {
        return HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * BOOL ReadProcessMemory(HANDLE hProcess, LPCVOID lpBaseAddress, LPVOID lpBuffer, SIZE_T nSize, SIZE_T *lpNumberOfBytesRead)
     *}
     */
    public static MemorySegment ReadProcessMemory$address() {
        return ADDR;
    }

    /**
     * {@snippet lang = c:
     * BOOL ReadProcessMemory(HANDLE hProcess, LPCVOID lpBaseAddress, LPVOID lpBuffer, SIZE_T nSize, SIZE_T *lpNumberOfBytesRead)
     *}
     */
    public static int ReadProcessMemory(MemorySegment hProcess, MemorySegment lpBaseAddress, MemorySegment lpBuffer, long nSize, MemorySegment lpNumberOfBytesRead) {
        var mh$ = HANDLE;
        try {
            if (Kernel32Common.TRACE_DOWNCALLS) {
                Kernel32Common.traceDowncall("ReadProcessMemory", hProcess, lpBaseAddress, lpBuffer, nSize, lpNumberOfBytesRead);
            }
            return (int) mh$.invokeExact(hProcess, lpBaseAddress, lpBuffer, nSize, lpNumberOfBytesRead);
        } catch (Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }
}