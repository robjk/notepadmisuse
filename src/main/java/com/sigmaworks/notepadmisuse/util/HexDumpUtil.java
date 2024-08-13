package com.sigmaworks.notepadmisuse.util;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;

/**
 * Taken from jen20's Gist and lightly modified
 * <a href="https://gist.github.com/jen20/906db194bd97c14d91df">jen20's original implementation</a>
 * <p>
 * Rumours exist of a Per Minborg pretty printer for MemorySegments, but a cursory search isn't showing it in JDK 22
 * <a href="https://github.com/openjdk/panama-foreign/pull/695/commits/28271f65b5b9e98da465e196c6ca770b5ae539ad#diff-af54654ae7517a55aa8a408b3fb1792f310d672b8d6d43f0967b87bb3686e997">MS Pretty Printer</a>
 * <p>
 * Changes:
 * Strip additional control characters that mess with terminal output
 * Trivial shims for MemorySegments
 */
public final class HexDumpUtil {

    public static void printHexDump(byte[] bytes) {
        System.out.println(formatHexDump(bytes, 0, bytes.length));
    }

    public static void printHexDump(byte[] bytes, int offset, int count) {
        System.out.println(formatHexDump(bytes, offset, count));
    }

    public static void printHexDump(MemorySegment segment) {
        printHexDump(segment, segment.byteSize());
    }

    /**
     * prints the supplied segment to stdout from offset 0 to length
     *
     * @param segment the segment to be dumped
     * @param length  the length in bytes of the dump
     */
    public static void printHexDump(MemorySegment segment, long length) {
        System.out.println(formatHexDump(segment.toArray(ValueLayout.JAVA_BYTE), 0, length));
    }

    public static void printHexDump(MemorySegment segment, long offset, long count) {
        System.out.println(formatHexDump(segment.toArray(ValueLayout.JAVA_BYTE), (int) offset, count));
    }

    public static String formatHexDump(byte[] array, int offset, long length) {
        final int width = 16;

        StringBuilder builder = new StringBuilder();

        for (int rowOffset = offset; rowOffset < offset + length; rowOffset += width) {
            builder.append(String.format("%06d:  ", rowOffset));

            for (int index = 0; index < width; index++) {
                if (rowOffset + index < array.length) {
                    builder.append(String.format("%02x ", array[rowOffset + index]));
                } else {
                    builder.append("   ");
                }
            }

            if (rowOffset < array.length) {
                int asciiWidth = Math.min(width, array.length - rowOffset);
                builder.append("  |  ");
                builder.append(new String(array, rowOffset, asciiWidth, StandardCharsets.UTF_8)
                        // the following can mess-up terminal output, and they're non-visible to boot
                        .replaceAll("" + (char) 0x0C, " ")
                        .replaceAll("" + (char) 0x0D, " ")
                        .replaceAll("" + (char) 0x0, " ")
                        .replaceAll("\t", " ")
                        .replaceAll("\r", " ")
                        .replaceAll("\n", " "));
            }

            builder.append(String.format("%n"));
        }

        return builder.toString();
    }
}