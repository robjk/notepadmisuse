package com.sigmaworks.notepadmisuse.ffm.bindings.psapi;

import java.lang.foreign.*;
import java.util.function.Consumer;

import static com.sigmaworks.notepadmisuse.ffm.Kernel32Common.C_LONG;
import static com.sigmaworks.notepadmisuse.ffm.Kernel32Common.C_POINTER;
import static java.lang.foreign.MemoryLayout.PathElement.groupElement;
import static java.lang.foreign.ValueLayout.OfInt;

/**
 * {@snippet lang = c:
 * struct _MODULEINFO {
 *     LPVOID lpBaseOfDll;
 *     DWORD SizeOfImage;
 *     LPVOID EntryPoint;
 * }
 *}
 */
@SuppressWarnings({"FinalStaticMethod", "unused"})
public class ModuleInfoBinding {

    ModuleInfoBinding() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
            C_POINTER.withName("lpBaseOfDll"),
            C_LONG.withName("SizeOfImage"),
            MemoryLayout.paddingLayout(4),
            C_POINTER.withName("EntryPoint")
    ).withName("_MODULEINFO");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final AddressLayout lpBaseOfDll$LAYOUT = (AddressLayout) $LAYOUT.select(groupElement("lpBaseOfDll"));

    /**
     * Layout for field:
     * {@snippet lang = c:
     * LPVOID lpBaseOfDll
     *}
     */
    public static final AddressLayout lpBaseOfDll$layout() {
        return lpBaseOfDll$LAYOUT;
    }

    private static final long lpBaseOfDll$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang = c:
     * LPVOID lpBaseOfDll
     *}
     */
    public static final long lpBaseOfDll$offset() {
        return lpBaseOfDll$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang = c:
     * LPVOID lpBaseOfDll
     *}
     */
    public static MemorySegment lpBaseOfDll(MemorySegment struct) {
        return struct.get(lpBaseOfDll$LAYOUT, lpBaseOfDll$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang = c:
     * LPVOID lpBaseOfDll
     *}
     */
    public static void lpBaseOfDll(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(lpBaseOfDll$LAYOUT, lpBaseOfDll$OFFSET, fieldValue);
    }

    private static final OfInt SizeOfImage$LAYOUT = (OfInt) $LAYOUT.select(groupElement("SizeOfImage"));

    /**
     * Layout for field:
     * {@snippet lang = c:
     * DWORD SizeOfImage
     *}
     */
    public static final OfInt SizeOfImage$layout() {
        return SizeOfImage$LAYOUT;
    }

    private static final long SizeOfImage$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang = c:
     * DWORD SizeOfImage
     *}
     */
    public static final long SizeOfImage$offset() {
        return SizeOfImage$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang = c:
     * DWORD SizeOfImage
     *}
     */
    public static int SizeOfImage(MemorySegment struct) {
        return struct.get(SizeOfImage$LAYOUT, SizeOfImage$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang = c:
     * DWORD SizeOfImage
     *}
     */
    public static void SizeOfImage(MemorySegment struct, int fieldValue) {
        struct.set(SizeOfImage$LAYOUT, SizeOfImage$OFFSET, fieldValue);
    }

    private static final AddressLayout EntryPoint$LAYOUT = (AddressLayout) $LAYOUT.select(groupElement("EntryPoint"));

    /**
     * Layout for field:
     * {@snippet lang = c:
     * LPVOID EntryPoint
     *}
     */
    public static final AddressLayout EntryPoint$layout() {
        return EntryPoint$LAYOUT;
    }

    private static final long EntryPoint$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang = c:
     * LPVOID EntryPoint
     *}
     */
    public static final long EntryPoint$offset() {
        return EntryPoint$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang = c:
     * LPVOID EntryPoint
     *}
     */
    public static MemorySegment EntryPoint(MemorySegment struct) {
        return struct.get(EntryPoint$LAYOUT, EntryPoint$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang = c:
     * LPVOID EntryPoint
     *}
     */
    public static void EntryPoint(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(EntryPoint$LAYOUT, EntryPoint$OFFSET, fieldValue);
    }

    /**
     * Obtains a slice of {@code arrayParam} which selects the array element at {@code index}.
     * The returned segment has address {@code arrayParam.address() + index * layout().byteSize()}
     */
    public static MemorySegment asSlice(MemorySegment array, long index) {
        return array.asSlice(layout().byteSize() * index);
    }

    /**
     * The size (in bytes) of this struct
     */
    public static long sizeof() {
        return layout().byteSize();
    }

    /**
     * Allocate a segment of size {@code layout().byteSize()} using {@code allocator}
     */
    public static MemorySegment allocate(SegmentAllocator allocator) {
        return allocator.allocate(layout());
    }

    /**
     * Allocate an array of size {@code elementCount} using {@code allocator}.
     * The returned segment has size {@code elementCount * layout().byteSize()}.
     */
    public static MemorySegment allocateArray(long elementCount, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(elementCount, layout()));
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction} (if any).
     * The returned segment has size {@code layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, Arena arena, Consumer<MemorySegment> cleanup) {
        return reinterpret(addr, 1, arena, cleanup);
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction} (if any).
     * The returned segment has size {@code elementCount * layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, long elementCount, Arena arena, Consumer<MemorySegment> cleanup) {
        return addr.reinterpret(layout().byteSize() * elementCount, arena, cleanup);
    }
}

