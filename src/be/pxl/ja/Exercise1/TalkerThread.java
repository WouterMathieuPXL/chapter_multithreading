package be.pxl.ja.Exercise1;

public class TalkerThread extends Thread {
    private int id;

    public TalkerThread(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("Talker #" + id + ": " + i);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 4; i++) {
            TalkerThread t = new TalkerThread(i);
            t.start();
        }
    }
}
