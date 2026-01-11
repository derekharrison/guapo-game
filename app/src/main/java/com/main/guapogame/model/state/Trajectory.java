package com.main.guapogame.model.state;

import java.util.LinkedList;
import java.util.List;

public class Trajectory {
    private static List<Position> track = new LinkedList<>();
    private static final int MAX_TRACE = 15;

    public static void addPosition(Position position) {
        track.add(position);
        if(track.size() > MAX_TRACE)
            track.remove(0);
    }

    public static Position getFirst() {
        if(!track.isEmpty()) {
            return track.get(0);
        }

        return null;
    }

    public static Position getLast() {
        if(!track.isEmpty()) {
            return track.get(track.size() - 1);
        }

        return null;
    }

    public static Position getSecondToLast() {
        if(!track.isEmpty() && track.size() > 1) {
            return track.get(track.size() - 2);
        }

        return null;
    }

    private Trajectory() {}
}
