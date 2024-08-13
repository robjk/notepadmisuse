package com.sigmaworks.notepadmisuse.ffm.mappings;

import com.sigmaworks.notepadmisuse.ffm.bindings.winnt.MemoryBasicInformationBinding;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.VarHandle;

import static com.sigmaworks.notepadmisuse.ffm.mappings.MappingUtil.getAddress;

public class MemoryBasicInformationMapper {
    /**
     * {@snippet lang = c:
     * struct _MEMORY_BASIC_INFORMATION {
     *     PVOID BaseAddress;
     *     PVOID AllocationBase;
     *     DWORD AllocationProtect;
     *     WORD PartitionId;
     *     SIZE_T RegionSize;
     *     DWORD State;
     *     DWORD Protect;
     *     DWORD Type;
     * }
     *}
     */

    public record MemoryBasicInformationRecord(
            long baseAddress,
            long allocationBase,
            int allocationProtect,
            short partitionId,
            long regionSize,
            int state,
            int protect,
            int type) {
    }

    static final VarHandle BASE_ADDRESS = varHandle("BaseAddress");
    static final VarHandle ALLOCATION_BASE = varHandle("AllocationBase");
    static final VarHandle ALLOCATION_PROTECT = varHandle("AllocationProtect");
    static final VarHandle PARTITION_ID = varHandle("PartitionId");
    static final VarHandle REGION_SIZE = varHandle("RegionSize");
    static final VarHandle STATE = varHandle("State");
    static final VarHandle PROTECT = varHandle("Protect");
    static final VarHandle TYPE = varHandle("Type");

    private static VarHandle varHandle(String fieldName) {
        return MemoryBasicInformationBinding.layout().varHandle(MemoryLayout.PathElement.groupElement(fieldName));
    }

    public static final RecordMapper<MemoryBasicInformationRecord> MEMORY_BASIC_INFORMATION_MAPPER = new RecordMapper<>() {

        @Override
        public MemoryBasicInformationRecord get(MemorySegment s) {
            return new MemoryBasicInformationRecord(
                    getAddress(s, BASE_ADDRESS),
                    getAddress(s, ALLOCATION_BASE),
                    (int) ALLOCATION_PROTECT.get(s, 0L),
                    (short) PARTITION_ID.get(s, 0L),
                    (long) REGION_SIZE.get(s, 0L),
                    (int) STATE.get(s, 0L),
                    (int) PROTECT.get(s, 0L),
                    (int) TYPE.get(s, 0L));
        }

        @Override
        public void set(MemorySegment segment, MemoryBasicInformationRecord value) {
            throw new UnsupportedOperationException("not yet implemented");
        }

        @Override
        public MemoryLayout layout() {
            return MemoryBasicInformationBinding.layout();
        }
    };
}
