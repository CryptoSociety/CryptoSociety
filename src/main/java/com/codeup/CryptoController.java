package com.codeup;

import com.codeup.auth.User;
import com.codeup.auth.Users;
import com.codeup.models.Crypto;
import com.codeup.models.Cryptos;
import com.codeup.models.UserCrypto;
import com.codeup.models.UserCryptos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    Users usersRepo;

    private User loggedUser(){
        return usersRepo.findOne(loggedInUser().getId());
    }

    @GetMapping()
    public String index(Model model){
        List<Crypto> cryptoList = cryptosRepo.findByActiveEqualsAndIsApprovedEquals(true, true);
        List<Crypto> orderedCryptoList = new ArrayList<>();
//        TODO: This for loop simply makes it show from most recent to oldest. Removing/commenting it out will cause it to sort with oldest first
        for (int i = cryptoList.size()-1; i >= 0; i--) {
            if(isLoggedIn()) {
                if (loggedUser().getId() != cryptoList.get(i).getUser().getId() && userCryptosRepo.findByPlayerIdAndCryptoId(loggedUser().getId(), cryptoList.get(i).getId()) == null) {
                    orderedCryptoList.add(cryptoList.get(i));
                }
            } else {
                orderedCryptoList.add(cryptoList.get(i));
            }
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
    public String createCrypto(@Valid Crypto crypto, Errors validation, Model model) throws Exception {
        if(crypto.getScheme().equals("caesar") && !crypto.getCryptokey().matches("\\d+")) {
            validation.rejectValue("cryptokey", "crypto.cryptokey", "Key must be a positive whole number");
        }
        if(crypto.getScheme().equals("railfence") && !crypto.getCryptokey().matches("\\d+")){
            validation.rejectValue("cryptokey", "crypto.cryptokey", "Key must be a positive whole number");
        }
        if(crypto.getScheme().equals("kamasutra") && ((crypto.getCryptokey().length() < 26) || !Cryptography.check26
                (crypto.getCryptokey().toCharArray()))){
            validation.rejectValue("cryptokey", "crypto.cryptokey", "Key must contain all 26 letters exactly once");
        }
        if(crypto.getScheme().equals("vigenere") && !crypto.getCryptokey().matches("[a-zA-Z]+")) {
            validation.rejectValue("cryptokey", "crypto.cryptokey", "Key must contain only letters");
        }
        if(validation.hasErrors()){
            model.addAttribute(validation.getAllErrors());
            model.addAttribute("crypto", crypto);
            return "/cryptos/create";
        }
        crypto.setUser(loggedUser());
        crypto.setUsersSolved(0);
        crypto.setCryptoText(CipherSelector.create(crypto));
        crypto.setIsApproved(false);
        crypto.setActive(true);
        cryptosRepo.save(crypto);
        return "redirect:/cryptos/" + crypto.getId();
    }

    @GetMapping("/{id}")
    public String individualCrypto(@PathVariable long id, Model model){
        Crypto crypto = cryptosRepo.findOne(id);
        if((crypto.getIsApproved() && crypto.getActive()) || (isLoggedIn() && crypto.getUser().getId() == loggedUser().getId()) || (isLoggedIn() && loggedUser().getAdmin())) {
            model.addAttribute("crypto", crypto);
            if(isLoggedIn() && ((loggedUser().getId() == crypto.getUser().getId()) || (isLoggedIn() && loggedUser().getAdmin()))) {
                model.addAttribute("showEditControls", true);
            }
            if(isLoggedIn() && loggedUser().getAdmin()) {
                model.addAttribute("isAdmin", true);
            }
            boolean solvable = false;
            if(isLoggedIn()) {
                User user = loggedUser();
                if (isLoggedIn() && !user.getAdmin() && (user.getId() != crypto.getUser().getId()) && userCryptosRepo.findByPlayerIdAndCryptoId(user.getId(), crypto.getId()) == null) {
                    solvable = true;
                }
            }
            model.addAttribute("solvable", solvable);
            if(!solvable && isLoggedIn()){
                model.addAttribute("showExtendedInfo", true);
            }
            return "/cryptos/show";
        } else {
            return "redirect:/cryptos";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteCrypto(@PathVariable long id){
        Crypto crypto = cryptosRepo.findOne(id);
        if(isLoggedIn() && loggedUser().getId() == crypto.getUser().getId() || (isLoggedIn() && loggedUser().getAdmin())) {
            crypto.setActive(false);
            cryptosRepo.save(crypto);
            return "redirect:/cryptos";
        } else {
            return "redirect:/cryptos/{id}";
        }
    }

    @GetMapping("/{id}/edit")
    public String updateCryptoGet(@PathVariable long id, Model model){
        Crypto crypto = cryptosRepo.findOne(id);
        if(isLoggedIn() && (loggedUser().getId() == crypto.getUser().getId() || loggedUser().getAdmin())) {
            model.addAttribute("crypto", crypto);
            return "/cryptos/edit";
        } else {
            return "redirect:/cryptos/{id}";
        }

    }

    @PostMapping("/{id}/edit")
    public String updateCrypto(@PathVariable long id, @Valid Crypto crypto, Errors validation, Model model) throws Exception{
        if(crypto.getScheme().equals("caesar") && !crypto.getCryptokey().matches("\\d+")) {
            validation.rejectValue("cryptokey", "crypto.cryptokey", "Key must be a positive whole number");
        }
        if(crypto.getScheme().equals("railfence") && !crypto.getCryptokey().matches("\\d+")){
            validation.rejectValue("cryptokey", "crypto.cryptokey", "Key must be a positive whole number");
        }
        if(crypto.getScheme().equals("kamasutra") && ((crypto.getCryptokey().length() < 26) || !Cryptography.check26
                (crypto.getCryptokey().toCharArray()))){
            validation.rejectValue("cryptokey", "crypto.cryptokey", "Key must contain all 26 letters exactly once");
        }
        if(crypto.getScheme().equals("vigenere") && !crypto.getCryptokey().matches("[a-zA-Z]+")) {
            validation.rejectValue("cryptokey", "crypto.cryptokey", "Key must contain only letters");
        }
        if(validation.hasErrors()){
            model.addAttribute(validation.getAllErrors());
            model.addAttribute("crypto", crypto);
            return "/cryptos/edit";
        }
        crypto.setCryptoText(CipherSelector.create(crypto));
        Crypto oldCrypto = cryptosRepo.findOne(id);
        if(isLoggedIn() && loggedUser().getId() == oldCrypto.getUser().getId() || loggedUser().getAdmin()) {
            oldCrypto.setName(crypto.getName());
            oldCrypto.setSolution(crypto.getSolution());
            oldCrypto.setPlainText(crypto.getPlainText());
            oldCrypto.setCryptoText(crypto.getCryptoText());
            oldCrypto.setScheme(crypto.getScheme());
            oldCrypto.setCryptokey(crypto.getCryptokey());
            oldCrypto.setPoints(crypto.getPoints());
            oldCrypto.setActive(true);
            oldCrypto.setIsApproved(false);
            cryptosRepo.save(oldCrypto);
            return "redirect:/cryptos/{id}";
        } else {
            return "redirect:/cryptos/{id}";

        }
    }

    @PostMapping("{id}/solve")
    public String solveCrypto(@PathVariable long id, @RequestParam("solution") String solution){

        boolean cryptoIsCorrect = (cryptosRepo.findOne(id).getSolution().equalsIgnoreCase(solution));
        if(cryptoIsCorrect){
            Crypto crypto = cryptosRepo.findOne(id);
            crypto.setUsersSolved(crypto.getUsersSolved()+1);
            cryptosRepo.save(crypto);
            User currentUser = usersRepo.findOne(loggedUser().getId());
            currentUser.setPoints(currentUser.getPoints()+crypto.getPoints());
            usersRepo.save(currentUser);
            UserCrypto userCrypto = new UserCrypto();
            userCrypto.setCrypto(crypto);
            userCrypto.setPlayer(loggedUser());
            userCryptosRepo.save(userCrypto);
            return "redirect:/cryptos?success";
        } else {
            return "redirect:/cryptos/{id}?incorrect";
        }
    }

    @PostMapping("{id}/approve")
    public String approveCrypto(@PathVariable long id){
        if(isLoggedIn() && loggedUser().getAdmin()){
            Crypto crypto = cryptosRepo.findOne(id);
            crypto.setIsApproved(true);
            cryptosRepo.save(crypto);
            return "redirect:/admin?approved";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/challenge")
    public String challenge(){
        return "/cryptos/challenge";
    }
}
