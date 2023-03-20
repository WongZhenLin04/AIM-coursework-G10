package Utility;

public class edges_memes {
    private final int from;
    private final int to;
    private boolean status;

    public edges_memes(int from, int to){
        this.from=from;
        this.to=to;
    }
    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
