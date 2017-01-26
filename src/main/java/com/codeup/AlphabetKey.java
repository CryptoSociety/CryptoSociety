package com.codeup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duke on 1/18/17.
 */
public class AlphabetKey {
    private Map key = new HashMap();
    private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public AlphabetKey(char[] input) throws Exception {
        if (input.length != 26)
            throw new Exception("Expecting 26 letters, got " + input.length);
        for (int i = 0; i < alphabet.length; i = i + 2) {
            key.put(input[i], input[i + 1]);
            key.put(input[i + 1], input[i]);
        }
    }

    public char getLetter(char c) {
        return (char) key.get(c);
    }
}
