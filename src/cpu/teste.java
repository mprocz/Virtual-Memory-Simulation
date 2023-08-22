import java.util.Arrays;

class PageTable {
    private int[] entries;

    public PageTable(int numPages) {
        entries = new int[numPages];
        Arrays.fill(entries, -1); // Initialize with -1 to represent unallocated pages
    }

    public int getEntry(int virtualPage) {
        if (virtualPage >= 0 && virtualPage < entries.length) {
            return entries[virtualPage];
        } else {
            throw new IllegalArgumentException("Virtual page out of bounds");
        }
    }

    public void setEntry(int virtualPage, int frame) {
        if (virtualPage >= 0 && virtualPage < entries.length) {
            entries[virtualPage] = frame;
        } else {
            throw new IllegalArgumentException("Virtual page out of bounds");
        }
    }
}

class Memory {
    private int[] data;

    public Memory(int size) {
        data = new int[size];
    }

    public int read(int address) {
        if (address >= 0 && address < data.length) {
            return data[address];
        } else {
            throw new IllegalArgumentException("Address out of bounds");
        }
    }

    public void write(int address, int value) {
        if (address >= 0 && address < data.length) {
            data[address] = value;
        } else {
            throw new IllegalArgumentException("Address out of bounds");
        }
    }
}

class VirtualMemory {
    private int numPages;
    private int pageSize;
    private int numFrames;
    private PageTable pageTable;
    private Memory physicalMemory;

    public VirtualMemory(int numPages, int pageSize, int numFrames) {
        this.numPages = numPages;
        this.pageSize = pageSize;
        this.numFrames = numFrames;
        pageTable = new PageTable(numPages);
        physicalMemory = new Memory(numFrames * pageSize);
    }

    public void loadProgram(int[] program) {
        if (program.length > numPages * pageSize) {
            throw new IllegalArgumentException("Program size exceeds virtual memory capacity");
        }

        for (int i = 0; i < program.length; i++) {
            int virtualAddress = i;
            int value = program[i];
            write(virtualAddress, value);
        }
    }

    public int read(int virtualAddress) {
        int virtualPage = virtualAddress / pageSize;
        int offset = virtualAddress % pageSize;
        int frame = pageTable.getEntry(virtualPage);
        int physicalAddress = frame * pageSize + offset;
        return physicalMemory.read(physicalAddress);
    }

    public void write(int virtualAddress, int value) {
        int virtualPage = virtualAddress / pageSize;
        int offset = virtualAddress % pageSize;
        int frame = pageTable.getEntry(virtualPage);
        if (frame == -1) {
            frame = allocateFrame();
            pageTable.setEntry(virtualPage, frame);
        }
        int physicalAddress = frame * pageSize + offset;
        physicalMemory.write(physicalAddress, value);
    }

    private int allocateFrame() {
        // Simple frame allocation strategy
        for (int frame = 0; frame < numFrames; frame++) {
            boolean isFree = true;
            for (int entry : pageTable.getEntries()) {
                if (entry == frame) {
                    isFree = false;
                    break;
                }
            }
            if (isFree) {
                return frame;
            }
        }
        throw new IllegalStateException("No free frames available");
    }

    public static void main(String[] args) {
        int[] program = {1, 2, 3, 4, 5};
        VirtualMemory virtualMemory = new VirtualMemory(4, 4, 3);
        virtualMemory.loadProgram(program);

        for (int i = 0; i < program.length; i++) {
            int value = virtualMemory.read(i);
            System.out.println("Read value at address " + i + ": " + value);
        }

        virtualMemory.write(5, 10);
        int updatedValue = virtualMemory.read(5);
        System.out.println("Updated value at address 5: " + updatedValue);
    }
}