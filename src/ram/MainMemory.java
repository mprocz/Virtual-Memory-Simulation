package ram;

import disk.DiskBlock;
import main.ConsoleColors;

public class MainMemory {
    private Frames[] frames;
    public MainMemory(int nPages){
        this.frames = new Frames[nPages];
        this.initRAM(nPages);
        System.out.println(ConsoleColors.RED_BRIGHT + "Memoria Principal iniciada...\n" + ConsoleColors.RESET);
    }
    public void initRAM(int nPages){
        for (int i = 0; i < nPages; i++){
            this.frames[i] = new Frames(i);
        }
    }

    public void updateAge(int time){
        for (Frames frame : this.frames){
            frame.setAge(time);
        }
    }
    /*
    public void write(int message){
        System.out.println("writing...");
        int row = 0;
        int column = 0;
        try{
            while(this.ram[row][column].state == true){
                row++;
                column++;
                if (row >=2 || column >= 2){
                    throw new ArrayIndexOutOfBoundsException();
                }
            }
            this.ram[row][column].state = true;
            this.ram[row][column].value = message;
        }catch (ArrayIndexOutOfBoundsException exc){
            this.VMWrite(message);
        }
    }

    public void VMWrite(int message){
        System.out.println("writing in VMSPACE...");
    }

    public void read(){
        System.out.println("reading...");
    }
    */

}
