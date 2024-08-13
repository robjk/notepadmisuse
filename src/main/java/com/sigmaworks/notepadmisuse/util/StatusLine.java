package com.sigmaworks.notepadmisuse.util;

import static com.sigmaworks.notepadmisuse.util.StatusLine.Outcome.NEXT;
import static com.sigmaworks.notepadmisuse.util.StatusLine.Outcome.ONGOING;

/**
 * Exceptionally lazy-man implementation of a rewritable status line for long-running task progress updates
 * <p>
 * use 'chcp 65001' in yoru cmd prompt for prettiness
 */
public class StatusLine {


    public static final String ERASE_CURRENT_LINE = "\r                                                                                \r";

    public enum Outcome {
        ONGOING,
        SUCCESS,
        FAILURE,
        NEXT        // implies SUCCESS followed by starting a new status line
    }

    private static final String SPIN_CHARS = "○◌◦◌";
    private static final String SUCCESS = "[✔ ] ";
    private static final String FAILURE = "[❌] ";

    private int spinnerIndex = 0;
    private long lastLog = 0;
    private String lastMessage;

    private String nextSpinner(Outcome outcome) {
        return switch (outcome) {
            case ONGOING -> {
                char spinChar = SPIN_CHARS.charAt(spinnerIndex);
                spinnerIndex = (spinnerIndex + 1) % SPIN_CHARS.length();
                yield "[ " + spinChar + " ] ";
            }
            case FAILURE -> FAILURE;
            case NEXT, SUCCESS -> SUCCESS;
        };
    }

    private void updateSpinner(String text, Outcome outcome) {

        if (outcome == NEXT) {
            if (!lastMessage.isEmpty()) {
                System.out.print(ERASE_CURRENT_LINE);
                System.out.println(nextSpinner(Outcome.SUCCESS) + lastMessage);
            }
            outcome = ONGOING;
        }

        System.out.print(ERASE_CURRENT_LINE);
        System.out.print(nextSpinner(outcome) + text + (outcome == ONGOING ? "" : "\n"));
    }

    public void log(String text) {
        log(text, null);
    }

    public void log(String text, Outcome outcome) {
        if (outcome == null) {
            System.out.println(text);
            return;
        }

        updateSpinner(text, outcome);
        if (outcome == ONGOING || outcome == NEXT) {
            lastMessage = text;
        } else {
            lastMessage = "";
        }
    }

    /**
     * only update the status text on an intervalised basis, use for high-frequency logs.
     * <p>
     * Note the overloaded methods provide a limited means of passing parameters without incurring a garbage hit on
     * each call
     *
     * @param outcome Status outcome
     * @param text    status text, where parameters are provided it should follow String.formatted syntax
     */
    public void throttledLog(Outcome outcome, String text) {
        if (System.currentTimeMillis() - lastLog > 250 && (lastLog = System.currentTimeMillis()) != 0) {
            log(text, outcome);
        }
    }

    /**
     * only update the status text on an intervalised basis, use for high-frequency logs.
     * <p>
     * Note the overloaded methods provide a limited means of passing parameters without incurring a garbage hit on
     * each call
     *
     * @param outcome Status outcome
     * @param text    status text, where parameters are provided it should follow String.formatted syntax
     */
    public void throttledLog(Outcome outcome, String text, Object param1) {
        throttledLog(outcome, text, param1, null, null);
    }

    public void throttledLog(Outcome outcome, String text, Object param1, Object param2) {
        throttledLog(outcome, text, param1, param2, null);
    }

    public void throttledLog(Outcome outcome, String text, Object param1, Object param2, Object param3) {
        if (System.currentTimeMillis() - lastLog > 250 && (lastLog = System.currentTimeMillis()) != 0) {
            log(text.formatted(param1, param2, param3), outcome);
        }
    }
}
