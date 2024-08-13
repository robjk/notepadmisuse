package com.sigmaworks.notepadmisuse.ffm;

import java.lang.foreign.*;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.foreign.ValueLayout.JAVA_BYTE;

/**
 * Various constants from jextract
 */
public class Kernel32Common {

    public static final ValueLayout.OfBoolean C_BOOL = ValueLayout.JAVA_BOOLEAN;
    public static final ValueLayout.OfByte C_CHAR = ValueLayout.JAVA_BYTE;
    public static final ValueLayout.OfShort C_SHORT = ValueLayout.JAVA_SHORT;
    public static final ValueLayout.OfInt C_INT = ValueLayout.JAVA_INT;
    public static final ValueLayout.OfLong C_LONG_LONG = ValueLayout.JAVA_LONG;
    public static final ValueLayout.OfFloat C_FLOAT = ValueLayout.JAVA_FLOAT;
    public static final ValueLayout.OfDouble C_DOUBLE = ValueLayout.JAVA_DOUBLE;
    public static final AddressLayout C_POINTER = ValueLayout.ADDRESS
            .withTargetLayout(MemoryLayout.sequenceLayout(java.lang.Long.MAX_VALUE, JAVA_BYTE));
    public static final ValueLayout.OfInt C_LONG = ValueLayout.JAVA_INT;
    public static final ValueLayout.OfDouble C_LONG_DOUBLE = ValueLayout.JAVA_DOUBLE;

    public static final AddressLayout HMODULE = C_POINTER;
    /**
     * {@snippet lang=c :
     * typedef struct HRGN__ {
     *     int unused;
     * } *HRGN
     * }
     */

    // data types
    private static final int FALSE = (int)0L;
    /**
     * {@snippet lang=c :
     * #define FALSE 0
     * }
     */
    public static int FALSE() {
        return FALSE;
    }
    private static final int TRUE = (int)1L;
    /**
     * {@snippet lang=c :
     * #define TRUE 1
     * }
     */
    public static int TRUE() {
        return TRUE;
    }



    static final Arena LIBRARY_ARENA = Arena.ofAuto();
    public static final boolean TRACE_DOWNCALLS = Boolean.getBoolean("jextract.trace.downcalls");

    static final SymbolLookup SYMBOL_LOOKUP = SymbolLookup.libraryLookup(System.mapLibraryName("Kernel32"), LIBRARY_ARENA)
            .or(SymbolLookup.loaderLookup())
            .or(Linker.nativeLinker().defaultLookup());


    public static void traceDowncall(String name, Object... args) {
        String traceArgs = Arrays.stream(args)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        System.out.printf("%s(%s)\n", name, traceArgs);
    }

    public static MemorySegment findOrThrow(String symbol) {
        return SYMBOL_LOOKUP.find(symbol)
                .orElseThrow(() -> new UnsatisfiedLinkError("unresolved symbol: " + symbol));
    }

}
