package com.codeup;

import com.codeup.auth.BaseController;
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

    @Autowired
    Users usersRepo;

    private User loggedUser(User loggedInUser){
        return usersRepo.findOne(loggedInUser().getId());
    }

    @GetMapping()
    public String index(Model model){
        List<Crypto> cryptoList = cryptosRepo.findByActiveEqualsAndIsApprovedEquals(true, true);
        List<Crypto> orderedCryptoList = new ArrayList<>();
//        TODO: This for loop simply makes it show from most recent to oldest. Removing/commenting it out will cause it to sort with oldest first
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
        crypto.setUser(loggedUser(loggedInUser()));
        crypto.setUsersSolved(0);

//  TODO: Make these actually matter
        crypto.setCryptoText("TODO");
        crypto.setIsApproved(false);
        crypto.setActive(true);
        cryptosRepo.save(crypto);
        return "redirect:/cryptos";
    }

    @GetMapping("/{id}")
    public String individualCrypto(@PathVariable long id, Model model){
        Crypto crypto = cryptosRepo.findOne(id);
        if((crypto.getIsApproved() && crypto.getActive()) || (isLoggedIn() && crypto.getUser().getId() == loggedUser(loggedInUser()).getId()) || (isLoggedIn() && loggedUser(loggedInUser()).getAdmin())) {
            model.addAttribute("crypto", crypto);
            if(isLoggedIn() && ((loggedUser(loggedInUser()).getId() == crypto.getUser().getId()) || (isLoggedIn() && loggedUser(loggedInUser()).getAdmin()))) {
                model.addAttribute("showEditControls", true);
            }
            if(isLoggedIn() && loggedUser(loggedInUser()).getAdmin()) {
                model.addAttribute("isAdmin", true);
            }
            boolean solvable = false;
            if(isLoggedIn()) {
                User user = loggedUser(loggedInUser());
                if (isLoggedIn() && !user.getAdmin() && (user.getId() != crypto.getUser().getId()) && userCryptosRepo.findByPlayerIdAndCryptoId(user.getId(), crypto.getId()) == null) {
                    solvable = true;
                }
            }
            model.addAttribute("solvable", solvable);
            return "/cryptos/show";
        } else {
            return "redirect:/cryptos";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteCrypto(@PathVariable long id){
        Crypto crypto = cryptosRepo.findOne(id);
        if(isLoggedIn() && loggedUser(loggedInUser()).getId() == crypto.getUser().getId() || (isLoggedIn() && loggedUser(loggedInUser()).getAdmin())) {
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
        if(isLoggedIn() && (loggedUser(loggedInUser()).getId() == crypto.getUser().getId() || loggedUser(loggedInUser()).getAdmin())) {
            model.addAttribute("crypto", crypto);
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
        if(isLoggedIn() && loggedUser(loggedInUser()).getId() == oldCrypto.getUser().getId() || loggedUser(loggedInUser()).getAdmin()) {
            oldCrypto.setName(crypto.getName());
            oldCrypto.setSolution(crypto.getSolution());
            oldCrypto.setPlainText(crypto.getPlainText());
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
    public String solveCrypto(@PathVariable long id){

// TODO: Implement actual check for correct-ness
        boolean cryptoIsCorrect = true;
//------------------------------------------------------
        if(cryptoIsCorrect){
            Crypto crypto = cryptosRepo.findOne(id);
            crypto.setUsersSolved(crypto.getUsersSolved()+1);
            cryptosRepo.save(crypto);
            User currentUser = usersRepo.findOne(loggedUser(loggedInUser()).getId());
            currentUser.setPoints(currentUser.getPoints()+crypto.getPoints());
            usersRepo.save(currentUser);
            UserCrypto userCrypto = new UserCrypto();
            userCrypto.setCrypto(crypto);
            userCrypto.setPlayer(loggedUser(loggedInUser()));
            userCryptosRepo.save(userCrypto);
            return "redirect:/success";
        } else {
            return "redirect:/cryptos/{id}";
        }
    }

    @PostMapping("{id}/approve")
    public String approveCrypto(@PathVariable long id){
        if(isLoggedIn() && loggedUser(loggedInUser()).getAdmin()){
            Crypto crypto = cryptosRepo.findOne(id);
            crypto.setIsApproved(true);
            cryptosRepo.save(crypto);
            return "redirect:/admin";
        } else {
            return "redirect:/login";
        }
    }
}
