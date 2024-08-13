package com.sigmaworks.notepadmisuse.ffm.mappings;

import java.lang.foreign.MemorySegment;
import java.lang.invoke.VarHandle;

public class MappingUtil {

    public static long getAddress(MemorySegment s, VarHandle memorySegmentVarHandle) {
        MemorySegment ms = (MemorySegment) memorySegmentVarHandle.get(s, 0);
        return ms.address();
    }
}