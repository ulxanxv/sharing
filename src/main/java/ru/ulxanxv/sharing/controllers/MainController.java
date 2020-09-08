package ru.ulxanxv.sharing.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import ru.ulxanxv.sharing.entities.*;
import ru.ulxanxv.sharing.repositories.ClientRepository;
import ru.ulxanxv.sharing.repositories.CredentialRepository;
import ru.ulxanxv.sharing.repositories.DiskRepository;
import ru.ulxanxv.sharing.repositories.TakenItemRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MainController {

    private Long authenticatedId;


    private CredentialRepository credentialRepository;

    private ClientRepository clientRepository;

    private TakenItemRepository takenItemRepository;

    private DiskRepository diskRepository;

    @Autowired
    public MainController(CredentialRepository credentialRepository, ClientRepository clientRepository, TakenItemRepository takenItemRepository, DiskRepository diskRepository) {
        this.credentialRepository = credentialRepository;
        this.clientRepository = clientRepository;
        this.takenItemRepository = takenItemRepository;
        this.diskRepository = diskRepository;
    }

    @GetMapping
    public ResponseEntity<?> welcome() {
        defineAuthenticatedId();

        String userName = clientRepository.findById(authenticatedId)
                .get()
                .getName();
        return ResponseEntity.ok("Welcome to my REST-application project, " + userName + "!");
    }

    @GetMapping("user/getMyDisks")
    public ResponseEntity<List<Disk>> getAllUserDisks() {
        List<Disk> disks = clientRepository.findById(authenticatedId)
                .get()
                .getDisks();
        return ResponseEntity.ok(disks);
    }

    @GetMapping("user/getFreeDisks")
    public ResponseEntity<List<Disk>> getFreeDisks() {
        List<Disk> freeDisks = takenItemRepository.findByFree(true)
                .stream()
                .map(TakenItem::getDisk)
                .collect(Collectors.toList());
        return ResponseEntity.ok(freeDisks);
    }

    @GetMapping("user/getTakenDisks")
    public ResponseEntity<List<Disk>> getTakenDisks() {
        List<Disk> getTakenDisks = takenItemRepository.findByDebtorId(authenticatedId)
                .stream()
                .map(TakenItem::getDisk)
                .collect(Collectors.toList());
        return ResponseEntity.ok(getTakenDisks);
    }

    @GetMapping("user/getTakenFromUser")
    public ResponseEntity<List<Auxiliary>> getTakenFromUser() {
        List<Auxiliary> getTakenDisksFromUser = new ArrayList<>();
        takenItemRepository.findTakenItemFromUser(authenticatedId).forEach(x -> getTakenDisksFromUser.add(Auxiliary.getInstance(x)));
        return ResponseEntity.ok(getTakenDisksFromUser);
    }

    @GetMapping("/user/getDisk/{id}")
    public ResponseEntity<?> getDisk(@PathVariable("id") Long id) {
        Disk freeDisk = takenItemRepository.findFreeDisk(id);
        if (freeDisk == null) {
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }

        Client client = clientRepository.findById(authenticatedId).get();
        if (client.getName().equals(freeDisk.getOwner().getName())) {
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }

        TakenItem takenItem = takenItemRepository.findByDiskId(freeDisk.getId());
        freeDisk.setDebtor(client);
        takenItem.setDebtor(client);

        takenItemRepository.save(takenItem);
        diskRepository.save(freeDisk);

        return ResponseEntity.ok(Collections.EMPTY_LIST);
    }

    @GetMapping("/user/returnDisk/{id}")
    public ResponseEntity<?> returnDisk(@PathVariable("id") Long id) {
        Disk busyDisk = diskRepository.findById(id).get();
        if (busyDisk.getDebtor() == null) {
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }

        Client client = clientRepository.findById(authenticatedId).get();
        if (client.getName().equals(busyDisk.getOwner().getName())) {
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }

        TakenItem takenItem = takenItemRepository.findByDiskId(busyDisk.getId());
        busyDisk.setDebtor(null);
        takenItem.setDebtor(null);

        takenItemRepository.save(takenItem);
        diskRepository.save(busyDisk);

        return ResponseEntity.ok(Collections.EMPTY_LIST);
    }

    private void defineAuthenticatedId() {
        String userName;
        Credential auth = ((Credential) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (auth != null) {
            userName = auth.getName();
            try {
                this.authenticatedId = credentialRepository.findByName(userName).getId();
            } catch (CannotCreateTransactionException ignored) {}
        }
    }

}
