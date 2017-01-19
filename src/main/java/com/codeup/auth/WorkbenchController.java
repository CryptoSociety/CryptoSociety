package com.codeup.auth;

import com.codeup.Cryptography;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * Created by Duke on 1/19/17.
 */
@Controller
public class CaesarController {
    Cryptography c = new Cryptography();
    @GetMapping("/caesartool")
    public String makeCaesarTool(Model m){
        m.addAttribute("crypto", c);
        return "caesar";
    }

    @RequestMapping(value = "/caesartool.json", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String getCaesarcipher(
            @RequestParam("plaintext") String plaintext,
            @RequestParam("shift") int shift,
            @RequestParam("punctuation") boolean punctuation){
        String output =  c.caesar(plaintext, shift, punctuation);
        return output;
    }
    @RequestMapping(value = "/caesartool/decrypt.json", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String getCaesardecrypt(
            @RequestParam("ciphertext") String ciphertext,
            @RequestParam("shift") int shift){
        String output =  c.caesarDecypt(ciphertext, shift);
        return output;
    }
}
