package com.sigmaworks.notepadmisuse.animation;

import com.sigmaworks.notepadmisuse.util.GeneralUtils;

/**
 * Simple wrapper for ascii animations, stores attributes and allows accessing frames incrementally for rendering
 */
public class AsciiAnimation {

    private final AnimationDefinition definition;
    private final byte[] animationBytes;
    private final int totalFrames;
    private int frameCounter;

    public record AnimationDefinition(int width, int height, int frameSize, int frameRate, String template,
                                      String animation) {
        public AnimationDefinition(int width, int height, int frameRate, String loadingTemplate, String animation) {
            // unicode chars so * 2
            this(width, height, width * height * 2, frameRate, loadingTemplate, animation);
        }
    }

    // https://www.youtube.com/watch?v=pyZUJ92sql8
    public static final AnimationDefinition badApple172x62 = new AnimationDefinition(172,
            62,
            1,
            "logo (172x62).txt",
            "badapple (172x62) 6573.txt.gz");

    // https://www.pouet.net/prod.php?which=27096
    public static final AnimationDefinition boing172x62 = new AnimationDefinition(172,
            62,
            3,
            "logo (172x62).txt",
            "boing (172x62) custom.txt.gz");

    public AsciiAnimation(AnimationDefinition definition) {
        this.definition = definition;
        this.animationBytes = GeneralUtils.readResource(definition.animation);
        this.frameCounter = 0;

        assert (animationBytes.length / definition.frameSize) == (int) ((double) animationBytes.length / definition.frameSize);
        this.totalFrames = (animationBytes.length / definition.frameSize) - 1;
    }

    /**
     * copies the next frame of this animation into the supplied destBuffer.
     * The animation will automatically restart at frame zero when it has completed.
     *
     * @param destBuffer where to copy the next frame to
     * @return true if the animation will restart on the next call to copyNextFrame
     */
    public boolean copyNextFrame(byte[] destBuffer) {
        assert destBuffer.length == definition.frameSize : "destination buffer does not match frame size";

        int frameOffset = frameCounter * definition.frameSize;
        System.arraycopy(animationBytes, frameOffset, destBuffer, 0, definition.frameSize);

        frameCounter += definition.frameRate;

        if (frameCounter >= totalFrames) {
            frameCounter = 0;
            return true;
        }

        return false;
    }

    public int byteSize() {
        return animationBytes.length;
    }

    public int totalFrames() {
        return animationBytes.length % definition.frameSize;
    }

    public int getFrame() {
        return frameCounter;
    }

    public void setFrame(int frameNumber) {
        frameCounter = Math.max(0, frameNumber);
    }

    public AnimationDefinition getDefinition() {
        return definition;
    }
}
