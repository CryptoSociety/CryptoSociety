package com.codeup;

import java.util.Arrays;

/**
 * Created by Duke on 1/17/17.
 */
public class Cryptography {
    public String caesar(String plaintext, int shift, boolean punctuation) {
        plaintext = prepare(plaintext, punctuation);
        StringBuilder ciphertext = new StringBuilder(plaintext);
        for (int i = 0; i < ciphertext.length(); i++){
            char letter = ciphertext.charAt(i);
            if (Character.isLetter(letter)){
                ciphertext.setCharAt(i, (char)((letter + shift - (int)'A') % 26 + (int)'A'));
            }
        }
        return ciphertext.toString();
    }

    public String caesarDecypt(String ciphertext, int shift) {
        StringBuilder plaintext = new StringBuilder(ciphertext);
        for (int i = 0; i < plaintext.length(); i++){
            char letter = plaintext.charAt(i);
            if (Character.isLetter(letter)){
                plaintext.setCharAt(i, (char)((letter - shift + (int)'A') % 26 + (int)'A'));
            }
        }
        return plaintext.toString();
    }

    public String atbash(String input, boolean punctuation){
        input = prepare(input, punctuation);
        StringBuilder result = new StringBuilder(input);
        for (int i = 0; i < result.length(); i++){
            char letter = result.charAt(i);
            if(Character.isLetter(letter)){
                result.setCharAt(i, ((char)((((int)'Z') - (int)letter)+(int)'A')));
            }
        }
        return result.toString();
    }

    public String railfence(String plaintext, int numberOfRails, boolean punctuation) {
        plaintext = prepare(plaintext, punctuation);
        String[] rails = new String[numberOfRails];
        Arrays.fill(rails, "");
        int currentRail = 0;
        boolean ascending = false;
        for (int i = 0; i < plaintext.length(); i++) {
            rails[currentRail] = rails[currentRail] + plaintext.charAt(i);
            currentRail = ascending ? currentRail - 1 : currentRail + 1;
            if (currentRail == 0 || currentRail == numberOfRails - 1)
                ascending = !ascending;
        }
        return String.join("", rails);
    }

    public String railfenceDecrypt(String ciphertext, int numberOfRails) {
        StringBuilder plaintext = new StringBuilder();
        plaintext.setLength(ciphertext.length());
        int i = 0;
        int jumpOne = numberOfRails*2-2;
        int jumpTwo = 0;
        boolean useFirstJump = true;
        int insertLocation = 0;
        int railcounter = 1;
        while (i < ciphertext.length()) {
            while (insertLocation < ciphertext.length()) {
                if (jumpOne == 0)
                    useFirstJump = false;
                if (jumpTwo == 0)
                    useFirstJump = true;
                plaintext.setCharAt(insertLocation, ciphertext.charAt(i));
                i++;
                insertLocation += (useFirstJump) ? jumpOne : jumpTwo;
                useFirstJump = !useFirstJump;
            }
            useFirstJump = true;
            railcounter++;
            insertLocation = railcounter - 1;
            jumpOne = jumpOne - 2;
            jumpTwo = jumpTwo + 2;
        }
        return plaintext.toString();
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
