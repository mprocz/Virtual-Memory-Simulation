package cpu;

import disk.DiskBlock;
import disk.HardDrive;
import exception.MemoriaCheiaException;
import main.ConsoleColors;
import ram.MainMemory;
import disk.*;

import java.util.Arrays;

public class CPU {
    private MainMemory MP;
    private HardDrive disco;
    private MMU mmu;
    public CPU(){
        System.out.println(ConsoleColors.RED_BRIGHT + "Iniciando CPU...\n" + ConsoleColors.RESET);
        this.mmu = new MMU();
        this.MP = new MainMemory(8);
        this.disco = new HardDrive(8);
    }

    public void startProcess(int row, int column, int processIndex){
        System.out.println(ConsoleColors.GREEN_BRIGHT + "Iniciando execução de processo" + ConsoleColors.RESET);
        boolean processOn = true;
        while(processOn == true) {
            MP.updateAge();

            int processSize = disco.getSize(row, column, processIndex);
            int processPages = (processSize/4) + 1;

            MP.initPageTable(processPages);
            MP.initVirtualAdresses(processPages);
            DiskBlock[] process = disco.read(row, column, processSize);

            System.out.println("VA | FA | DV");
            for(int i = 0; i < processPages; i++){
                System.out.println(MP.pageTable[i][0] + " | " + MP.pageTable[i][1] + " | " + MP.pageTable[i][2]);
            }

            try {
                for (int i = 0; i < ((processSize / 4) + 1); i++) {
                    DiskBlock[] page = new DiskBlock[4];
                    int physicalA;
                    try{
                        for (int j = 0; j < 4; j++) {
                            page[j] = process[i * 4 + j];
                        }
                        physicalA = MP.write(page);
                        this.mmu.updatePageTable(i, physicalA, 0,MP);
                        MP.updateAge();
                    }catch (MemoriaCheiaException exc) {
                        System.out.println(exc);
                        physicalA = MP.VMWrite(page, i, this.disco, mmu);
                        this.mmu.updatePageTable(i, physicalA, 0, MP);
                        MP.updateAge();
                    }
                }
            }catch (ArrayIndexOutOfBoundsException exc){
                System.out.println(ConsoleColors.GREEN_BRIGHT + "processo escrito na memória principal" + ConsoleColors.RESET);
            }
            processOn = false;


            System.out.println("VA | FA | DV");
            for(int i = 0; i < processPages; i++){
                System.out.println(MP.pageTable[i][0] + " | " + MP.pageTable[i][1] + " | " + MP.pageTable[i][2]);
            }

            this.execute(processPages);
        }
    }

    private void execute(int processPages){
        System.out.println(ConsoleColors.GREEN_BRIGHT + "\n------------------------" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN_BRIGHT + " Executando processo..." + ConsoleColors.RESET);

        // Endereços virtuais para execução do programa;
        int[] executablePath = {1, 1, 2, 3, 9, 1, 11, 3, 4, 5, 8, 10, 6, 7, 11};
        for (int i = 0; i < executablePath.length; i++){
            System.out.println(ConsoleColors.GREEN_BRIGHT + "\n Executando frame " + executablePath[i] + " do processo" + ConsoleColors.RESET);

            int device = MP.pageTable[executablePath[i]][2];
            if (device == 0){
                System.out.println(ConsoleColors.YELLOW_BRIGHT + "   Frame localizado na posição " + MP.pageTable[executablePath[i]][1] + " da memória principal." + ConsoleColors.RESET);
            }else if (device == 1){
                System.out.println(ConsoleColors.YELLOW_BRIGHT + "   Frame localizado na posição " + MP.pageTable[executablePath[i]][1] + " do espaço de memória virtual." + ConsoleColors.RESET);
                System.out.println(ConsoleColors.YELLOW_BRIGHT + "   Operação SWAP será realizada no frame." + ConsoleColors.RESET);
                MP.swap(MP.pageTable[executablePath[i]][0], MP.pageTable[executablePath[i]][1], this.disco, this.mmu);
                System.out.println(ConsoleColors.YELLOW_BRIGHT + "   Frame localizado na posição " + MP.pageTable[executablePath[i]][1] + " da memória principal." + ConsoleColors.RESET);

            }
        }

        System.out.println("\nVA | FA | DV");
        for(int i = 0; i < processPages; i++){
            System.out.println(MP.pageTable[i][0] + " | " + MP.pageTable[i][1] + " | " + MP.pageTable[i][2]);
        }

        System.out.println(ConsoleColors.GREEN_BRIGHT + "\n\n Processo finalizado..." + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN_BRIGHT + "\n------------------------" + ConsoleColors.RESET);
    }
}
