package com.sigmaworks.notepadmisuse.ffm.bindings.winnt;

import com.sigmaworks.notepadmisuse.ffm.Kernel32Common;

import java.lang.foreign.*;
import java.util.function.Consumer;

import static java.lang.foreign.MemoryLayout.PathElement.groupElement;
import static java.lang.foreign.ValueLayout.*;

/**
 * {@snippet lang=c :
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
 * }
 */
@SuppressWarnings({"FinalStaticMethod", "unused"})
public class MemoryBasicInformationBinding {

    MemoryBasicInformationBinding() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
            Kernel32Common.C_POINTER.withName("BaseAddress"),
            Kernel32Common.C_POINTER.withName("AllocationBase"),
            Kernel32Common.C_LONG.withName("AllocationProtect"),
            Kernel32Common.C_SHORT.withName("PartitionId"),
            MemoryLayout.paddingLayout(2),
            Kernel32Common.C_LONG_LONG.withName("RegionSize"),
            Kernel32Common.C_LONG.withName("State"),
            Kernel32Common.C_LONG.withName("Protect"),
            Kernel32Common.C_LONG.withName("Type"),
            MemoryLayout.paddingLayout(4)
    ).withName("_MEMORY_BASIC_INFORMATION");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final AddressLayout BaseAddress$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("BaseAddress"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * PVOID BaseAddress
     * }
     */
    public static final AddressLayout BaseAddress$layout() {
        return BaseAddress$LAYOUT;
    }

    private static final long BaseAddress$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * PVOID BaseAddress
     * }
     */
    public static final long BaseAddress$offset() {
        return BaseAddress$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * PVOID BaseAddress
     * }
     */
    public static MemorySegment BaseAddress(MemorySegment struct) {
        return struct.get(BaseAddress$LAYOUT, BaseAddress$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * PVOID BaseAddress
     * }
     */
    public static void BaseAddress(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(BaseAddress$LAYOUT, BaseAddress$OFFSET, fieldValue);
    }

    private static final AddressLayout AllocationBase$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("AllocationBase"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * PVOID AllocationBase
     * }
     */
    public static final AddressLayout AllocationBase$layout() {
        return AllocationBase$LAYOUT;
    }

    private static final long AllocationBase$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * PVOID AllocationBase
     * }
     */
    public static final long AllocationBase$offset() {
        return AllocationBase$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * PVOID AllocationBase
     * }
     */
    public static MemorySegment AllocationBase(MemorySegment struct) {
        return struct.get(AllocationBase$LAYOUT, AllocationBase$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * PVOID AllocationBase
     * }
     */
    public static void AllocationBase(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(AllocationBase$LAYOUT, AllocationBase$OFFSET, fieldValue);
    }

    private static final OfInt AllocationProtect$LAYOUT = (OfInt)$LAYOUT.select(groupElement("AllocationProtect"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * DWORD AllocationProtect
     * }
     */
    public static final OfInt AllocationProtect$layout() {
        return AllocationProtect$LAYOUT;
    }

    private static final long AllocationProtect$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * DWORD AllocationProtect
     * }
     */
    public static final long AllocationProtect$offset() {
        return AllocationProtect$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * DWORD AllocationProtect
     * }
     */
    public static int AllocationProtect(MemorySegment struct) {
        return struct.get(AllocationProtect$LAYOUT, AllocationProtect$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * DWORD AllocationProtect
     * }
     */
    public static void AllocationProtect(MemorySegment struct, int fieldValue) {
        struct.set(AllocationProtect$LAYOUT, AllocationProtect$OFFSET, fieldValue);
    }

    private static final OfShort PartitionId$LAYOUT = (OfShort)$LAYOUT.select(groupElement("PartitionId"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * WORD PartitionId
     * }
     */
    public static final OfShort PartitionId$layout() {
        return PartitionId$LAYOUT;
    }

    private static final long PartitionId$OFFSET = 20;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * WORD PartitionId
     * }
     */
    public static final long PartitionId$offset() {
        return PartitionId$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * WORD PartitionId
     * }
     */
    public static short PartitionId(MemorySegment struct) {
        return struct.get(PartitionId$LAYOUT, PartitionId$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * WORD PartitionId
     * }
     */
    public static void PartitionId(MemorySegment struct, short fieldValue) {
        struct.set(PartitionId$LAYOUT, PartitionId$OFFSET, fieldValue);
    }

    private static final OfLong RegionSize$LAYOUT = (OfLong)$LAYOUT.select(groupElement("RegionSize"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * SIZE_T RegionSize
     * }
     */
    public static final OfLong RegionSize$layout() {
        return RegionSize$LAYOUT;
    }

    private static final long RegionSize$OFFSET = 24;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * SIZE_T RegionSize
     * }
     */
    public static final long RegionSize$offset() {
        return RegionSize$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * SIZE_T RegionSize
     * }
     */
    public static long RegionSize(MemorySegment struct) {
        return struct.get(RegionSize$LAYOUT, RegionSize$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * SIZE_T RegionSize
     * }
     */
    public static void RegionSize(MemorySegment struct, long fieldValue) {
        struct.set(RegionSize$LAYOUT, RegionSize$OFFSET, fieldValue);
    }

    private static final OfInt State$LAYOUT = (OfInt)$LAYOUT.select(groupElement("State"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * DWORD State
     * }
     */
    public static final OfInt State$layout() {
        return State$LAYOUT;
    }

    private static final long State$OFFSET = 32;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * DWORD State
     * }
     */
    public static final long State$offset() {
        return State$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * DWORD State
     * }
     */
    public static int State(MemorySegment struct) {
        return struct.get(State$LAYOUT, State$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * DWORD State
     * }
     */
    public static void State(MemorySegment struct, int fieldValue) {
        struct.set(State$LAYOUT, State$OFFSET, fieldValue);
    }

    private static final OfInt Protect$LAYOUT = (OfInt)$LAYOUT.select(groupElement("Protect"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * DWORD Protect
     * }
     */
    public static final OfInt Protect$layout() {
        return Protect$LAYOUT;
    }

    private static final long Protect$OFFSET = 36;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * DWORD Protect
     * }
     */
    public static final long Protect$offset() {
        return Protect$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * DWORD Protect
     * }
     */
    public static int Protect(MemorySegment struct) {
        return struct.get(Protect$LAYOUT, Protect$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * DWORD Protect
     * }
     */
    public static void Protect(MemorySegment struct, int fieldValue) {
        struct.set(Protect$LAYOUT, Protect$OFFSET, fieldValue);
    }

    private static final OfInt Type$LAYOUT = (OfInt)$LAYOUT.select(groupElement("Type"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * DWORD Type
     * }
     */
    public static final OfInt Type$layout() {
        return Type$LAYOUT;
    }

    private static final long Type$OFFSET = 40;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * DWORD Type
     * }
     */
    public static final long Type$offset() {
        return Type$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * DWORD Type
     * }
     */
    public static int Type(MemorySegment struct) {
        return struct.get(Type$LAYOUT, Type$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * DWORD Type
     * }
     */
    public static void Type(MemorySegment struct, int fieldValue) {
        struct.set(Type$LAYOUT, Type$OFFSET, fieldValue);
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
    public static long sizeof() { return layout().byteSize(); }

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

