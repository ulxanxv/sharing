package ru.ulxanxv.sharing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import ru.ulxanxv.sharing.entities.Credential;
import ru.ulxanxv.sharing.entities.Disk;
import ru.ulxanxv.sharing.repositories.ClientRepository;
import ru.ulxanxv.sharing.repositories.CredentialRepository;

import java.util.List;

@RestController
@RequestMapping("/user")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
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
        Credential auth = ((Credential) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String userName;

        if (auth != null) {
            userName = auth.getName();
            
            try {
                this.authenticatedId = credentialRepository.findByName(userName).getId();
            } catch (CannotCreateTransactionException ignored) {}
        }

    }

}
