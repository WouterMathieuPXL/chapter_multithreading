package be.pxl.ja.Exercise1;

public class TalkerRunnable implements Runnable {
    private final int id;

    public TalkerRunnable(int id) {
        this.id = id;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 4; i++) {
            new Thread(new TalkerRunnable(i)).start();
        }
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
}
