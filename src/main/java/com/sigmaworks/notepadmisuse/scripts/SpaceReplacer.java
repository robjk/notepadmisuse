package com.sigmaworks.notepadmisuse.scripts;

import com.sigmaworks.notepadmisuse.util.GeneralUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * mediatoascii is great, but has a tendency to use '.' characters to represent white, for this particular use case the
 * rendering is not going to be vsync aligned, and any non-white characters that need rendering are likely to increase
 * the perception of flickering.
 * <p>
 * This code replace all '.' with ' '  where they do not have at least one non-'.' character in an adjacent cell
 */
public class SpaceReplacer {

    public static void main(String[] args) throws IOException {
        int rowLength = 172;

        byte[] sourceBytes = GeneralUtils.readResource("badapple (172x62) 6573.txt");

        String utf16le = new String(sourceBytes, StandardCharsets.UTF_16LE);
        char[] chars = utf16le.toCharArray();
        // Process the grid to replace surrounded '.' with ' '

        char[] replaced = replaceSurroundedDots(chars, rowLength);
        String toString = new String(replaced);
        Files.writeString(Path.of("badapple (172x62) 6573.txt"), toString, StandardCharsets.UTF_16LE);
    }

    // Function to replace surrounded '.' with ' '
    public static char[] replaceSurroundedDots(char[] grid, int rowLength) {
        char[] newGrid = new char[grid.length];

        int rowCount = grid.length / rowLength;

        int frameStartOffset = 0;
        int frameEndOffset = 0;
        for (int y = 0; y < rowCount; y++) {
            if (y % 62 == 0) {
                frameStartOffset = (y * rowLength);
                frameEndOffset = (y + 62) * rowLength;
            }
            for (int x = 0; x < rowLength; x++) {
                int index = (y * rowLength) + x;
                if (grid[index] == '.' && isSurroundedByDots(grid, rowLength, rowCount, x, y, frameStartOffset, frameEndOffset)) {
                    newGrid[index] = ' ';
                } else {
                    newGrid[index] = grid[index];
                }
            }
        }

        return newGrid;
    }

    // Function to check if a '.' is surrounded by '.' in all 8 adjacent cells
    public static boolean isSurroundedByDots(char[] grid,
                                             int rowLength,
                                             int rowCount,
                                             int x,
                                             int y,
                                             int frameStartOffset,
                                             int frameEndOffset) {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (newX < 0
                    || newY < 0
                    || newX >= rowLength
                    || newY < frameStartOffset
                    || newY > frameEndOffset) {
                continue;
            }

            int offset = (newY * rowLength) + newX;
            char charToCheck = grid[offset];
            if (charToCheck != '.' && charToCheck != ' ' && charToCheck != '\n') {
                return false;
            }
        }

        return true;
    }

    // Function to print the grid
    public static void printGrid(char[] grid, int gridSize) {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                System.out.print(grid[(y * gridSize) + x] + " ");
            }
            System.out.println();
        }
    }
}
