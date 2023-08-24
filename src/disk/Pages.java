package disk;
import ram.MemoryBlock;

public class Pages {
    public int index;
    private int age;
    private MemoryBlock[] memory = new MemoryBlock[4];

    public Pages(int index){
        this.initPage();
        this.index = index;
        this.age = 0;
    }

    public void initPage(){
        for (int i = 0; i < 4; i++){
            this.memory[i] = new MemoryBlock(i);
        }
    }
}
