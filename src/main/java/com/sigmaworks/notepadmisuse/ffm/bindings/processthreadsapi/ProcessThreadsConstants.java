package com.sigmaworks.notepadmisuse.ffm.bindings.processthreadsapi;

public class ProcessThreadsConstants {
    // Process permission flags

    private static final int PROCESS_TERMINATE = (int) 1L;

    /**
     * {@snippet lang = c:
     * #define PROCESS_TERMINATE 1
     *}
     */
    public static int PROCESS_TERMINATE() {
        return PROCESS_TERMINATE;
    }

    private static final int PROCESS_CREATE_THREAD = (int) 2L;

    /**
     * {@snippet lang = c:
     * #define PROCESS_CREATE_THREAD 2
     *}
     */
    public static int PROCESS_CREATE_THREAD() {
        return PROCESS_CREATE_THREAD;
    }

    private static final int PROCESS_SET_SESSIONID = (int) 4L;

    /**
     * {@snippet lang = c:
     * #define PROCESS_SET_SESSIONID 4
     *}
     */
    public static int PROCESS_SET_SESSIONID() {
        return PROCESS_SET_SESSIONID;
    }

    private static final int PROCESS_VM_OPERATION = (int) 8L;

    /**
     * {@snippet lang = c:
     * #define PROCESS_VM_OPERATION 8
     *}
     */
    public static int PROCESS_VM_OPERATION() {
        return PROCESS_VM_OPERATION;
    }

    private static final int PROCESS_VM_READ = (int) 16L;

    /**
     * {@snippet lang = c:
     * #define PROCESS_VM_READ 16
     *}
     */
    public static int PROCESS_VM_READ() {
        return PROCESS_VM_READ;
    }

    private static final int PROCESS_VM_WRITE = (int) 32L;

    /**
     * {@snippet lang = c:
     * #define PROCESS_VM_WRITE 32
     *}
     */
    public static int PROCESS_VM_WRITE() {
        return PROCESS_VM_WRITE;
    }

    private static final int PROCESS_DUP_HANDLE = (int) 64L;

    /**
     * {@snippet lang = c:
     * #define PROCESS_DUP_HANDLE 64
     *}
     */
    public static int PROCESS_DUP_HANDLE() {
        return PROCESS_DUP_HANDLE;
    }

    private static final int PROCESS_CREATE_PROCESS = (int) 128L;

    /**
     * {@snippet lang = c:
     * #define PROCESS_CREATE_PROCESS 128
     *}
     */
    public static int PROCESS_CREATE_PROCESS() {
        return PROCESS_CREATE_PROCESS;
    }

    private static final int PROCESS_SET_QUOTA = (int) 256L;

    /**
     * {@snippet lang = c:
     * #define PROCESS_SET_QUOTA 256
     *}
     */
    public static int PROCESS_SET_QUOTA() {
        return PROCESS_SET_QUOTA;
    }

    private static final int PROCESS_SET_INFORMATION = (int) 512L;

    /**
     * {@snippet lang = c:
     * #define PROCESS_SET_INFORMATION 512
     *}
     */
    public static int PROCESS_SET_INFORMATION() {
        return PROCESS_SET_INFORMATION;
    }

    private static final int PROCESS_QUERY_INFORMATION = (int) 1024L;

    /**
     * {@snippet lang = c:
     * #define PROCESS_QUERY_INFORMATION 1024
     *}
     */
    public static int PROCESS_QUERY_INFORMATION() {
        return PROCESS_QUERY_INFORMATION;
    }

    private static final int PROCESS_SUSPEND_RESUME = (int) 2048L;

    /**
     * {@snippet lang = c:
     * #define PROCESS_SUSPEND_RESUME 2048
     *}
     */
    public static int PROCESS_SUSPEND_RESUME() {
        return PROCESS_SUSPEND_RESUME;
    }

    private static final int PROCESS_QUERY_LIMITED_INFORMATION = (int) 4096L;

    /**
     * {@snippet lang = c:
     * #define PROCESS_QUERY_LIMITED_INFORMATION 4096
     *}
     */
    public static int PROCESS_QUERY_LIMITED_INFORMATION() {
        return PROCESS_QUERY_LIMITED_INFORMATION;
    }

    private static final int PROCESS_SET_LIMITED_INFORMATION = (int) 8192L;

    /**
     * {@snippet lang = c:
     * #define PROCESS_SET_LIMITED_INFORMATION 8192
     *}
     */
    public static int PROCESS_SET_LIMITED_INFORMATION() {
        return PROCESS_SET_LIMITED_INFORMATION;
    }

    private static final int PROCESS_ALL_ACCESS = (int) 2097151L;

    /**
     * {@snippet lang = c:
     * #define PROCESS_ALL_ACCESS 2097151
     *}
     */
    public static int PROCESS_ALL_ACCESS() {
        return PROCESS_ALL_ACCESS;
    }
}
