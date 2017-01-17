package com.codeup;

/**
 * Created by Duke on 1/17/17.
 */
public class Cryptography {
    public void caesar(String plaintext, int shift, boolean punctuation) {
        plaintext = prepare(plaintext, punctuation);
        StringBuilder ciphertext = new StringBuilder(plaintext);
        for (int i = 0; i < ciphertext.length(); i++){
            char letter = ciphertext.charAt(i);
            if (isAlphnumeric(letter)){
                ciphertext.setCharAt(i, (char)(letter + shift));
            }
        }
        System.out.println(ciphertext);
    }

    public void caesarDecypt(String ciphertext, int shift) {
        StringBuilder plaintext = new StringBuilder(ciphertext);
        for (int i = 0; i < plaintext.length(); i++){
            char letter = plaintext.charAt(i);
            if (isAlphnumeric(letter)){
                plaintext.setCharAt(i, (char)(letter - shift));
            }
        }
        System.out.println(plaintext);
    }

    private String prepare(String input, boolean punctuation) {
        input = input.toUpperCase();
        if (punctuation)
            return input;
        return input.replaceAll("[^A-Z0-9]", "");
    }

    private boolean isAlphnumeric(char c){
        return (Character.isLetter(c) || Character.isDigit(c));
    }
}
