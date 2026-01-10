package com.main.guapogame.model.state;

import java.security.SecureRandom;

public class Random {
    private static SecureRandom secureRandom = new SecureRandom();

    public static int getRandomNumber(int bound) {
        return secureRandom.nextInt(bound);
    }

    public static boolean getRandomBoolean() {
        return secureRandom.nextBoolean();
    }

    private Random() {}
}
