package com.main.guapogame.model.state;

import java.security.SecureRandom;

public class Random {
    private static SecureRandom random = new SecureRandom();

    public static int getRandomNumber(int bound) {
        return random.nextInt(bound);
    }

    public static boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    private Random() {}
}
