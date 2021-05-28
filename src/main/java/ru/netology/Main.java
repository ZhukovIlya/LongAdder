package ru.netology;

import java.util.Arrays;
import java.util.concurrent.atomic.LongAdder;

class Main {
    public static void main(String[] args) throws InterruptedException {
        final int AMOUNT_OF_REVENUE = 3;
        int checkRevenue;
        int[] shop1 = new int[AMOUNT_OF_REVENUE];
        int[] shop2 = new int[AMOUNT_OF_REVENUE];
        int[] shop3 = new int[AMOUNT_OF_REVENUE];
        revenue(shop1);
        revenue(shop2);
        revenue(shop3);
        LongAdder stat = new LongAdder();

        Thread countingShop1 = new Thread(() -> Arrays.stream(shop1).sequential().forEach(stat::add));
        Thread countingShop2 = new Thread(() -> Arrays.stream(shop2).sequential().forEach(stat::add));
        Thread countingShop3 = new Thread(() -> Arrays.stream(shop3).sequential().forEach(stat::add));

        countingShop1.start();
        countingShop2.start();
        countingShop3.start();

        countingShop1.join();
        countingShop2.join();
        countingShop3.join();

        System.out.println("Общий итог: " + stat.sum());


        checkRevenue = (sumRevenue(shop1) + (sumRevenue(shop2) + sumRevenue(shop3)));
        System.out.println("Общий итог проверка: " + checkRevenue);

    }

    public static void revenue(int[] rev) {
        for (int i = 0; i < rev.length; i++) {
            rev[i] = (int) (Math.random() * 1000);
        }
    }

    public static int sumRevenue(int[] arr) {
        int sum = 0;
        sum += Arrays.stream(arr).sequential().sum();
        return sum;
    }
}
