package disk;

public class Process {
    public int number;
    public DiskBlock[] process;

    public Process(int number, int size){
        this.number = number;
        this.process = new DiskBlock[size];
        initProcess(size);
    }

    public void initProcess(int size){
        for (int i = 0; i < size; i++){
            String value = "abcdefg";
            process[i] = new DiskBlock(i, value, 1, this.number);
        }
    }
}
