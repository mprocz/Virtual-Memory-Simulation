package ram;

public class MemoryBlock {
    private int adress;
    private boolean status;
    public String content;

    public MemoryBlock(int adress){
        this.adress = adress;
        this.status = false;
        this.content = null;
    }

    public void setStatus(boolean status){
        this.status = status;
    }
    public void setContent(String content){
        this.content = content;
    }
}
