package disk;

public class DiskBlock {
    public int adress;
    public String content;
    public int state;
    public int process;

    public DiskBlock(int adress, String content, int state, int process){
        this.adress = adress;
        this.content = content;
        this.state = state;
        this.process = process;
    }
}
