package ru.ulxanxv.sharing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ulxanxv.sharing.entities.Credential;
import ru.ulxanxv.sharing.repositories.CredentialRepository;

import java.util.Collections;

@RestController
@RequestMapping("/start")
public class StartController {

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<?> start() {

        // First credential
        Credential user = new Credential();
        user.setName("Ulxanxv");
        user.setPassword(passwordEncoder.encode("nakaro"));
        credentialRepository.save(user);

        // Second credential
        user = new Credential();
        user.setName("Amberd");
        user.setPassword(passwordEncoder.encode("alowen"));
        credentialRepository.save(user);

        // Third credential
        user = new Credential();
        user.setName("Ynagan");
        user.setPassword(passwordEncoder.encode("vatene"));
        credentialRepository.save(user);

        return ResponseEntity.ok(Collections.EMPTY_LIST);
    }

}
