package ru.ulxanxv.sharing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.*;
import ru.ulxanxv.sharing.aspects.DefineId;
import ru.ulxanxv.sharing.aspects.IDefineId;
import ru.ulxanxv.sharing.models.Auxiliary;
import ru.ulxanxv.sharing.models.Disk;
import ru.ulxanxv.sharing.repositories.ClientRepository;
import ru.ulxanxv.sharing.repositories.CredentialRepository;
import ru.ulxanxv.sharing.services.DiskInfoService;
import ru.ulxanxv.sharing.services.DiskSharingService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class MainController implements IDefineId {

    private Long authenticatedId;

    private final DiskInfoService diskInfoService;
    private final DiskSharingService diskSharingService;

    private final CredentialRepository credentialRepository;
    private final ClientRepository clientRepository;


    @Autowired
    public MainController(DiskInfoService diskInfoService,
                          DiskSharingService diskSharingService,
                          CredentialRepository credentialRepository,
                          ClientRepository clientRepository) {
        this.diskInfoService = diskInfoService;
        this.diskSharingService = diskSharingService;
        this.credentialRepository = credentialRepository;
        this.clientRepository = clientRepository;
    }

    @DefineId
    @GetMapping("/")
    public ResponseEntity<?> welcome() {
        String userName = clientRepository.findById(authenticatedId)
                .get()
                .getName();

        diskSharingService.setAuthenticatedId(authenticatedId);
        diskInfoService.setAuthenticatedId(authenticatedId);

        return ResponseEntity.ok("Welcome to my REST-application project, " + userName + "!");
    }

    @GetMapping("/disks")
    public ResponseEntity<List<Disk>> disks() {
        return diskInfoService.allUserDisks();
    }

    @GetMapping("/free_disks")
    public ResponseEntity<List<Disk>> freeDisks() {
        return diskInfoService.allFreeDisks();
    }

    @GetMapping("/taken_disks_by_me")
    public ResponseEntity<List<Disk>> takenDisksByMe() {
        return diskInfoService.allTakenDisksByUser();
    }

    @GetMapping("/taken_disks_from_me")
    public ResponseEntity<List<Auxiliary>> takenDisksFromMe() {
        return diskInfoService.allTakenDisksFromUser();
    }

    @PostMapping("/getDisk/{id}")
    public ResponseEntity<?> getDisk(@PathVariable("id") Long id) {
        return diskSharingService.getDisk(id);
    }

    @PostMapping("/returnDisk/{id}")
    public ResponseEntity<?> returnDisk(@PathVariable("id") Long id) {
        return diskSharingService.returnDisk(id);
    }

    @Override
    public void defineAuthenticatedId() {
        String userName;
        User auth = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (auth != null) {
            userName = auth.getUsername();
            try {
                this.authenticatedId = credentialRepository.findByName(userName).getId();
            } catch (CannotCreateTransactionException ignored) {}
        }
    }

}
