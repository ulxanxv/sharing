package ru.ulxanxv.sharing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.*;
import ru.ulxanxv.sharing.models.Auxiliary;
import ru.ulxanxv.sharing.models.Disk;
import ru.ulxanxv.sharing.repositories.CredentialRepository;
import ru.ulxanxv.sharing.services.DiskInfoService;
import ru.ulxanxv.sharing.services.DiskSharingService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class MainController implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    private Long authenticatedId;

    private final DiskInfoService diskInfoService;
    private final DiskSharingService diskSharingService;

    private final CredentialRepository credentialRepository;


    @Autowired
    public MainController(DiskInfoService diskInfoService,
                          DiskSharingService diskSharingService,
                          CredentialRepository credentialRepository) {
        this.diskInfoService = diskInfoService;
        this.diskSharingService = diskSharingService;
        this.credentialRepository = credentialRepository;
    }

    @GetMapping("/disks/all")
    public ResponseEntity<List<Disk>> disks() {
        return ResponseEntity.ok(diskInfoService.allUserDisks());
    }

    @GetMapping("/disks/free")
    public ResponseEntity<List<Disk>> freeDisks() {
        return ResponseEntity.ok(diskInfoService.allFreeDisks());
    }

    @GetMapping("/disks/taken/by_me")
    public ResponseEntity<List<Disk>> takenDisksByMe() {
        return ResponseEntity.ok(diskInfoService.allTakenDisksByUser());
    }

    @GetMapping("/disks/taken/from_me")
    public ResponseEntity<List<Auxiliary>> takenDisksFromMe() {
        return ResponseEntity.ok(diskInfoService.allTakenDisksFromUser());
    }

    @PutMapping("/disk/take/{id}")
    public ResponseEntity<?> takeDisk(@PathVariable("id") Long id) {
        return ResponseEntity.ok(diskSharingService.takeDisk(id));
    }

    @PutMapping("/disk/return/{id}")
    public ResponseEntity<?> returnDisk(@PathVariable("id") Long id) {
        return ResponseEntity.ok(diskSharingService.returnDisk(id));
    }

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent interactiveAuthenticationSuccessEvent) {
        User auth = ((User) interactiveAuthenticationSuccessEvent.getAuthentication().getPrincipal());

        if (auth != null) {
            String userName = auth.getUsername();
            try {
                this.authenticatedId = credentialRepository.findByName(userName).getId();

                diskSharingService.setAuthenticatedId(authenticatedId);
                diskInfoService.setAuthenticatedId(authenticatedId);
            } catch (CannotCreateTransactionException ignored) {}
        }
    }
}
