package com.sigmaworks.notepadmisuse.ffm.mappings;

import com.sigmaworks.notepadmisuse.ffm.bindings.psapi.ModuleInfoBinding;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.VarHandle;

public class ModuleInfoMapper {

    public record ModuleInfoRecord(
            long baseOfDll,
            int sizeOfImage,
            long entryPoint) {
    }

    static final VarHandle BASE_OF_DLL = varHandle("lpBaseOfDll");
    static final VarHandle SIZE_OF_IMAGE = varHandle("SizeOfImage");
    static final VarHandle ENTRY_POINT = varHandle("EntryPoint");

    private static VarHandle varHandle(String fieldName) {
        return ModuleInfoBinding.layout().varHandle(MemoryLayout.PathElement.groupElement(fieldName));
    }

    public static final RecordMapper<ModuleInfoRecord> MODULE_INFO_MAPPER = new RecordMapper<>() {

        @Override
        public ModuleInfoRecord get(MemorySegment s) {
            MemorySegment baseOfDll = (MemorySegment) BASE_OF_DLL.get(s, 0L);
            MemorySegment entryPoint = (MemorySegment) ENTRY_POINT.get(s, 0L);

            return new ModuleInfoRecord(baseOfDll.address(),
                    (int) SIZE_OF_IMAGE.get(s, 0L),
                    entryPoint.address());
        }

        @Override
        public void set(MemorySegment segment, ModuleInfoRecord value) {
            throw new UnsupportedOperationException("not yet implemented");
        }

        @Override
        public MemoryLayout layout() {
            return ModuleInfoBinding.layout();
        }
    };
}
