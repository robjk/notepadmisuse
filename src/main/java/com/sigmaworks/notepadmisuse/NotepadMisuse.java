package com.sigmaworks.notepadmisuse;

import com.sigmaworks.notepadmisuse.animation.AnimationOrchestrator;
import com.sigmaworks.notepadmisuse.ffm.bindings.winnt.MemoryProtectionConstants;
import com.sigmaworks.notepadmisuse.ffm.mappings.MemoryBasicInformationMapper.MemoryBasicInformationRecord;
import com.sigmaworks.notepadmisuse.ffm.mappings.SystemInfoMapper.SystemInfoRecord;
import com.sigmaworks.notepadmisuse.util.GeneralUtils;
import com.sigmaworks.notepadmisuse.util.StatusLine;

import java.io.IOException;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

import static com.sigmaworks.notepadmisuse.animation.AsciiAnimation.badApple172x62;
import static com.sigmaworks.notepadmisuse.ffm.WinOS.*;
import static com.sigmaworks.notepadmisuse.ffm.bindings.processthreadsapi.ProcessThreadsConstants.PROCESS_ALL_ACCESS;
import static com.sigmaworks.notepadmisuse.ffm.bindings.winnt.MemoryProtectionConstants.*;
import static com.sigmaworks.notepadmisuse.util.StatusLine.Outcome.*;

public class NotepadMisuse {

    private static final StatusLine status = new StatusLine();

    public static final String PROCESS_NAME = "notepad.exe";
    public static final String NOTEPAD_SHA_256_HASH = "c286747d319818c1205fd487040840353c5f4542faffdabae9131481bdfeb92a";
    public static final byte[] START_MARKER = "Foreign Functions & Memory".getBytes(StandardCharsets.UTF_16LE);
    public static final byte[] END_MARKER = "yromeM & snoitcnuF ngieroF".getBytes(StandardCharsets.UTF_16LE);

    static {
        System.loadLibrary("kernel32");
        System.loadLibrary("user32");
    }

void main() {

        status.log("spawn a notepad.exe instance", ONGOING);
        Process notepadProcess = startNotepad(badApple172x62.template());

        // hunt for a notepad instance that contains our marker strings
        status.log("find pid for notepad.exe", NEXT);
        int notepadPid = resolveNotepadPid();
        status.log("resolved notepad pid to " + notepadPid, SUCCESS);

        status.log("retrieving system information", ONGOING);
        MemorySegment hProc = OpenProcess(notepadPid, PROCESS_ALL_ACCESS());

        status.log("retrieving system information", ONGOING);
        SystemInfoRecord systemInfoRecord = GetSystemInfo();
        status.log("retrieved system information", SUCCESS);

        // Hunt for the start and end markers within the template file previously loaded into notepad
        // Note that multiple matches can be found, this program is not smart enough to disambiguate such a situation
        // and will just use the memory between the first occurrence of the start and end marker(s)
        status.log("searching for start token");
        List<Long> startTokenAddresses = findBytes(hProc, systemInfoRecord, START_MARKER);
        startTokenAddresses.forEach(aLong -> status.log("found start token at: %x".formatted(aLong), SUCCESS));

        status.log("searching for end token");
        List<Long> endTokenAddresses = findBytes(hProc, systemInfoRecord, END_MARKER);
        endTokenAddresses.forEach(aLong -> status.log("found end token at  : %x".formatted(aLong), SUCCESS));

        status.log("searching for notepad main window");
        long hWndNotepad = findNotepadEditWindow(notepadPid);
        status.log("notepad's child hWnd %x has been found".formatted(hWndNotepad), SUCCESS);

        // at this point we have all we need:
        // 1. the address of the text view buffer within the notepad process, which we can use as a display buffer
        // 2. the handle for the notepad window, so we can force it to be redrawn when a frame update has been completed

        AnimationOrchestrator orchestrator = new AnimationOrchestrator(hProc,
                hWndNotepad,
                startTokenAddresses.getFirst(),
                endTokenAddresses.getLast());
        orchestrator.animate(15_000);

        status.log("closing process handle", ONGOING);
        CloseHandle(hProc);
        status.log("closed process handle", SUCCESS);

        status.log("terminating process", SUCCESS);
        notepadProcess.destroy();
    }

    /**
     * start a notepad process and open the template file containing the markers tokens we'll be searching for
     *
     * @param template path to template file containing START_MARKER and END_MARKER
     * @return process instance
     */
    private static Process startNotepad(String template) {
        try {

            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("SHA-256");
                byte[] notepadBinary = Files.readAllBytes(Path.of("C:\\Windows\\notepad.exe"));
                byte[] encodedhash = digest.digest(notepadBinary);
                String hash = HexFormat.of().formatHex(encodedhash);
                if (hash.equals(NOTEPAD_SHA_256_HASH)) {
                    status.log("notepad.exe hash check passed", SUCCESS);
                } else {
                    status.log("notepad.exe hash failure\n\texpected = %s\n\tactual  = %s\n\tcontinuing but all bets are off".formatted(NOTEPAD_SHA_256_HASH, hash), FAILURE);
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            Path templateFile = Files.createTempFile("notepad", "txt");
            templateFile.toFile().deleteOnExit();
            Files.write(templateFile, GeneralUtils.readResource(template));
            String templateFilePath = templateFile.toAbsolutePath().toString();

            ProcessBuilder processBuilder = new ProcessBuilder("C:\\Windows\\notepad.exe", templateFilePath);
            Process process = processBuilder.start();

            int timeout = 20;
            while (!process.isAlive() && timeout-- > 0) {
                try {
                    status.log("waiting for notepad to start", ONGOING);
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (process.isAlive()) {
                status.log("notepad started", SUCCESS);
            } else {
                status.log("notepad failed to start", FAILURE);
                throw new RuntimeException("unable to start notepad process");
            }
            return process;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Enumerate all running processes, extarct the base name for each and compare with the required process name.
     * <p>
     * Note that if multiple notepad instances are running, this is not smart enough to scan them all
     *
     * @return the pid for (a) notepad.exe
     */
    private static int resolveNotepadPid() {
        int pidResolvingAttempts = 0;
        int notepadPid = -1;

        while (pidResolvingAttempts < 5 && notepadPid == -1) {
            int[] pids = K32EnumProcesses();
            status.log("pid resolving attempt, %d pids to scan %d".formatted(pids.length, pidResolvingAttempts), ONGOING);

            for (int pid : pids) {
                status.log("inspecting pid " + pid, ONGOING);
                MemorySegment hProc = OpenProcess(pid, PROCESS_ALL_ACCESS());

                if (!hProc.equals(MemorySegment.NULL)) {
                    try {
                        long[] enumProcessModules = K32EnumProcessModules(hProc, true);
                        if (enumProcessModules.length == 0) {
                            status.log("no process modules available for pid " + pid, FAILURE);
                            continue;
                        }

                        String processName = K32GetModuleBaseNameA(hProc, enumProcessModules[0]);
                        if (processName.equals(PROCESS_NAME)) {
                            notepadPid = pid;
                            break;
                        }
                    } finally {
                        CloseHandle(hProc);
                    }
                }
            }

            if (notepadPid == -1) {
                pidResolvingAttempts++;
                status.log("pid resolving attempt %d failed".formatted(pidResolvingAttempts), FAILURE);
            }
        }

        if (notepadPid == -1) {
            status.log("unable to resolve notepad pid", FAILURE);
            throw new RuntimeException("Unable to find the pid for notepad.exe");
        }

        return notepadPid;
    }

    private static List<Long> findBytes(MemorySegment hProc, SystemInfoRecord systemInfoRecord, byte[] searchBytes) {
        List<Long> foundAddresses = null;
        while (foundAddresses == null || foundAddresses.isEmpty()) {
            status.log("searching for token in process heap", ONGOING);
            foundAddresses = findAllMatches(hProc, systemInfoRecord, searchBytes);
        }
        return foundAddresses;
    }

    /**
     * Scan from the lowest allowable address to the highest allowable address for the search bytes.
     * <p>
     * Optimised by using VirtualqueryEx to identify memory regions belonging to the process (rather than incrementally
     * searching the entire address space).  Guard pages and other non-readable pages are skipped.
     *
     * @param hProc            the process handle of the notepad instance we're scanning
     * @param systemInfoRecord GetSystemInfo struct detailing min/max addresses on this windows system
     * @param searchBytes      the bytes to be found
     * @return multiple hits are possible, all will be returned
     */
    private static List<Long> findAllMatches(MemorySegment hProc, SystemInfoRecord systemInfoRecord, byte[] searchBytes) {
        List<Long> searchHits = new ArrayList<>();

        long searchIndex = systemInfoRecord.minimumApplicationAddress();

        try (Arena offHeap = Arena.ofConfined()) {

            MemorySegment searchBytesMS = offHeap.allocate(searchBytes.length);
            MemorySegment.copy(MemorySegment.ofArray(searchBytes), 0, searchBytesMS, 0, searchBytesMS.byteSize());

            while (searchIndex < systemInfoRecord.maximumApplicationAddress()) {
                MemoryBasicInformationRecord memoryBasicInfoRecord = VirtualQueryEx(hProc, searchIndex);

                long regionSize = memoryBasicInfoRecord.regionSize();
                long endAddress = searchIndex + regionSize;
                String regionDetails = "region %x-%x [%x]".formatted(searchIndex, endAddress, regionSize);

                if (isPageReadable(memoryBasicInfoRecord)) {
                    status.log("reading heap@%x, size=%x".formatted(memoryBasicInfoRecord.baseAddress(), regionSize), ONGOING);

                    byte[] bytes = ReadProcessMemory(hProc, memoryBasicInfoRecord.baseAddress(), regionSize);
                    if (bytes.length == 0) {
                        status.log("no memory read!", FAILURE);
                        continue;
                    }

                    MemorySegment searchBuffer = ReadProcessMemory(offHeap, hProc, memoryBasicInfoRecord.baseAddress(), regionSize);

                    // scan the process's buffer for the search bytes, using memcmp - because we can
                    for (int i = 0; i < bytes.length - searchBytesMS.byteSize(); i++) {
                        long startAddress = searchBuffer.address() + i;
                        status.throttledLog(ONGOING, "searching index %x", i);
                        MemorySegment searchOffset = MemorySegment.ofAddress(startAddress);

                        if (memcmp(searchBytesMS, searchOffset, searchBytesMS.byteSize())) {
                            searchHits.add(memoryBasicInfoRecord.baseAddress() + i);
                        }
                    }
                } else {
                    status.log("%s is protected, skipping".formatted(regionDetails), ONGOING);
                }
                searchIndex += regionSize;
            }
        }

        return searchHits;
    }

    /**
     * Check the supplied {@link MemoryBasicInformationRecord} and ensure the page protection and state attributes allow
     * for read access.
     *
     * @param memoryBasicInfoRecord previously populated for the page under investigation
     * @return true if the memory can be safely read as-is
     */
    private static boolean isPageReadable(MemoryBasicInformationRecord memoryBasicInfoRecord) {
        int pageProtectionFlags = memoryBasicInfoRecord.protect();

        boolean isPageGuard = (pageProtectionFlags & PAGE_GUARD()) != 0;
        boolean isReadOnly = (pageProtectionFlags & PAGE_READONLY()) != 0;
        boolean isReadWrite = (pageProtectionFlags & PAGE_READWRITE()) != 0;
        boolean isPageNoAccess = (pageProtectionFlags & PAGE_NOACCESS()) != 0;
        boolean isMemoryCommitted = (memoryBasicInfoRecord.state() & MemoryProtectionConstants.MEM_COMMIT()) != 0;
        boolean isFreeMemory = (memoryBasicInfoRecord.state() & MemoryProtectionConstants.MEM_FREE()) != 0;

        boolean cannotProcess = isPageGuard ||
                isPageNoAccess ||
                isFreeMemory ||
                !isMemoryCommitted;

        return !cannotProcess;
    }

    /**
     * windows applications will have many "windows" associated with them, typically each control (e.g menu bar, status
     * line, edit boxes etc).  Notepad has several window handles that are not relevant to our purposes.
     * <p>
     * This method hunts for a window which has a child window associated, in the version of notepad running here there
     * is only one window which has children, and the first of those children is the text edit view.
     *
     * @param notepadPid the pid of the process we're looking to enumerate its windows
     * @return the hWnd handle of the window that has children
     */
    private static Long findNotepadEditWindow(int notepadPid) {
        long foundPid;

        Long childAfter = null;
        do {
            childAfter = FindWindowExA(null, childAfter);
            foundPid = GetWindowThreadProcessId(childAfter);
            if (foundPid == notepadPid) {
                status.log("hWnd %x has been found for notepad pid %d".formatted(childAfter, foundPid), ONGOING);
                long childHwnd = FindWindowExA(childAfter, null);
                if (childHwnd == 0) {
                    status.log("hwnd %d has no children, keep searching".formatted(childAfter), FAILURE);
                    foundPid = -1;
                }
            }
        } while (foundPid != notepadPid);
        return childAfter;
    }
}
