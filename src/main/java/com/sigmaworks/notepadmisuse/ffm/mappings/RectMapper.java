package com.sigmaworks.notepadmisuse.ffm.mappings;

import com.sigmaworks.notepadmisuse.ffm.bindings.winuser.RectBinding;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.VarHandle;

public class RectMapper {
    /**
     * {@snippet lang = c:
     * struct _RECTL {
     *     LONG left;
     *     LONG top;
     *     LONG right;
     *     LONG bottom;
     * }
     *}
     */

    public record RectRecord(int left, int top, int right, int bottom) {
    }

    static final VarHandle LEFT = varHandle("left");
    static final VarHandle TOP = varHandle("top");
    static final VarHandle RIGHT = varHandle("right");
    static final VarHandle BOTTOM = varHandle("bottom");

    private static VarHandle varHandle(String fieldName) {
        return RectBinding.layout().varHandle(MemoryLayout.PathElement.groupElement(fieldName));
    }

    public static final RecordMapper<RectRecord> RECT_MAPPER = new RecordMapper<>() {
        @Override
        public RectRecord get(MemorySegment s) {
            return new RectRecord(
                    (int) LEFT.get(s, 0L),
                    (int) TOP.get(s, 0L),
                    (int) RIGHT.get(s, 0L),
                    (int) BOTTOM.get(s, 0L));
        }

        @Override
        public void set(MemorySegment segment, RectRecord value) {
            LEFT.set(segment, 0L, value.left);
            TOP.set(segment, 0L, value.top);
            RIGHT.set(segment, 0L, value.right);
            BOTTOM.set(segment, 0L, value.bottom);
        }

        @Override
        public MemoryLayout layout() {
            return RectBinding.layout();
        }
    };

}
