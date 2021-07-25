package sample;

public enum Speed {
    SLOW(1),
    MED(2),
    FAST(3);
    private int num;
    Speed(int i){
        num = i;
    }

    public int getNum() {
        return num;
    }
}
