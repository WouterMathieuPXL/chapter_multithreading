package be.pxl.ja.Exercise2;

import java.time.Duration;
import java.time.LocalDateTime;

public class DivisorCounter implements Runnable {
    private static int number;
    private static int numberOfDivisors;
    private final int min;
    private final int max;

    public DivisorCounter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();

        int min = 1;
        int max = 100000;
        int numberOfThreads = 6;
        int step = max / numberOfThreads;
        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(new DivisorCounter(i * step + 1, (i + 1) * step));
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LocalDateTime end = LocalDateTime.now();
        int timeDifference = (int) Duration.between(start, end).toMillis();
        System.out.println("Range [" + min + "-" + max + "]");
        System.out.println("Getal: " + DivisorCounter.getNumber());
        System.out.println("Aantal delers: " + DivisorCounter.getNumberOfDivisors());
        System.out.println("Tijd: " + timeDifference + " ms");
    }

    public static int countDivisors(int n) {
        int count = 0;
        for (int i = 1; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                // If divisors are equal, count only one
                if (n / i == i) {
                    count++;
                } else {
                    // Otherwise count both
                    count += 2;
                }
            }
        }
        return count;
    }

    public static int getNumber() {
        return number;
    }

    public static int getNumberOfDivisors() {
        return numberOfDivisors;
    }

    @Override
    public void run() {
        findNumber();
    }

    public void findNumber() {
        for (int i = min; i <= max; i++) {
            int divisors = countDivisors(i);
            if (divisors > numberOfDivisors) {
                number = i;
                numberOfDivisors = divisors;
            }
        }
    }
}
