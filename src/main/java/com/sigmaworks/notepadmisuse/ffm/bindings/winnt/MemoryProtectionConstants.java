package com.sigmaworks.notepadmisuse.ffm.bindings.winnt;

public class MemoryProtectionConstants {

    private static final int PAGE_NOACCESS = (int)1L;
    /**
     * {@snippet lang=c :
     * #define PAGE_NOACCESS 1
     * }
     */
    public static int PAGE_NOACCESS() {
        return PAGE_NOACCESS;
    }
    private static final int PAGE_READONLY = (int)2L;
    /**
     * {@snippet lang=c :
     * #define PAGE_READONLY 2
     * }
     */
    public static int PAGE_READONLY() {
        return PAGE_READONLY;
    }
    private static final int PAGE_READWRITE = (int)4L;
    /**
     * {@snippet lang=c :
     * #define PAGE_READWRITE 4
     * }
     */
    public static int PAGE_READWRITE() {
        return PAGE_READWRITE;
    }
    private static final int PAGE_WRITECOPY = (int)8L;
    /**
     * {@snippet lang=c :
     * #define PAGE_WRITECOPY 8
     * }
     */
    public static int PAGE_WRITECOPY() {
        return PAGE_WRITECOPY;
    }
    private static final int PAGE_EXECUTE = (int)16L;
    /**
     * {@snippet lang=c :
     * #define PAGE_EXECUTE 16
     * }
     */
    public static int PAGE_EXECUTE() {
        return PAGE_EXECUTE;
    }
    private static final int PAGE_EXECUTE_READ = (int)32L;
    /**
     * {@snippet lang=c :
     * #define PAGE_EXECUTE_READ 32
     * }
     */
    public static int PAGE_EXECUTE_READ() {
        return PAGE_EXECUTE_READ;
    }
    private static final int PAGE_EXECUTE_READWRITE = (int)64L;
    /**
     * {@snippet lang=c :
     * #define PAGE_EXECUTE_READWRITE 64
     * }
     */
    public static int PAGE_EXECUTE_READWRITE() {
        return PAGE_EXECUTE_READWRITE;
    }
    private static final int PAGE_EXECUTE_WRITECOPY = (int)128L;
    /**
     * {@snippet lang=c :
     * #define PAGE_EXECUTE_WRITECOPY 128
     * }
     */
    public static int PAGE_EXECUTE_WRITECOPY() {
        return PAGE_EXECUTE_WRITECOPY;
    }
    private static final int PAGE_GUARD = (int)256L;
    /**
     * {@snippet lang=c :
     * #define PAGE_GUARD 256
     * }
     */
    public static int PAGE_GUARD() {
        return PAGE_GUARD;
    }
    private static final int PAGE_NOCACHE = (int)512L;
    /**
     * {@snippet lang=c :
     * #define PAGE_NOCACHE 512
     * }
     */
    public static int PAGE_NOCACHE() {
        return PAGE_NOCACHE;
    }
    private static final int PAGE_WRITECOMBINE = (int)1024L;
    /**
     * {@snippet lang=c :
     * #define PAGE_WRITECOMBINE 1024
     * }
     */
    public static int PAGE_WRITECOMBINE() {
        return PAGE_WRITECOMBINE;
    }
    private static final int PAGE_GRAPHICS_NOACCESS = (int)2048L;
    /**
     * {@snippet lang=c :
     * #define PAGE_GRAPHICS_NOACCESS 2048
     * }
     */
    public static int PAGE_GRAPHICS_NOACCESS() {
        return PAGE_GRAPHICS_NOACCESS;
    }
    private static final int PAGE_GRAPHICS_READONLY = (int)4096L;
    /**
     * {@snippet lang=c :
     * #define PAGE_GRAPHICS_READONLY 4096
     * }
     */
    public static int PAGE_GRAPHICS_READONLY() {
        return PAGE_GRAPHICS_READONLY;
    }
    private static final int PAGE_GRAPHICS_READWRITE = (int)8192L;
    /**
     * {@snippet lang=c :
     * #define PAGE_GRAPHICS_READWRITE 8192
     * }
     */
    public static int PAGE_GRAPHICS_READWRITE() {
        return PAGE_GRAPHICS_READWRITE;
    }
    private static final int PAGE_GRAPHICS_EXECUTE = (int)16384L;
    /**
     * {@snippet lang=c :
     * #define PAGE_GRAPHICS_EXECUTE 16384
     * }
     */
    public static int PAGE_GRAPHICS_EXECUTE() {
        return PAGE_GRAPHICS_EXECUTE;
    }
    private static final int PAGE_GRAPHICS_EXECUTE_READ = (int)32768L;
    /**
     * {@snippet lang=c :
     * #define PAGE_GRAPHICS_EXECUTE_READ 32768
     * }
     */
    public static int PAGE_GRAPHICS_EXECUTE_READ() {
        return PAGE_GRAPHICS_EXECUTE_READ;
    }
    private static final int PAGE_GRAPHICS_EXECUTE_READWRITE = (int)65536L;
    /**
     * {@snippet lang=c :
     * #define PAGE_GRAPHICS_EXECUTE_READWRITE 65536
     * }
     */
    public static int PAGE_GRAPHICS_EXECUTE_READWRITE() {
        return PAGE_GRAPHICS_EXECUTE_READWRITE;
    }
    private static final int PAGE_GRAPHICS_COHERENT = (int)131072L;
    /**
     * {@snippet lang=c :
     * #define PAGE_GRAPHICS_COHERENT 131072
     * }
     */
    public static int PAGE_GRAPHICS_COHERENT() {
        return PAGE_GRAPHICS_COHERENT;
    }
    private static final int PAGE_GRAPHICS_NOCACHE = (int)262144L;
    /**
     * {@snippet lang=c :
     * #define PAGE_GRAPHICS_NOCACHE 262144
     * }
     */
    public static int PAGE_GRAPHICS_NOCACHE() {
        return PAGE_GRAPHICS_NOCACHE;
    }
    private static final int PAGE_TARGETS_NO_UPDATE = (int)1073741824L;
    /**
     * {@snippet lang=c :
     * #define PAGE_TARGETS_NO_UPDATE 1073741824
     * }
     */
    public static int PAGE_TARGETS_NO_UPDATE() {
        return PAGE_TARGETS_NO_UPDATE;
    }
    private static final int PAGE_TARGETS_INVALID = (int)1073741824L;
    /**
     * {@snippet lang=c :
     * #define PAGE_TARGETS_INVALID 1073741824
     * }
     */
    public static int PAGE_TARGETS_INVALID() {
        return PAGE_TARGETS_INVALID;
    }
    private static final int PAGE_ENCLAVE_UNVALIDATED = (int)536870912L;
    /**
     * {@snippet lang=c :
     * #define PAGE_ENCLAVE_UNVALIDATED 536870912
     * }
     */
    public static int PAGE_ENCLAVE_UNVALIDATED() {
        return PAGE_ENCLAVE_UNVALIDATED;
    }
    private static final int PAGE_ENCLAVE_MASK = (int)268435456L;
    /**
     * {@snippet lang=c :
     * #define PAGE_ENCLAVE_MASK 268435456
     * }
     */
    public static int PAGE_ENCLAVE_MASK() {
        return PAGE_ENCLAVE_MASK;
    }


    private static final int MEM_COMMIT = (int)4096L;
    /**
     * {@snippet lang=c :
     * #define MEM_COMMIT 4096
     * }
     */
    public static int MEM_COMMIT() {
        return MEM_COMMIT;
    }
    private static final int MEM_RESERVE = (int)8192L;
    /**
     * {@snippet lang=c :
     * #define MEM_RESERVE 8192
     * }
     */
    public static int MEM_RESERVE() {
        return MEM_RESERVE;
    }
    private static final int MEM_REPLACE_PLACEHOLDER = (int)16384L;
    /**
     * {@snippet lang=c :
     * #define MEM_REPLACE_PLACEHOLDER 16384
     * }
     */
    public static int MEM_REPLACE_PLACEHOLDER() {
        return MEM_REPLACE_PLACEHOLDER;
    }
    private static final int MEM_RESERVE_PLACEHOLDER = (int)262144L;
    /**
     * {@snippet lang=c :
     * #define MEM_RESERVE_PLACEHOLDER 262144
     * }
     */
    public static int MEM_RESERVE_PLACEHOLDER() {
        return MEM_RESERVE_PLACEHOLDER;
    }
    private static final int MEM_RESET = (int)524288L;
    /**
     * {@snippet lang=c :
     * #define MEM_RESET 524288
     * }
     */
    public static int MEM_RESET() {
        return MEM_RESET;
    }
    private static final int MEM_TOP_DOWN = (int)1048576L;
    /**
     * {@snippet lang=c :
     * #define MEM_TOP_DOWN 1048576
     * }
     */
    public static int MEM_TOP_DOWN() {
        return MEM_TOP_DOWN;
    }
    private static final int MEM_WRITE_WATCH = (int)2097152L;
    /**
     * {@snippet lang=c :
     * #define MEM_WRITE_WATCH 2097152
     * }
     */
    public static int MEM_WRITE_WATCH() {
        return MEM_WRITE_WATCH;
    }
    private static final int MEM_PHYSICAL = (int)4194304L;
    /**
     * {@snippet lang=c :
     * #define MEM_PHYSICAL 4194304
     * }
     */
    public static int MEM_PHYSICAL() {
        return MEM_PHYSICAL;
    }
    private static final int MEM_ROTATE = (int)8388608L;
    /**
     * {@snippet lang=c :
     * #define MEM_ROTATE 8388608
     * }
     */
    public static int MEM_ROTATE() {
        return MEM_ROTATE;
    }
    private static final int MEM_DIFFERENT_IMAGE_BASE_OK = (int)8388608L;
    /**
     * {@snippet lang=c :
     * #define MEM_DIFFERENT_IMAGE_BASE_OK 8388608
     * }
     */
    public static int MEM_DIFFERENT_IMAGE_BASE_OK() {
        return MEM_DIFFERENT_IMAGE_BASE_OK;
    }
    private static final int MEM_RESET_UNDO = (int)16777216L;
    /**
     * {@snippet lang=c :
     * #define MEM_RESET_UNDO 16777216
     * }
     */
    public static int MEM_RESET_UNDO() {
        return MEM_RESET_UNDO;
    }
    private static final int MEM_LARGE_PAGES = (int)536870912L;
    /**
     * {@snippet lang=c :
     * #define MEM_LARGE_PAGES 536870912
     * }
     */
    public static int MEM_LARGE_PAGES() {
        return MEM_LARGE_PAGES;
    }
    private static final int MEM_UNMAP_WITH_TRANSIENT_BOOST = (int)1L;
    /**
     * {@snippet lang=c :
     * #define MEM_UNMAP_WITH_TRANSIENT_BOOST 1
     * }
     */
    public static int MEM_UNMAP_WITH_TRANSIENT_BOOST() {
        return MEM_UNMAP_WITH_TRANSIENT_BOOST;
    }
    private static final int MEM_COALESCE_PLACEHOLDERS = (int)1L;
    /**
     * {@snippet lang=c :
     * #define MEM_COALESCE_PLACEHOLDERS 1
     * }
     */
    public static int MEM_COALESCE_PLACEHOLDERS() {
        return MEM_COALESCE_PLACEHOLDERS;
    }
    private static final int MEM_PRESERVE_PLACEHOLDER = (int)2L;
    /**
     * {@snippet lang=c :
     * #define MEM_PRESERVE_PLACEHOLDER 2
     * }
     */
    public static int MEM_PRESERVE_PLACEHOLDER() {
        return MEM_PRESERVE_PLACEHOLDER;
    }
    private static final int MEM_DECOMMIT = (int)16384L;
    /**
     * {@snippet lang=c :
     * #define MEM_DECOMMIT 16384
     * }
     */
    public static int MEM_DECOMMIT() {
        return MEM_DECOMMIT;
    }
    private static final int MEM_RELEASE = (int)32768L;
    /**
     * {@snippet lang=c :
     * #define MEM_RELEASE 32768
     * }
     */
    public static int MEM_RELEASE() {
        return MEM_RELEASE;
    }
    private static final int MEM_FREE = (int)65536L;
    /**
     * {@snippet lang=c :
     * #define MEM_FREE 65536
     * }
     */
    public static int MEM_FREE() {
        return MEM_FREE;
    }
    private static final int MEM_EXTENDED_PARAMETER_GRAPHICS = (int)1L;
    /**
     * {@snippet lang=c :
     * #define MEM_EXTENDED_PARAMETER_GRAPHICS 1
     * }
     */
    public static int MEM_EXTENDED_PARAMETER_GRAPHICS() {
        return MEM_EXTENDED_PARAMETER_GRAPHICS;
    }
    private static final int MEM_EXTENDED_PARAMETER_NONPAGED = (int)2L;
    /**
     * {@snippet lang=c :
     * #define MEM_EXTENDED_PARAMETER_NONPAGED 2
     * }
     */
    public static int MEM_EXTENDED_PARAMETER_NONPAGED() {
        return MEM_EXTENDED_PARAMETER_NONPAGED;
    }
    private static final int MEM_EXTENDED_PARAMETER_ZERO_PAGES_OPTIONAL = (int)4L;
    /**
     * {@snippet lang=c :
     * #define MEM_EXTENDED_PARAMETER_ZERO_PAGES_OPTIONAL 4
     * }
     */
    public static int MEM_EXTENDED_PARAMETER_ZERO_PAGES_OPTIONAL() {
        return MEM_EXTENDED_PARAMETER_ZERO_PAGES_OPTIONAL;
    }
    private static final int MEM_EXTENDED_PARAMETER_NONPAGED_LARGE = (int)8L;
    /**
     * {@snippet lang=c :
     * #define MEM_EXTENDED_PARAMETER_NONPAGED_LARGE 8
     * }
     */
    public static int MEM_EXTENDED_PARAMETER_NONPAGED_LARGE() {
        return MEM_EXTENDED_PARAMETER_NONPAGED_LARGE;
    }
    private static final int MEM_EXTENDED_PARAMETER_NONPAGED_HUGE = (int)16L;
    /**
     * {@snippet lang=c :
     * #define MEM_EXTENDED_PARAMETER_NONPAGED_HUGE 16
     * }
     */
    public static int MEM_EXTENDED_PARAMETER_NONPAGED_HUGE() {
        return MEM_EXTENDED_PARAMETER_NONPAGED_HUGE;
    }
    private static final int MEM_EXTENDED_PARAMETER_SOFT_FAULT_PAGES = (int)32L;
    /**
     * {@snippet lang=c :
     * #define MEM_EXTENDED_PARAMETER_SOFT_FAULT_PAGES 32
     * }
     */
    public static int MEM_EXTENDED_PARAMETER_SOFT_FAULT_PAGES() {
        return MEM_EXTENDED_PARAMETER_SOFT_FAULT_PAGES;
    }
    private static final int MEM_EXTENDED_PARAMETER_EC_CODE = (int)64L;
    /**
     * {@snippet lang=c :
     * #define MEM_EXTENDED_PARAMETER_EC_CODE 64
     * }
     */
    public static int MEM_EXTENDED_PARAMETER_EC_CODE() {
        return MEM_EXTENDED_PARAMETER_EC_CODE;
    }
    private static final int MEM_EXTENDED_PARAMETER_IMAGE_NO_HPAT = (int)128L;
    /**
     * {@snippet lang=c :
     * #define MEM_EXTENDED_PARAMETER_IMAGE_NO_HPAT 128
     * }
     */
    public static int MEM_EXTENDED_PARAMETER_IMAGE_NO_HPAT() {
        return MEM_EXTENDED_PARAMETER_IMAGE_NO_HPAT;
    }
    private static final int MEM_EXTENDED_PARAMETER_TYPE_BITS = (int)8L;
    /**
     * {@snippet lang=c :
     * #define MEM_EXTENDED_PARAMETER_TYPE_BITS 8
     * }
     */
    public static int MEM_EXTENDED_PARAMETER_TYPE_BITS() {
        return MEM_EXTENDED_PARAMETER_TYPE_BITS;
    }
    private static final int SEC_HUGE_PAGES = (int)131072L;
}
