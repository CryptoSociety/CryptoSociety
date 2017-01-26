package com.codeup.auth;

import com.codeup.models.Cryptos;
import com.codeup.models.UserCryptos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeup.auth.BaseController.isLoggedIn;
import static com.codeup.auth.BaseController.loggedInUser;

@Controller
public class AuthenticationController {

    @Autowired
    Users userDao;

    @Autowired
    Cryptos cryptosRepo;

    private User loggedUser() {
        return userDao.findOne(loggedInUser().getId());
    }

    @GetMapping("/login")
    public String showLoginForm() {
        if (isLoggedIn()) {
            return "redirect:/users/profile";
        } else {
            return "users/login";
        }
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        if (isLoggedIn()) {
            return "redirect:/users/profile";
        } else {
            model.addAttribute("user", new User());
            return "users/register";
        }
    }

    //  TODO: Implement automatic login after registration, maybe
    @PostMapping("/register")
    public String createUser(@Valid User user, Errors validation, Model model) {
        if (validation.hasErrors()) {
            model.addAttribute(validation.getAllErrors());
            model.addAttribute("user", user);
            return "users/register";
        }
        String username = user.getUsername();
        boolean hasError = false;
        if (userDao.findByUsername(username) != null) {
            model.addAttribute("usernameError", "Username is already taken");
            hasError = true;
        } else if (username.trim().isEmpty() || username.trim().length() < 5 || username.trim().length() > 40) {
            model.addAttribute("usernameError", "Username must be between 5 and 40 characters long");
            hasError = true;
        } else if (username.contains(" ")) {
            model.addAttribute("Username cannot contain spaces. Underscores and hyphens are okay, though.");
            hasError = true;
        } else {
            String email = user.getEmail();
            Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            if (userDao.findByEmail(email) != null) {
                model.addAttribute("emailError", "Email is already taken");
                hasError = true;
            } else if (!matcher.find()) {
                model.addAttribute("emailError", "Please enter a valid email address.");
                hasError = true;
            } else {
                String password = user.getPassword();
                if (password.equalsIgnoreCase("Invalid entry")) {
                    model.addAttribute("passwordError", "Password cannot be blank and must be between 5 and 40 characters");
                    hasError = true;
                }
            }
        }
        if (hasError) {
            model.addAttribute("user", user);
            return "users/register";
        } else {
            user.setAdmin(false);
            userDao.save(user);
            return "redirect:/login";
        }
    }

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable long id, Model model) {
        User user = userDao.findOne(id);
        model.addAttribute("user", user);
        if (isLoggedIn()) {
            model.addAttribute("loggedInUser", loggedUser());
            model.addAttribute("isAdmin", isLoggedIn() && loggedUser().getAdmin());
            model.addAttribute("showEditControls", isLoggedIn() && loggedUser().getId() == user.getId());
        }
        return "/users/profile";
    }

    @GetMapping("/users/profile")
    public String personalProfile() {
        if (isLoggedIn()) {
            return ("redirect:/users/" + loggedUser().getId());
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/users/{id}/settings")
    public String userSettingsGet(@PathVariable long id, Model model) {
        if (isLoggedIn() && loggedUser().getId() == id) {
            model.addAttribute("user", userDao.findOne(id));
            return "/users/edit";
        } else {
            return "redirect:/users/" + id;
        }
    }

    @PostMapping("/users/{id}/settings")
    public String userSettingsPost(@PathVariable long id, User updatedUser, Model model, Errors errors, HttpServletRequest request) throws Exception {
        if (isLoggedIn() && loggedUser().getId() == id) {
            boolean hasError = false;
            User user = userDao.findOne(id);

            if (!request.getParameter("currentpassword").isEmpty() && !request.getParameter("newpassword").trim().isEmpty() && !request.getParameter("confirmpassword").trim().isEmpty()) {
                if (BCrypt.checkpw(request.getParameter("currentpassword"), user.getPassword())) {
                    if (request.getParameter("newpassword").equals(request.getParameter("confirmpassword"))) {
                        if (request.getParameter("newpassword").trim().length() < 5 || request.getParameter("newpassword").trim().length() > 40) {
                            model.addAttribute("passwordError", "Password must be 5-40 characters");
                            hasError = true;
                        } else {
                            user.setPassword(request.getParameter("newpassword"));
                        }
                    } else {
                        model.addAttribute("passwordError", "New password and confirm password did not match!");
                        hasError = true;
                    }
                } else {
                    model.addAttribute("passwordError", "Incorrect password");
                    hasError = true;
                }
            }

            if (!request.getParameter("username").isEmpty()) {
                String username = request.getParameter("username");
                if (userDao.findByUsername(username) == null || userDao.findByUsername(username).getId() == user.getId()) {
                    user.setUsername(username);
                } else {
                    model.addAttribute("usernameError", "Username is already taken");
                    hasError = true;
                }
                if (username.trim().isEmpty() || username.trim().length() < 5 || username.trim().length() > 40) {
                    model.addAttribute("usernameError", "Username must be between 5 and 40 characters long");
                    hasError = true;
                } else if (username.contains(" ")) {
                    model.addAttribute("Username cannot contain spaces. Underscores and hyphens are okay, though.");
                }
            }

            if (!request.getParameter("email").isEmpty()) {
                String email = request.getParameter("email");
                if (userDao.findByEmail(email) == null || userDao.findByEmail(email).getId() == user.getId()) {
                    user.setEmail(email);
                } else {
                    model.addAttribute("emailError", "Email is already taken");
                    hasError = true;
                }
                Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
                if (!matcher.find()) {
                    model.addAttribute("emailError", "Please enter a valid email address.");
                    hasError = true;
                }
            }

            if (hasError) {
                model.addAttribute("user", user);
                return "/users/edit";
            } else {
                userDao.save(user);
                return "redirect:/users/profile";
            }
        } else {
            return "redirect:/users/profile";
        }
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        if (isLoggedIn() && loggedUser().getAdmin()) {
            model.addAttribute("activeUnapproved", cryptosRepo.findByActiveEqualsAndIsApprovedEquals(true, false));
            return "/admin";
        } else {
            return "redirect:/login";
        }
    }
    @GetMapping("/leaderboard")
    public String leaderboard(Model model){
        model.addAttribute("leaderboardUserList", userDao.findTop10ByOrderByPointsDesc());
        return "/users/leaderboard";
    }
}