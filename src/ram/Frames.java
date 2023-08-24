package ram;

import disk.DiskBlock;

public class Frames {
    public boolean status;
    public int index;
    public int age;
    public MemoryBlock[] memory = new MemoryBlock[4];

    public Frames(int index){
        this.initFrame();
        this.index = index;
        this.age = 0;
        this.status = false;
    }

    public void initFrame(){
        for (int i = 0; i < 4; i++){
            this.memory[i] = new MemoryBlock(i);
        }
    }

}
