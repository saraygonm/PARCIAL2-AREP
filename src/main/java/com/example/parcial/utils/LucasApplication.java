package com.example.parcial.utils;

import java.util.ArrayList;
import java.util.List;

public class LucasApplication {

    public static List<Integer> generateLucasSequence(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("El valor de n debe ser mayor o igual a 0.");
        }

        List<Integer> sequence = new ArrayList<>();
        int a = 2, b = 1;

        if (n >= 0) sequence.add(a);

        if (n >= 1) sequence.add(b);

        for (int i = 2; i <= n; i++) {
            int next = a + b;
            sequence.add(next);
            a = b;
            b = next;
        }

        return sequence;
    }
}
