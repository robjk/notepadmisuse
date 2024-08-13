package com.sigmaworks.notepadmisuse.ffm.mappings;

import com.sigmaworks.notepadmisuse.ffm.bindings.winnt.OsVersionInfoExABinding;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.VarHandle;

import static java.lang.foreign.ValueLayout.PathElement;

public class OsVersionInfoExAMapper {

    public record OsVersionInfoExARecord(long osVersionInfoSize,
                                         long majorVersion,
                                         long minorVersion,
                                         long buildNumber,
                                         long platformId,
                                         short servicePackMajor,
                                         short servicePackMinor,
                                         short suiteMask,
                                         byte productType,
                                         byte reserved) {
    }

    static final VarHandle OS_VERSION_INFO_SIZE = varHandle("dwOSVersionInfoSize");
    static final VarHandle MAJOR_VERSION = varHandle("dwMajorVersion");
    static final VarHandle MINOR_VERSION = varHandle("dwMinorVersion");
    static final VarHandle BUILD_NUMBER = varHandle("dwBuildNumber");
    static final VarHandle PLATFORM_ID = varHandle("dwPlatformId");
//    static final VarHandle CSDVersion = varHandle("szCSDVersion");
    static final VarHandle SERVICE_PACK_MAJOR = varHandle("wServicePackMajor");
    static final VarHandle SERVICE_PACK_MINOR = varHandle("wServicePackMinor");
    static final VarHandle SUITE_MASK = varHandle("wSuiteMask");
    static final VarHandle PRODUCT_TYPE = varHandle("wProductType");
    static final VarHandle RESERVED = varHandle("wReserved");

    private static VarHandle varHandle(String fieldName) {
        return OsVersionInfoExABinding.layout().varHandle(PathElement.groupElement(fieldName));
    }

    public static final RecordMapper<OsVersionInfoExARecord> OS_VERSION_INFO_EX_A_MAPPER = new RecordMapper<>() {

        @Override
        public OsVersionInfoExARecord get(MemorySegment s) {
            return new OsVersionInfoExARecord((long) OS_VERSION_INFO_SIZE.get(s, 0L),
                    (long) MAJOR_VERSION.get(s, 0L),
                    (long) MINOR_VERSION.get(s, 0L),
                    (long) BUILD_NUMBER.get(s, 0L),
                    (long) PLATFORM_ID.get(s, 0L),
                    (short) SERVICE_PACK_MAJOR.get(s, 0L),
                    (short) SERVICE_PACK_MINOR.get(s, 0L),
                    (short) SUITE_MASK.get(s, 0L),
                    (byte) PRODUCT_TYPE.get(s, 0L),
                    (byte) RESERVED.get(s, 0L));
        }

        @Override
        public void set(MemorySegment segment, OsVersionInfoExARecord value) {
            throw new UnsupportedOperationException("not yet implemented");
        }

        @Override
        public MemoryLayout layout() {
            return OsVersionInfoExABinding.layout();
        }
    };
}