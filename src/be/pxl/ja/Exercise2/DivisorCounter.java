package be.pxl.ja.Exercise2;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class DivisorCounter extends Thread {
    private int number;
    private int numberOfDivisors;
    private final int min;
    private final int max;

    public int getNumber() {
        return number;
    }

    public DivisorCounter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();

        int min = 1;
        int max = 10000;
        int numberOfThreads = 1;
        int step = max / numberOfThreads;
        DivisorCounter[] counters = new DivisorCounter[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            counters[i] = new DivisorCounter(i * step + 1, (i + 1) * step);
            counters[i].start();
        }
        for (DivisorCounter counter : counters) {
            try {
                counter.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LocalDateTime end = LocalDateTime.now();
        int timeDifference = (int) Duration.between(start, end).toMillis();

        Comparator comparator = Comparator.comparingInt(DivisorCounter::getNumber);
        DivisorCounter counter = (DivisorCounter) Arrays.stream(counters).min(comparator).get();

        System.out.println("Range [" + min + "-" + max + "]");
        System.out.println("Getal: " + counter.number);
        System.out.println("Aantal delers: " + counter.numberOfDivisors);
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
