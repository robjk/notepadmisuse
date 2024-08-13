package com.sigmaworks.notepadmisuse.animation;

import java.lang.foreign.MemorySegment;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import static com.sigmaworks.notepadmisuse.ffm.WinOS.InvalidateRect;
import static com.sigmaworks.notepadmisuse.ffm.WinOS.WriteProcessMemory;

/**
 * Expose the notepad edit box buffer as an easier to use scene consiting of a single frame that can be updated
 */
public class Scene {

    private final Dimensions dimensions;
    private final MemorySegment hProc;
    private final ByteBuffer renderBuffer;
    private final MemorySegment destinationAddress;
    private final MemorySegment segmentView;
    private final byte[] emptyScene;
    private final long hWndNotepad;
    private long currentFrameStart;
    private long frameCount;

    public record Dimensions(int width, int height, int charWidth, int frameLength) {
        Dimensions(int width, int height) {
            this(width, height, width * 2, width * height * 2);
        }

        int offset(int x, int y) {
            return (y * charWidth) + x * 2;
        }
    }

    public Scene(int width, int height, MemorySegment hProc, long hWndNotepad, long startTokenAddress) {
        this.hWndNotepad = hWndNotepad;
        this.dimensions = new Dimensions(width, height);
        this.hProc = hProc;
        // create a UTF-16LE bytebuffer we can use to back a MemorySegment for passing to WriteProcessMemory
        this.renderBuffer = ByteBuffer.allocateDirect(dimensions.frameLength);
        this.renderBuffer.order(ByteOrder.LITTLE_ENDIAN);
        // the MS backed by the renderBuffer
        this.segmentView = MemorySegment.ofBuffer(renderBuffer);

        this.emptyScene = new byte[dimensions.frameLength];

        // address in notepad.exe where the "frame buffer" resides
        this.destinationAddress = MemorySegment.ofAddress(startTokenAddress);
    }

    public void reset() {
        renderBuffer.position(0);
        renderBuffer.put(emptyScene, 0, dimensions.frameLength);
    }

    /**
     * replace the contents of the renderBuffer with the supplied srcBuffer, srcBuffer should be the dimensions as
     * renderBuffer.
     * <p>
     * Typically used to initialise a new frame
     *
     * @param srcBuffer buffer to overwrite the render buffer with
     */
    public void overwrite(byte[] srcBuffer) {
        assert srcBuffer.length == dimensions.frameLength : "overwrite buffer (%d) != renderBuffer size (%d)".formatted(srcBuffer.length, dimensions.frameLength);

        renderBuffer.position(0);
        renderBuffer.put(srcBuffer, 0, dimensions.frameLength);
    }

    /**
     * writes the supplied byte array to the render buffer at the specified x/y
     */
    public void set(int x, int y, byte[] src) {
        renderBuffer.put(dimensions.offset(x, y), src);
    }

    public void set(int x, int y, char value) {
        int offset = dimensions.offset(x, y);
        renderBuffer.putChar(offset, value);
    }

    public void set(int offset, char value) {
        assert offset < dimensions.frameLength : "attempt to set char at offset %d beyond frameLength %d".formatted(offset, dimensions.frameLength);
        renderBuffer.putChar(offset, value);
    }

    public void renderScene() {
        // target 30 fps - we're beholden to GDI refresh, so it's not going be vsync aligned, but we can try
        long frameRate = TimeUnit.MILLISECONDS.toNanos(32);
        long elapsedNanos = (Math.min(frameRate, System.nanoTime() - currentFrameStart));
        long nanoDelay = Math.max(0, frameRate - elapsedNanos);
        LockSupport.parkNanos(nanoDelay);

        frameCount++;
        currentFrameStart = System.nanoTime();
        WriteProcessMemory(hProc, destinationAddress, segmentView, dimensions.frameLength);
        InvalidateRect(hWndNotepad, null, false);
    }

    public long getFrameCount() {
        return frameCount;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }
}
