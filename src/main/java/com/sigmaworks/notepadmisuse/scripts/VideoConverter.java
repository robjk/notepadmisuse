import com.sigmaworks.notepadmisuse.util.GeneralUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

static int frameCount;

/**
 * Takes mediatoascii output, removes the framemarkers and performs a somewhat half-arsed trimming/padding exercise to
 * make it fit the desired dimensions, padding/trimming as appropriate.
 * <p>
 * <b>This code should be categorised as "not remotely robust", no-one should use it</b>
 * <p>
 * for %a in (mh\*) do mediatoascii --as-text --max-fps 30 --use-max-fps-for-output-video --video-path=%a --scale-down 1
 */
void main() throws IOException {

    Settings badApple172x62 = new Settings("ba.172w.62h.30fps.txt",
            172,
            62,
            172,
            62,
            true,
            true);

    Settings boing176x68 = new Settings("boing 176x68.txt",
            176,
            68,
            172,
            62,
            true,
            true);

    Settings javaLogo = new Settings("java-logo 122x48-utf-le16.txt",
            116,
            48,
            172,
            62,
            true,
            true);

    // select the source video for conversion
    Settings videoSettings = boing176x68;

    char padChar = ' ';

    String topPad, bottomPad, leftPad, rightPad;
    topPad = videoSettings.createTopPad(padChar);
    bottomPad = videoSettings.createBottomPad(padChar);
    leftPad = videoSettings.createLeftPad(padChar);
    rightPad = videoSettings.createRightPad(padChar);

    byte[] source = GeneralUtils.readResource(videoSettings.sourceFile);
    String concatenated = new String(source, StandardCharsets.UTF_8);
    String[] splitSource = concatenated.split("\n");
    List<String> input = List.of(splitSource);

    StringBuilder allFrames = new StringBuilder();
    int frameSize = videoSettings.outputWidth * videoSettings.outputHeight;
    StringBuilder frame = new StringBuilder();

    int frameRow = 0;
    frameCount = 0;

    for (String line : input) {
        boolean frameMarker = line.charAt(0) == 0x1B && line.charAt(1) == 0x63;

        // new frame marker
        if (frameMarker) {
            // but we first need to add the previous frame
            if (!frame.isEmpty()) {
                frame.append(bottomPad);
                allFrames.append(frame);
                assert allFrames.length() == (frameCount + 1) * frameSize;
                assert (allFrames.length() % frameSize == 0) : "framesize mismatch";
                frameCount++;
                frame.setLength(0);
            }

            frameRow = 0;
            if (!videoSettings.isRowWithinHeightTrim(frameRow)) {
                frame.append(topPad);
                String removedFrameMarker = line.substring(2);
                String processed = processRow(removedFrameMarker, videoSettings);
                addWithPad(frame, processed, leftPad, rightPad, videoSettings.outputWidth);
            }
            frameRow++;
            continue;
        }

        if (!videoSettings.isRowWithinHeightTrim(frameRow)) {
            String processed = processRow(line, videoSettings);
            addWithPad(frame, processed, leftPad, rightPad, videoSettings.outputWidth);
        }
        frameRow++;
    }

    assert allFrames.length() == frameSize * frameCount;

    if (!frame.isEmpty()) {
        frameCount++;
        // for single frames the padding logic above will have been skipped apply it now
        if (frameCount == 1) {
            allFrames.append(topPad).append(frame).append(bottomPad);
        } else {
            allFrames.append(frame);
        }
    }

    assert allFrames.length() * 2 == frameSize * (frameCount + 1);
    String everything = allFrames.toString();
//    everything = everything.replaceAll("P", " ");
//    everything = everything.replaceAll(":", "7");
//    everything = everything.replaceAll("\\?", " ");
    Files.writeString(Path.of("./test (%dx%d) %d.txt".formatted(videoSettings.outputWidth, videoSettings.outputHeight, frameCount)), everything, StandardCharsets.UTF_16LE);
}

private void addWithPad(StringBuilder frame, String body, String leftPad, String rightPad, int expectedWidth) {
    StringBuilder nextRow = new StringBuilder();
    nextRow.append(leftPad).append(body).append(rightPad).append("\n");
    System.out.printf("left=%d, row=%d, right=%d, total=%d%n", leftPad.length(), body.length(), rightPad.length(), nextRow.length());

    assert nextRow.length() == expectedWidth : "width mismatch, expect=%d vs %d".formatted(expectedWidth, nextRow.length());

    frame.append(nextRow);
}

private String processRow(String row, Settings videoSettings) {
    if (videoSettings.isWidthTrimmed()) {
        // paddingLeft/Right will be -ve
        int start = -videoSettings.paddingLeft;
        int end = row.length() + videoSettings.paddingRight;
        return row.substring(start, end);
    }

    return row;
}


record Settings(
        String sourceFile,
        int inputWidth,
        int inputHeight,
        int outputWidth,
        int outputHeight,
        int paddingLeft,
        int paddingRight,
        int paddingTop,
        int paddingBottom) {

    Settings(
            String sourceFile,
            int inputWidth,
            int inputHeight,
            int outputWidth,
            int outputHeight,
            boolean evenHeightPad,
            boolean evenWidthPad) {

        int paddingLeft, paddingRight, paddingTop, paddingBottom;

        int heightPadding = outputHeight - inputHeight;
        int widthPadding = outputWidth - inputWidth;

        if (evenHeightPad) {
            paddingTop = heightPadding / 2;
            int heightCarry = heightPadding % 2 == 0 ? 0 : 1;
            heightCarry *= heightPadding >= 0 ? 1 : -1;
            paddingBottom = (heightPadding / 2) + heightCarry;
        } else {
            paddingTop = 0;
            paddingBottom = heightPadding;
        }

        if (evenWidthPad) {
            paddingLeft = widthPadding / 2;
            int widthCarry = widthPadding % 2 == 0 ? 0 : 1;
            widthCarry *= widthPadding >= 0 ? 1 : -1;
            paddingRight = (widthPadding / 2) + widthCarry;
        } else {
            paddingLeft = 0;
            paddingRight = widthPadding;
        }

        assert heightPadding == paddingTop + paddingBottom;
        assert widthPadding == paddingLeft + paddingRight;

        this(sourceFile,
                inputWidth,
                inputHeight,
                outputWidth,
                outputHeight,
                paddingLeft,
                paddingRight,
                paddingTop,
                paddingBottom);
    }

    public boolean isTopTrimmed() {
        return paddingTop < 0;
    }

    public boolean isBottomTrimmed() {
        return paddingBottom < 0;
    }

    public boolean isLeftTrimmed() {
        return paddingLeft < 0;
    }

    public boolean isRightTrimmed() {
        return paddingRight < 0;
    }

    public boolean isHeightTrimmed() {
        return isTopTrimmed() || isBottomTrimmed();
    }

    public boolean isWidthTrimmed() {
        return isLeftTrimmed() || isRightTrimmed();
    }

    public boolean isHeightPadded() {
        return !isHeightTrimmed();
    }

    public boolean isWidthPadded() {
        return !isWidthTrimmed();
    }

    public String createTopPad(char padChar) {
        if (paddingTop <= 0) {
            return "";
        }
        return String.format("%" + (outputWidth - 1) + "s\n", padChar).repeat(paddingTop);
    }

    public String createBottomPad(char padChar) {
        if (paddingBottom <= 0) {
            return "";
        }
        return String.format("%" + (outputWidth - 1) + "s\n", padChar).repeat(paddingBottom);
    }

    public String createLeftPad(char padChar) {
        if (paddingLeft <= 0) {
            return "";
        }
        return String.format("%" + paddingLeft + "s", padChar);
    }

    public String createRightPad(char padChar) {
        if (paddingRight <= 0) {
            return "";
        }
        return String.format("%" + paddingRight + "s", padChar);
    }

    public boolean isRowWithinTopTrim(int rowNumber) {
        if (!isTopTrimmed()) {
            return false;
        }

        return rowNumber < (-paddingTop);
    }

    public boolean isRowWithinBottomTrim(int rowNumber) {
        if (!isBottomTrimmed()) {
            return false;
        }

        return rowNumber >= inputHeight + paddingBottom;
    }

    public boolean isRowWithinHeightTrim(int rowNumber) {
        return isRowWithinTopTrim(rowNumber) || isRowWithinBottomTrim(rowNumber);
    }
}