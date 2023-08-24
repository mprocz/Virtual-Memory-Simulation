package ram;

public class MemoryBlock {
    private int adress;
    private int status;
    private String content;

    public MemoryBlock(int adress){
        this.adress = adress;
        this.status = 0;
        this.content = null;
    }
}
