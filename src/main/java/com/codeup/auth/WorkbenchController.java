package com.codeup.auth;

import com.codeup.Cryptography;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * Created by Duke on 1/19/17.
 */
@Controller
@RequestMapping("/workbench")
public class WorkbenchController {
    Cryptography c = new Cryptography();

    @GetMapping("/caesar")
    public String makeCaesarTool(Model m){
        m.addAttribute("system", "caesar");
        return "tool";
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

    @GetMapping("/atbash")
    public String makeAtbashTool(Model m){
        m.addAttribute("system", "atbash");
        return "tool";
    }

    @RequestMapping(value = "/atbashtool.json", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getAtbash(@RequestParam("input") String input){
        String output = c.atbash(input, true);
        return output;
    }
}
