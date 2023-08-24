package disk;
import ram.Frames;
import main.ConsoleColors;
import ram.MemoryBlock;

public class HardDrive {

    private final int rowSize = 100;
    private final int columnSize = 100;
    private final int nPages = 16;
    private DiskBlock[][] disk = new DiskBlock[rowSize][columnSize];
    public Pages[] VMSpace = new Pages[nPages];

    public HardDrive(int pagesInMP){
        this.initDisk(pagesInMP);
        this.writeProcess(48);
        System.out.println(ConsoleColors.RED_BRIGHT + "Disco iniciado...\n" + ConsoleColors.RESET);
    }

    public void initDisk(int pagesInMP){
        for (int i = 0; i < this.rowSize; i++){
            for (int j = 0; j < this.columnSize; j++){
                this.disk[i][j] = new DiskBlock(i*100+j, null, 0, 0);
            }
        }
        int index = pagesInMP;
        for (int i = 0; i < this.nPages; i++){
            this.VMSpace[i] = new Pages(pagesInMP);
            pagesInMP++;
        }
    }

    public void writeProcess(int size){
        Process p = new Process(1, size);

        int row = 0;
        int column = 0;
        while(disk[row][column].state != 0){
            if (column+1 >= columnSize){
                row = row + 1;
                column = 0;
            }else{
                column = column + 1;
            };
        }

        for (int i = 0; i < size; i++){
            this.disk[row][column].content = p.process[i].content;
            this.disk[row][column].state = p.process[i].state;
            this.disk[row][column].process = p.process[i].process;

            if (column+1 >= columnSize){
                row = row + 1;
                column = 0;
            }else{
                column = column + 1;
            }
        }
    }

    public int getSize(int row, int column, int process){
        int size = 0;
        while(this.disk[row][column].process == process){
            if (column+1 >= columnSize){
                row = row + 1;
                column = 0;
            }else{
                column = column + 1;
            }
            size = size + 1;
        }
        return size;
    }
    public DiskBlock[] read(int row, int column, int processSize){
        DiskBlock[] process = new DiskBlock[processSize];
        int i = 0;
        while (i < processSize){
            process[i] = disk[row][column];
            if (column+1 >= columnSize){
                row = row + 1;
                column = 0;
            }else{
                column = column + 1;
            }
            i++;
        }
        return process;
    }

    public int writeVMSpace(Frames frame){
        int i = 0;
        while(this.VMSpace[i].status == true && i < this.nPages){
            i++;
        }
        this.VMSpace[i].status = true;
        for (int j = 0; j < 4; j++){
            this.VMSpace[i].memory[j].setStatus(true);
            this.VMSpace[i].memory[j].setContent(frame.memory[j].content);
        }
        System.out.println(ConsoleColors.RED_BRIGHT + "   Escrito na memória virtual. Posição: " + i + ConsoleColors.RESET);
        return i;
    }

    public MemoryBlock[] readPage(int index){
        return this.VMSpace[index].memory;
    }
}
