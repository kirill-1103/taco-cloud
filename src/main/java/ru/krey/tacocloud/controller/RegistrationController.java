package ru.krey.tacocloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.krey.tacocloud.other.RegistrationForm;
import ru.krey.tacocloud.repo.UserRepository;

@Controller
@RequestMapping("/register")
@Slf4j
public class RegistrationController {
    @Autowired
    private final UserRepository userRepo;

    @Autowired
    private final PasswordEncoder encoder;

    public RegistrationController(UserRepository userRepo, PasswordEncoder encoder){
        this.userRepo=userRepo;
        this.encoder=encoder;
    }

    @GetMapping
    public String registerForm(){
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form){
        log.info(form.getFullname());
        userRepo.save(form.toUser(encoder));
        return "redirect:/login";
    }
}
