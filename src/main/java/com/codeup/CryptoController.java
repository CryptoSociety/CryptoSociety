package com.codeup;

import com.codeup.auth.BaseController;
import com.codeup.models.Crypto;
import com.codeup.models.Cryptos;
import com.codeup.models.UserCrypto;
import com.codeup.models.UserCryptos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.codeup.auth.BaseController.isLoggedIn;
import static com.codeup.auth.BaseController.loggedInUser;

@Controller
@RequestMapping("/cryptos")
public class CryptoController {

    @Autowired
    Cryptos cryptosRepo;

    @Autowired
    UserCryptos userCryptosRepo;

    @GetMapping()
    public String index(Model model){
        List<Crypto> cryptoList = (List<Crypto>) cryptosRepo.findAll();
        List<Crypto> orderedCryptoList = new ArrayList<>();
        for (int i = cryptoList.size()-1; i >= 0; i--) {
            orderedCryptoList.add(cryptoList.get(i));
        }
        model.addAttribute("cryptoList", orderedCryptoList);
        return "/cryptos/index";
    }

    @GetMapping("/create")
    public String createGet(Model model){
        model.addAttribute("crypto", new Crypto());
        return "/cryptos/create";
    }

    @PostMapping("/create")
    public String createCrypto(@Valid Crypto crypto, Errors validation, Model model){
        if(validation.hasErrors()){
            model.addAttribute(validation.getAllErrors());
            model.addAttribute("crypto", crypto);
            return "/cryptos/create";
        }
        crypto.setUser(BaseController.loggedInUser());
        cryptosRepo.save(crypto);
        return "redirect:/cryptos";
    }

    @GetMapping("/{id}")
    public String individualCrypto(@PathVariable long id, Model model){
        Crypto crypto = cryptosRepo.findOne(id);
        model.addAttribute("crypto", crypto);
        model.addAttribute("showEditControls", isLoggedIn() && loggedInUser().getId() == crypto.getUser().getId());
        return "cryptos/show";
    }

    @PostMapping("/{id}/delete")
    public String deleteCrypto(@PathVariable long id){
        Crypto crypto = cryptosRepo.findOne(id);
        if(isLoggedIn() && loggedInUser().getId() == crypto.getUser().getId()) {
            cryptosRepo.delete(id);
            return "redirect:/cryptos";
        } else {
            return "redirect:/cryptos/{id}";
        }
    }

    @GetMapping("/{id}/edit")
    public String updateCryptoGet(@PathVariable long id, Model model){
        Crypto crypto = cryptosRepo.findOne(id);
        if(isLoggedIn() && loggedInUser().getId() == crypto.getUser().getId()) {
            model.addAttribute("cryptos", crypto);
            return "/cryptos/edit";
        } else {
            return "redirect:/cryptos/{id}";
        }

    }

    @PostMapping("/{id}/edit")
    public String updateCrypto(@PathVariable long id, @Valid Crypto crypto, Errors validation, Model model){
        if(validation.hasErrors()){
            model.addAttribute(validation.getAllErrors());
            model.addAttribute("crypto", crypto);
            return "/cryptos/edit";
        }
        Crypto oldCrypto = cryptosRepo.findOne(id);
        if(isLoggedIn() && loggedInUser().getId() == oldCrypto.getUser().getId()) {
            oldCrypto.setName(crypto.getName());
            oldCrypto.setSolution(crypto.getSolution());
            cryptosRepo.save(oldCrypto);
            return "redirect:/cryptos/{id}";
        } else {
            return "redirect:/cryptos/{id}";

        }
    }

    @PostMapping("{id}/solve")
    public String solveCrypto(@PathVariable long id, Model model){
//        TODO: Implement actual check for correct-ness
        boolean cryptoIsCorrect = true;
        if(cryptoIsCorrect){
            UserCrypto userCrypto = new UserCrypto();
            userCrypto.setCrypto(cryptosRepo.findOne(id));
            userCrypto.setPlayer(loggedInUser());
            userCryptosRepo.save(userCrypto);
            return "redirect:/success";
        } else {
            return "redirect:/cryptos/{id}";
        }
    }
}
