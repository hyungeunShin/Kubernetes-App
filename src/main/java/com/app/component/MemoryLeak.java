package com.app.component;

import java.util.HashMap;
import java.util.Map;

public class MemoryLeak {
    private final Map<Integer, Memory> memorableList = new HashMap<>();

    public void run() {
        Integer i = 0;
        while(true) {
            memorableList.put(i, new Memory(i));
            i++;
        }
    }

    static class Memory {
        Integer index ;

        Memory(final Integer index) {
            this.index = index;
        }
    }
}
