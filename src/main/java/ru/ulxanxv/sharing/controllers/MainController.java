package ru.ulxanxv.sharing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ulxanxv.sharing.entities.Disk;
import ru.ulxanxv.sharing.repositories.ClientRepository;
import ru.ulxanxv.sharing.repositories.CredentialRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class MainController {

    private Long authenticatedId;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/getDisks")
    public ResponseEntity<List<Disk>> getAllUserDisks() {
        defineAuthenticatedId();
        List<Disk> disks = clientRepository.findById(authenticatedId)
                .get()
                .getDisks();

        return ResponseEntity.ok(disks);
    }

    private void defineAuthenticatedId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName     = null;

        if (auth != null) {
            Authentication principal = SecurityContextHolder.getContext().getAuthentication();
            userName = principal.getName();

            try {
                this.authenticatedId = credentialRepository.findByName(userName)
                        .getClient()
                        .getId();
            } catch (CannotCreateTransactionException ignored) {}
        }
    }

}
