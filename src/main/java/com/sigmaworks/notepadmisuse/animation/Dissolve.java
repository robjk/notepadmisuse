package com.sigmaworks.notepadmisuse.animation;

/**
 * Simple transition using a Feistel network to pick "at random" which pixels should be rendered from the two frames.
 * <p>
 * <p>
 * references:
 * <p>
 * <a href="http://antirez.com/news/113">blog article on Feistel Networks</a>
 * <p>
 * <a href="https://gist.github.com/edco/695435f902b187686274205527ed2fc6">enhanced javascript implementation</a>
 */
public class Dissolve implements Transition {

    private final byte[] rendered;
    private final int rate;
    private final Scene.Dimensions sceneDimensions;
    private int animationFrame;
    private boolean finished = false;

    /**
     * @param scene the scene whose dimensions the dissolve is working to
     * @param rate  the number of additional pixels per #transform call that will be taken from the second scene
     */
    public Dissolve(Scene scene, int rate) {
        this.sceneDimensions = scene.getDimensions();
        this.rendered = new byte[sceneDimensions.frameLength()];
        this.rate = rate;
    }

    @Override
    public byte[] transform(byte[] preBuffer, byte[] postBuffer) {
        // short-circuit copy if transition is complete
        if (hasFinished()) {
            System.arraycopy(postBuffer, 0, rendered, 0, rendered.length);
            return rendered;
        }

        // start with pre frame
        System.arraycopy(preBuffer, 0, rendered, 0, rendered.length);

        // copy in characters from post frame
        for (int i = 0; i < animationFrame; i++) {
            int pickedOffset = nextDissolveInRange(i,
                    sceneDimensions.width(),
                    sceneDimensions.height());

            // if the feistel mapping has been completed then the transition has finished, all pixels have been replaced
            if (pickedOffset == Integer.MAX_VALUE) {
                finished = true;
                break;
            } else {
                int charOffset = pickedOffset * 2;
                rendered[charOffset] = postBuffer[charOffset];
                rendered[charOffset + 1] = postBuffer[charOffset + 1];
            }
        }
        animationFrame += rate;

        return rendered;
    }

    @Override
    public void reset() {
        finished = false;
        animationFrame = 0;
    }

    @Override
    public boolean hasFinished() {
        return finished;
    }

    /**
     * Transforms the n bit input into another seemingly pseudo random number
     * in the same range. Every input n bit input will generate a different
     * n bit output.
     */
    private long feistelNetwork(long input, int bits) {
        long mask = (1L << bits) - 1;
        int halfBits = bits / 2;
        long halfMask = (1L << halfBits) - 1;
        long l = input & halfMask;
        long r = input >> halfBits;
        for (int i = 0; i < 5; i++) {
            long nl = r;
            long F = (((r * 19) + (r >> 1)) ^ r) & halfMask;
            r = l ^ F;
            l = nl;
        }
        return ((r << halfBits) | l) & mask;
    }

    /**
     * feistel network based dissolve, call repeatedly until terminal marker value returned
     *
     * @param iteration iteration number through the sequence
     * @param width     width of the screen
     * @param height    height of the screen
     * @return the pixel offset selected, or MIN_VALUE if sequence is complete
     */
    private int nextDissolve(int iteration, int width, int height) {
        int bits = 2 * (int) Math.ceil(Math.log(width * height) / Math.log(2) / 2);
        long lastFrame = 1L << bits;

        // Set many pixels per iteration otherwise it's too slow.
        if (iteration >= lastFrame) {
            return Integer.MAX_VALUE;
        }
        return (int) feistelNetwork(iteration, bits);
    }

    /**
     * Ensure the next output from the feistel range lies within our desired width x height range.
     *
     * @param iteration the current iteration sequence
     * @param width     width of the screen
     * @param height    height of the screen
     * @return the mapped output offset, that lies within the width x height range
     */
    private int nextDissolveInRange(int iteration, int width, int height) {
        int result;
        int localIteration = iteration;
        boolean inRange;

        do {
            result = nextDissolve(localIteration++, width, height);
            inRange = result < (long) width * height;
        } while (result != Integer.MAX_VALUE && !inRange);

        return result;
    }

}
