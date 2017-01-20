package com.codeup.auth;

import com.codeup.models.Cryptos;
import com.codeup.models.UserCryptos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.codeup.auth.BaseController.isLoggedIn;
import static com.codeup.auth.BaseController.loggedInUser;

@Controller
public class AuthenticationController {

    @Autowired
    Users userDao;

    @Autowired
    Cryptos cryptosRepo;

    private User loggedUser(User loggedInUser){
        return userDao.findOne(loggedInUser().getId());
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "users/register";
    }

//  TODO: Implement automatic login after registration, maybe
    @PostMapping("/register")
    public String createUser(@Valid User user, Errors validation, Model model) {
        if (validation.hasErrors()) {
            model.addAttribute(validation.getAllErrors());
            model.addAttribute("user", user);
            return "users/register";
        }
        userDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable long id, Model model){
        User user = userDao.findOne(id);
        model.addAttribute("user", user);
        model.addAttribute("loggedInUser", loggedUser(loggedInUser()));
        model.addAttribute("isAdmin", isLoggedIn() && loggedUser(loggedInUser()).getAdmin());
        model.addAttribute("showEditControls", isLoggedIn() && loggedUser(loggedInUser()).getId() == user.getId());
        return "/users/profile";
    }

    @GetMapping("/users/profile")
    public String personalProfile(){
        if(isLoggedIn()){
            return ("redirect:/users/"+loggedUser(loggedInUser()).getId());
        } else {
            return "redirect:/login";
        }
    }

//  TODO: Doesn't actually send to admin currently - always redirects to /login...
    @GetMapping("/admin")
    public String adminPage(Model model){
        System.out.println(isLoggedIn());
        System.out.println(loggedUser(loggedInUser()).getAdmin());
        if(isLoggedIn() && loggedUser(loggedInUser()).getAdmin()){
            model.addAttribute("activeUnapproved",cryptosRepo.findByActiveEqualsAndIsApprovedEquals(true, false));
            return "/admin";
        } else {
            return "redirect:/login";
        }
    }

    @ResponseBody
    @GetMapping("/test")
    public String test() {

        System.out.println(loggedUser(loggedInUser()).getId());
        System.out.println(loggedUser(loggedInUser()).getEmail());
        System.out.println(loggedUser(loggedInUser()).getAdmin());

        return "ok";
    }
}