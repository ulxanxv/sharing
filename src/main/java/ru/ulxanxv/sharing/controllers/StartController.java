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
import ru.ulxanxv.sharing.entities.TakenItem;
import ru.ulxanxv.sharing.repositories.ClientRepository;
import ru.ulxanxv.sharing.repositories.CredentialRepository;
import ru.ulxanxv.sharing.repositories.TakenItemRepository;

import java.util.ArrayList;
import java.util.Collections;

@RestController
@RequestMapping("/start")
public class StartController {

    private CredentialRepository credentialRepository;

    private ClientRepository clientRepository;

    private PasswordEncoder passwordEncoder;

    private TakenItemRepository takenItemRepository;

    @Autowired
    public StartController(CredentialRepository credentialRepository, ClientRepository clientRepository, PasswordEncoder passwordEncoder, TakenItemRepository takenItemRepository) {
        this.credentialRepository = credentialRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.takenItemRepository = takenItemRepository;
    }

    /**
     * Populating the database with initial values
     * @return
     */
    @GetMapping
    public ResponseEntity<?> start() {

        // First credential
        Credential credential = new Credential();
        credential.setName("Ulxanxv");
        credential.setPassword(passwordEncoder.encode("1234"));
        credentialRepository.save(credential);

        // Second credential
        Credential credential1 = new Credential();
        credential1.setName("Amberd");
        credential1.setPassword(passwordEncoder.encode("alowen"));
        credentialRepository.save(credential1);

        // Third credential
        Credential credential2 = new Credential();
        credential2.setName("Ynagan");
        credential2.setPassword(passwordEncoder.encode("vatene"));
        credentialRepository.save(credential2);

        // First client
        Client client = new Client();
        client.setName("Ulxanxv");
        client.setCredential(credential);

        Disk disk = new Disk();
        disk.setName("OneDisk");
        disk.setOwner(client);

        TakenItem takenItem = new TakenItem();
        takenItem.setDisk(disk);
        takenItemRepository.save(takenItem);

        Disk disk1 = new Disk();
        disk1.setName("SecondDisk");
        disk1.setOwner(client);

        takenItem = new TakenItem();
        takenItem.setDisk(disk1);
        takenItemRepository.save(takenItem);

        Disk disk2 = new Disk();
        disk2.setName("ThirdDisk");
        disk2.setOwner(client);

        takenItem = new TakenItem();
        takenItem.setDisk(disk2);
        takenItemRepository.save(takenItem);

        client.setDisks(new ArrayList<>());
        client.getDisks().add(disk);
        client.getDisks().add(disk1);
        client.getDisks().add(disk2);

        clientRepository.save(client);

        // Second client
        Client client1 = new Client();
        client1.setName("Amberd");
        client1.setCredential(credential1);

        disk = new Disk();
        disk.setName("FourthDisk");
        disk.setOwner(client1);

        takenItem = new TakenItem();
        takenItem.setDisk(disk);
        takenItemRepository.save(takenItem);

        disk1 = new Disk();
        disk1.setName("FifthDisk");
        disk1.setOwner(client1);

        takenItem = new TakenItem();
        takenItem.setDisk(disk1);
        takenItemRepository.save(takenItem);

        client1.setDisks(new ArrayList<>());
        client1.getDisks().add(disk);
        client1.getDisks().add(disk1);
        clientRepository.save(client1);

        // Third client
        client = new Client();
        client.setName("Ynagan");
        client.setCredential(credential2);

        disk = new Disk();
        disk.setName("SixthDisk");
        disk.setDebtor(client1);
        disk.setOwner(client);

        takenItem = new TakenItem();
        takenItem.setDisk(disk);
        takenItem.setDebtor(client1);
        takenItemRepository.save(takenItem);

        disk1 = new Disk();
        disk1.setName("SeventhDisk");
        disk1.setDebtor(client1);
        disk1.setOwner(client);

        takenItem = new TakenItem();
        takenItem.setDisk(disk1);
        takenItem.setDebtor(client1);
        takenItemRepository.save(takenItem);

        client.setDisks(new ArrayList<>());
        client.getDisks().add(disk);
        client.getDisks().add(disk1);
        clientRepository.save(client);

        return ResponseEntity.ok(Collections.EMPTY_LIST);
    }

}
