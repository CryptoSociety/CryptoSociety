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

    @GetMapping("/kamasutra")
    public String makeKamaSutraTool(Model m){
        m.addAttribute("system", "kamasutra");
        return "tool";
    }

    @RequestMapping(value = "/kamasutratool.json", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String encryptKamaSutra(
            @RequestParam("input") String input,
            @RequestParam("key") String key) throws Exception {
        String output = c.kamasutra(input, key.toUpperCase().toCharArray(), true);
        return output;
    }

    @GetMapping("/railfence")
    public String makeRailfenceTool(Model m){
        m.addAttribute("system", "railfence");
        return "tool";
    }

    @RequestMapping(value = "/railfencetool.json", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String encryptRailfence(
            @RequestParam("plaintext") String plaintext,
            @RequestParam("rails") int rails
    ){
        String output = c.railfence(plaintext, rails, true);
        return output;
    }

    @RequestMapping(value = "/railfencetool/decrypt.json", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String decryptRailfence(
            @RequestParam("ciphertext") String ciphertext,
            @RequestParam("rails") String rails
    ){
        String output = c.railfenceDecrypt(ciphertext, Integer.parseInt(rails));
        return output;
    }

}
