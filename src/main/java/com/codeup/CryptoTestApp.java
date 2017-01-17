package com.codeup;

/**
 * Created by Duke on 1/17/17.
 */
public class CryptoTestApp {
    public static void main(String[] args) {
        Cryptography c = new Cryptography();
        c.caesar("Dear John, I'm testing a letter!  Isn't that great?  Now, is this a shift of 1, 2, 3, 4, 5... XOXO " +
                        "-Kathy",
                100,
                true);
        c.caesar("Dear John, I'm testing a letter!  Isn't that great?  Now, is this a shift of 1, 2, 3, 4, 5...  XOXO" +
                " -Kathy",
                100,
                false);
        c.caesarDecypt("EFBS KPIO, J'N UFTUJOH B MFUUFS!  JTO'U UIBU HSFBU?  OPX, JT UIJT B TIJGU PG 2, 3, 4, 5, 6..." +
                " YPYP -LBUIZ",
                100);
        c.caesarDecypt("EFBSKPIOJNUFTUJOHBMFUUFSJTOUUIBUHSFBUOPXJTUIJTBTIJGUPG23456YPYPLBUIZ", 100);
    }
}
