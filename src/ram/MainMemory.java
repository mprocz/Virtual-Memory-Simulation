package ram;

import disk.DiskBlock;
import main.ConsoleColors;
import disk.*;
import exception.*;
import cpu.MMU;

public class MainMemory {
    private Frames[] frames;

    public int[][] pageTable;
    private int nPages;
    public MainMemory(int nPages){
        this.nPages = nPages;
        this.frames = new Frames[this.nPages];
        this.initRAM(nPages);
        System.out.println(ConsoleColors.RED_BRIGHT + "Memoria Principal iniciada...\n" + ConsoleColors.RESET);
    }
    public void initRAM(int nPages){
        for (int i = 0; i < nPages; i++){
            this.frames[i] = new Frames(i);
        }
    }

    public void initPageTable(int size){
        this.pageTable = new int[size][3];

        for (int i = 0; i < size; i++){
            for (int j = 0; j < 3; j++){
                this.pageTable[i][j] = -1;
            }
        }
    }

    public void initVirtualAdresses(int processPages){
        for (int i = 0; i < processPages; i++){
            this.pageTable[i][0] = i;
        }
    }
    public void updateAge(){
        for (Frames frame : this.frames){
            if (frame.status == true){
                frame.age++;
            }
        }
    }

    public int write(DiskBlock[] page) throws MemoriaCheiaException{
        System.out.println("writing...");
        try{
            int i = 0;
            while(this.frames[i].status == true && i < this.nPages){
                i++;
            }
            System.out.println(ConsoleColors.RED_BRIGHT + "frame escrito: " + i + ConsoleColors.RESET);
            this.frames[i].status = true;
            for (int j = 0; j < 4; j++){
                frames[i].memory[j].setStatus(true);
                frames[i].memory[j].setContent(page[j].content);
            }
            return i;
        }catch (ArrayIndexOutOfBoundsException exc){
            throw new MemoriaCheiaException();
        }
    }

    public int VMWrite(DiskBlock[] page, int virtualA, HardDrive disco, MMU mmu){
        System.out.println("writing in VMSPACE...");
        int swapIndexPage = this.LRU();
        System.out.println(ConsoleColors.RED_BRIGHT + "Swap frame: " + swapIndexPage + ConsoleColors.RESET);


        int physicalA = disco.writeVMSpace(frames[swapIndexPage]);
        mmu.updatePageTable(pageTable[swapIndexPage][0], physicalA, 1, this);

        for (int j = 0; j < 4; j++){
            frames[swapIndexPage].age = 0;
            frames[swapIndexPage].memory[j].setStatus(true);
            frames[swapIndexPage].memory[j].setContent(page[j].content);
        }
        return swapIndexPage;
    }

    public int swap(int virtualA, int physicalA, HardDrive disco, MMU mmu){
        System.out.println("   escrevendo do VMSpace para MP...");
        int swapIndexPage = this.LRU();

        int physicalASwap = disco.writeVMSpace(this.frames[swapIndexPage]);
        mmu.updatePageTable(pageTable[swapIndexPage][0], physicalASwap, 1, this);

        MemoryBlock[] swapPage = disco.readPage(physicalA);
        disco.VMSpace[physicalA].status = false;

        for (int j = 0; j < 4; j++){
            frames[swapIndexPage].age = 0;
            frames[swapIndexPage].memory[j].setStatus(true);
            frames[swapIndexPage].memory[j].setContent(swapPage[j].content);
        }
        mmu.updatePageTable(virtualA, swapIndexPage, 0, this);
        return swapIndexPage;
    }

    private int LRU(){
        int index = 0;
        for (int i = 0; i < this.nPages; i++){
            if (this.frames[i].age >= index){
                index = i;
            }
        }
        return index;
    }
}
