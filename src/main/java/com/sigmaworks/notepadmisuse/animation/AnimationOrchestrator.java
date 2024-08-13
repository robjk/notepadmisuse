package com.sigmaworks.notepadmisuse.animation;

import com.sigmaworks.notepadmisuse.util.GeneralUtils;
import com.sigmaworks.notepadmisuse.NotepadMisuse;
import com.sigmaworks.notepadmisuse.util.StatusLine;

import java.lang.foreign.MemorySegment;
import java.nio.charset.StandardCharsets;

import static com.sigmaworks.notepadmisuse.util.StatusLine.Outcome.ONGOING;
import static com.sigmaworks.notepadmisuse.util.StatusLine.Outcome.SUCCESS;
import static com.sigmaworks.notepadmisuse.animation.AsciiAnimation.badApple172x62;
import static com.sigmaworks.notepadmisuse.animation.AsciiAnimation.boing172x62;

/**
 * Runs the chosen animation sequence, transitioning where appropriate
 */
public class AnimationOrchestrator {
    private static final boolean DISPLAY_FRAME_COUNTER = false;

    private final MemorySegment hProc;
    private final long hWndNotepad;
    private final long notepadStartAddress;
    private final long notepadEndAddress;

    private final StatusLine status = new StatusLine();

    public AnimationOrchestrator(MemorySegment hProc, long hWndNotepad, Long notepadStartAddress, Long endTokenAddress) {
        this.hProc = hProc;
        this.hWndNotepad = hWndNotepad;
        this.notepadStartAddress = notepadStartAddress;
        this.notepadEndAddress = endTokenAddress;
    }

    public void animate(int durationInFrames) {

        status.log("reading animations", ONGOING);
        AsciiAnimation badApple = new AsciiAnimation(badApple172x62);
        badApple.setFrame(100);
        AsciiAnimation boing = new AsciiAnimation(boing172x62);
        status.log("read animations", SUCCESS);

        assert boing.getDefinition().height() == badApple.getDefinition().height();
        assert boing.getDefinition().width() == badApple.getDefinition().width();

        AsciiAnimation.AnimationDefinition templateDefinition = boing.getDefinition();


        byte[] logoBuffer = GeneralUtils.readResource(templateDefinition.template());
        assert notepadEndAddress + NotepadMisuse.END_MARKER.length - notepadStartAddress + 2 == logoBuffer.length : "start/end markers should be same size as the logo template";    // +2 for line feed

        Scene scene = new Scene(templateDefinition.width(), templateDefinition.height(), hProc, hWndNotepad, notepadStartAddress);

        status.log("animation size=%d, dimension=%dwx%dh, frameSize=%d, modulus=%d".formatted(badApple.byteSize(),
                templateDefinition.width(),
                templateDefinition.height(),
                templateDefinition.frameSize(),
                badApple.totalFrames()), SUCCESS);

        byte[] nextFrameBuffer = new byte[templateDefinition.frameSize()];
        byte[] nextFrameBuffer2 = new byte[templateDefinition.frameSize()];

        Dissolve dissolveToBoing = new Dissolve(scene, 150);
        Dissolve dissolveToBadApple = new Dissolve(scene, 150);

        Runnable phase1 = () -> {
            byte[] transformedFrame;

            boing.copyNextFrame(nextFrameBuffer);
            transformedFrame = dissolveToBoing.transform(logoBuffer, nextFrameBuffer);
            scene.overwrite(transformedFrame);
        };

        Runnable phase2 = () -> {
            byte[] transformedFrame;

            boing.copyNextFrame(nextFrameBuffer);
            if (!badApple.copyNextFrame(nextFrameBuffer2)) {
                transformedFrame = dissolveToBadApple.transform(nextFrameBuffer, nextFrameBuffer2);
                scene.overwrite(transformedFrame);
            }
        };

        while (durationInFrames-- > 0) {
            if (scene.getFrameCount() < 350) {
                status.throttledLog(ONGOING, "%d : %s", scene.getFrameCount(), "Amiga");
                phase1.run();
            } else {
                status.throttledLog(ONGOING, "%d : %s", scene.getFrameCount(), "traditional bad apple rendering in unusual places");
                phase2.run();
            }

            if (DISPLAY_FRAME_COUNTER) {
                // stamp current animation frame count into buffer
                scene.set(0, 0, Long.toString(scene.getFrameCount()).getBytes(StandardCharsets.UTF_16LE));
            }
            scene.renderScene();
        }
    }
}
