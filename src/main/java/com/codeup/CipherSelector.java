package com.codeup;

import com.codeup.models.Crypto;

/**
 * Created by Duke on 1/20/17.
 */
public class CipherSelector {
    public static String create(Crypto crypto) throws Exception {
        Cryptography c = new Cryptography();
        String ciphertext;
        switch (crypto.getScheme()) {
            case "caesar":
                ciphertext = c.caesar(crypto.getPlainText(),
                        crypto.getCryptokey(),
                        crypto.isKeepPunctuation());
                break;
            case "atbash":
                ciphertext = c.atbash(crypto.getPlainText(), crypto.isKeepPunctuation());
                break;
            case "kamasutra":
                ciphertext = c.kamasutra(crypto.getPlainText(),
                        crypto.getCryptokey(),
                        crypto.isKeepPunctuation());
                break;
            case "railfence":
                ciphertext = c.railfence(crypto.getPlainText(),
                        crypto.getCryptokey(),
                        crypto.isKeepPunctuation());
                break;
            case "vigenere":
                ciphertext = c.vigenere(crypto.getPlainText(),
                        crypto.getCryptokey(),
                        crypto.isKeepPunctuation());
                break;
            default:
                return null;
        }
        return ciphertext;
    }
}
