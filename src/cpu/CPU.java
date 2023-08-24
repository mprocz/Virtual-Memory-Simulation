package cpu;

import disk.HardDrive;
import main.ConsoleColors;
import ram.MainMemory;

public class CPU {
    private MainMemory MP;
    private HardDrive disco;
    private MMU mmu;
    public CPU(){
        System.out.println(ConsoleColors.RED_BRIGHT + "Iniciando CPU...\n" + ConsoleColors.RESET);
        this.mmu = new MMU();
        this.MP = new MainMemory(8);
        this.disco = new HardDrive();
    }

    public void startProcess(int row, int column){
        boolean processOn = true;
        int time = 0;
        while(processOn == true){
            MP.updateAge(time);



            time = time + 1;
            if (time == 10){
                processOn = false;
            }
        }
    }
}
