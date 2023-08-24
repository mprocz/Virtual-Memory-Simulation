package disk;

import main.ConsoleColors;

public class HardDrive {
    private DiskBlock[][] disk = new DiskBlock[100][100];
    private Pages[] VMSpace = new Pages[16];

    public HardDrive(){
        this.initDisk();
        this.writeProcess(48);
        this.writeProcess(48);
        System.out.println(ConsoleColors.RED_BRIGHT + "Disco iniciado...\n" + ConsoleColors.RESET);
    }

    public void initDisk(){
        for (int i = 0; i < 100; i++){
            for (int j = 0; j < 100; j++){
                this.disk[i][j] = new DiskBlock(i*100+j, null, 0, 0);
            }
        }
        for (int i = 0; i < 10; i++){
            this.VMSpace[i] = new Pages(i);
        }
    }

    public void writeProcess(int size){
        Process p = new Process(1, size);

        int row = 0;
        int column = 0;
        while(disk[row][column].state != 0){
            if (column+1 >= 100){
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

            if (column+1 >= 100){
                row = row + 1;
                column = 0;
            }else{
                column = column + 1;
            }
        }
    }
}
