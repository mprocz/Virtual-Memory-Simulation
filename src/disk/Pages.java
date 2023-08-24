package disk;
import ram.MemoryBlock;

public class Pages {
    public boolean status;
    public int index;
    private int age;
    public MemoryBlock[] memory = new MemoryBlock[4];

    public Pages(int index){
        this.initPage();
        this.index = index;
        this.age = 0;
        this.status = false;
    }

    public void initPage(){
        for (int i = 0; i < 4; i++){
            this.memory[i] = new MemoryBlock(i);
        }
    }
}
