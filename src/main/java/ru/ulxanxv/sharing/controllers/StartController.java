package ru.ulxanxv.sharing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ulxanxv.sharing.entities.Client;
import ru.ulxanxv.sharing.entities.Credential;
import ru.ulxanxv.sharing.entities.Disk;
import ru.ulxanxv.sharing.repositories.ClientRepository;
import ru.ulxanxv.sharing.repositories.CredentialRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("/start")
public class StartController {

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<?> start() {

        // First credential
        Credential credential = new Credential();
        credential.setName("Ulxanxv");
        credential.setPassword(passwordEncoder.encode("nakaro"));
        credentialRepository.save(credential);

        // Second credential
        credential = new Credential();
        credential.setName("Amberd");
        credential.setPassword(passwordEncoder.encode("alowen"));
        credentialRepository.save(credential);

        // Third credential
        credential = new Credential();
        credential.setName("Ynagan");
        credential.setPassword(passwordEncoder.encode("vatene"));
        credentialRepository.save(credential);

        // First client
        Client client = new Client();
        client.setName("Ulxanxv");
        client.setCredential(credential);

        Disk disk = new Disk();
        disk.setName("OneDisk");
        disk.setFirstOwner(client);

        Disk disk1 = new Disk();
        disk1.setName("SecondDisk");
        disk1.setFirstOwner(client);

        Disk disk2 = new Disk();
        disk2.setName("ThirdDisk");
        disk2.setFirstOwner(client);

        client.setDisks(Arrays.asList(disk, disk1, disk2));
        clientRepository.save(client);

        // Second client
        client = new Client();
        client.setName("Amberd");
        client.setCredential(credential);

        disk = new Disk();
        disk.setName("FourthDisk");
        disk.setFirstOwner(client);

        disk1 = new Disk();
        disk1.setName("FifthDisk");
        disk1.setFirstOwner(client);

        client.setDisks(Arrays.asList(disk, disk1));
        clientRepository.save(client);

        return ResponseEntity.ok(Collections.EMPTY_LIST);
    }

}
