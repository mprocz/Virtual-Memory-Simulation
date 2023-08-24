package ram;

import disk.DiskBlock;

public class Frames {
    public int index;
    private int age;
    private MemoryBlock[] memory = new MemoryBlock[4];

    public Frames(int index){
        this.initFrame();
        this.index = index;
        this.age = 0;
    }

    public void initFrame(){
        for (int i = 0; i < 4; i++){
            this.memory[i] = new MemoryBlock(i);
        }
    }

    public void setAge(int age){
        this.age = age;
    }
}
