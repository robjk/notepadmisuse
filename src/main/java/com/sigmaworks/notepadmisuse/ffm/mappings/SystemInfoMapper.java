package com.sigmaworks.notepadmisuse.ffm.mappings;

import com.sigmaworks.notepadmisuse.ffm.bindings.sysinfo.SystemInfoBinding;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.VarHandle;

public class SystemInfoMapper {
    /*
            MemoryLayout.unionLayout(
                    psapi_h.C_LONG.withName("dwOemId"),
                    MemoryLayout.structLayout(
                            psapi_h.C_SHORT.withName("wProcessorArchitecture"),
                            psapi_h.C_SHORT.withName("wReserved")
                    ).withName("$anon$50:9")
            ).withName("$anon$48:5"),
            psapi_h.C_LONG.withName("dwPageSize"),
            psapi_h.C_POINTER.withName("lpMinimumApplicationAddress"),
            psapi_h.C_POINTER.withName("lpMaximumApplicationAddress"),
            psapi_h.C_LONG_LONG.withName("dwActiveProcessorMask"),
            psapi_h.C_LONG.withName("dwNumberOfProcessors"),
            psapi_h.C_LONG.withName("dwProcessorType"),
            psapi_h.C_LONG.withName("dwAllocationGranularity"),
            psapi_h.C_SHORT.withName("wProcessorLevel"),
            psapi_h.C_SHORT.withName("wProcessorRevision")
    ).withName("_SYSTEM_INFO");
     */

    public record SystemInfoRecord(int pageSize,
                                   long minimumApplicationAddress,
                                   long maximumApplicationAddress,
                                   long activeProcessorMask,
                                   int numberOfProcessors,
                                   int processorType,
                                   int allocationGranularity,
                                   short processorLevel,
                                   short processorRevision) {
    }

    static final VarHandle PAGE_SIZE = varHandle("dwPageSize");
    static final VarHandle MIN_APPLICATION_ADDRESS = varHandle("lpMinimumApplicationAddress");
    static final VarHandle MAX_APPLICATION_ADDRESS = varHandle("lpMaximumApplicationAddress");
    static final VarHandle ACTIVE_PROCESSOR_MASK = varHandle("dwActiveProcessorMask");
    static final VarHandle NO_OF_PROCESSORS = varHandle("dwNumberOfProcessors");
    static final VarHandle PROCESSOR_TYPE = varHandle("dwProcessorType");
    static final VarHandle ALLOCATION_GRANULARITY = varHandle("dwAllocationGranularity");
    static final VarHandle PROCESSOR_LEVEL = varHandle("wProcessorLevel");
    static final VarHandle PROCESSOR_REVISION = varHandle("wProcessorRevision");

    private static VarHandle varHandle(String fieldName) {
        return SystemInfoBinding.layout().varHandle(MemoryLayout.PathElement.groupElement(fieldName));
    }

    public static final RecordMapper<SystemInfoRecord> SYSTEM_INFO_MAPPER = new RecordMapper<>() {

        @Override
        public SystemInfoRecord get(MemorySegment s) {
            return new SystemInfoRecord((int) PAGE_SIZE.get(s, 0L),
                    MappingUtil.getAddress(s, MIN_APPLICATION_ADDRESS),
                    MappingUtil.getAddress(s, MAX_APPLICATION_ADDRESS),
                    (long) ACTIVE_PROCESSOR_MASK.get(s, 0),
                    (int) NO_OF_PROCESSORS.get(s, 0),
                    (int) PROCESSOR_TYPE.get(s, 0),
                    (int) ALLOCATION_GRANULARITY.get(s, 0),
                    (short) PROCESSOR_LEVEL.get(s, 0),
                    (short) PROCESSOR_REVISION.get(s, 0)
            );
        }

        @Override
        public void set(MemorySegment segment, SystemInfoRecord value) {
            throw new UnsupportedOperationException("not yet implemented");
        }

        @Override
        public MemoryLayout layout() {
            return SystemInfoBinding.layout();
        }
    };
}
