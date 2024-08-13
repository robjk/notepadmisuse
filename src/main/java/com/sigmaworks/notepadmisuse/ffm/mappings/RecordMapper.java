package com.sigmaworks.notepadmisuse.ffm.mappings;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

public interface RecordMapper<T extends Record> {
    T get(MemorySegment segment);

    void set(MemorySegment segment, T value);

    MemoryLayout layout();
}
