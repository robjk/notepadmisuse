package com.sigmaworks.notepadmisuse.animation;

public interface Transition {

    /**
     * perform some transition between the preBuffer and postBuffer, call repeatedly to animate
     * @param preBuffer source "image"
     * @param postBuffer destination "image"
     * @return output "image"
     */
    byte[] transform(byte[] preBuffer, byte[] postBuffer);

    /**
     * reset any internal counters within the transition, allow the transition to start from scratch again
     */
    void reset();

    /**
     * @return true if the transition effect has completed
     */
    boolean hasFinished();
}
