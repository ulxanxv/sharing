package ru.ulxanxv.sharing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulxanxv.sharing.models.Client;
import ru.ulxanxv.sharing.models.TakenItem;
import ru.ulxanxv.sharing.repositories.ClientRepository;
import ru.ulxanxv.sharing.repositories.CredentialRepository;
import ru.ulxanxv.sharing.repositories.TakenItemRepository;
import ru.ulxanxv.sharing.security.DefineId;

import java.util.Collections;
import java.util.Optional;

@Service
public class DiskSharingService extends DefineId {

    private final ClientRepository clientRepository;
    private final TakenItemRepository takenItemRepository;

    @Autowired
    public DiskSharingService(ClientRepository clientRepository,
                              TakenItemRepository takenItemRepository,
                              CredentialRepository credentialRepository) {
        super(credentialRepository);
        this.clientRepository = clientRepository;
        this.takenItemRepository = takenItemRepository;
    }

    @Transactional
    public ResponseEntity<?> takeDisk(Long id) {
        TakenItem disk = takenItemRepository.findFreeDisk(id);

        if (disk == null) {
            return ResponseEntity.badRequest().body("This disk is busy!");
        }

        Optional<Client> byId = clientRepository.findById(authenticatedId);

        if (byId.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found!");
        }
        Client client = byId.get();

        if (client.getName().equals(disk.getOwner().getName())) {
            return ResponseEntity.badRequest().body("You cannot borrow your disc from yourself!");
        }

        TakenItem takenItem = takenItemRepository.findByDiskId(disk.getId());
        takenItem.setDebtor(client);
        takenItemRepository.save(takenItem);

        return ResponseEntity.ok(Collections.EMPTY_LIST);

    }

    @Transactional
    public ResponseEntity<?> returnDisk(Long id) {
        TakenItem returningDisk = takenItemRepository.findReturningDisk(id);

        if (returningDisk.getDebtor() == null) {
            return ResponseEntity.badRequest().body("Nobody borrowed this disc!");
        }

        Optional<Client> byId = clientRepository.findById(authenticatedId);

        if (byId.isEmpty()) {
            return ResponseEntity.ok(Collections.EMPTY_LIST);
        }
        Client client = byId.get();

        if (!client.getName().equals(returningDisk.getDebtor().getName())) {
            return ResponseEntity.badRequest().body("You cannot return this disc!");
        }

        TakenItem takenItem = takenItemRepository.findByDiskId(returningDisk.getId());
        takenItem.setDebtor(null);
        takenItemRepository.save(takenItem);

        return ResponseEntity.ok(Collections.EMPTY_LIST);

    }

}
