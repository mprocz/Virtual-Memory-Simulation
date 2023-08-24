package cpu;

import main.ConsoleColors;
import ram.MainMemory;

public class MMU {
    int[] TLB;
    public MMU (){
        System.out.println(ConsoleColors.RED_BRIGHT + "Iniciando Memory Management Unit...\n" + ConsoleColors.RESET);
    }

    public void updatePageTable(int virtualA, int physicalA, int device, MainMemory MP){
        MP.pageTable[virtualA][1] = physicalA;
        MP.pageTable[virtualA][2] = device;
    }
}
