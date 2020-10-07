package ru.ulxanxv.sharing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulxanxv.sharing.models.Auxiliary;
import ru.ulxanxv.sharing.models.Client;
import ru.ulxanxv.sharing.models.Disk;
import ru.ulxanxv.sharing.repositories.ClientRepository;
import ru.ulxanxv.sharing.repositories.CredentialRepository;
import ru.ulxanxv.sharing.repositories.DiskRepository;
import ru.ulxanxv.sharing.repositories.TakenItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DiskInfoService {

    private Long authenticatedId;

    private final ClientRepository clientRepository;
    private final TakenItemRepository takenItemRepository;
    private final DiskRepository diskRepository;


    @Autowired
    public DiskInfoService(ClientRepository clientRepository, TakenItemRepository takenItemRepository, DiskRepository diskRepository) {
        this.clientRepository = clientRepository;
        this.takenItemRepository = takenItemRepository;
        this.diskRepository = diskRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<Disk>> allUserDisks() {
        Optional<Client> byId = clientRepository.findById(authenticatedId);

        if (byId.isPresent()) {
            return ResponseEntity.ok(diskRepository.findAllDisks(byId.get().getId()));
        }

        throw new NoSuchElementException("User not found!");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<Disk>> allFreeDisks() {
        return ResponseEntity.ok(diskRepository.findFreeDisks(authenticatedId));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<Disk>> allTakenDisksByUser() {
        List<Disk> getTakenDisks = takenItemRepository.findTakenDisks(authenticatedId);
        return ResponseEntity.ok(getTakenDisks);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<Auxiliary>> allTakenDisksFromUser() {
        List<Auxiliary> getTakenDisksFromUser = new ArrayList<>();
        takenItemRepository.findTakenItemFromUser(authenticatedId).forEach(x -> getTakenDisksFromUser.add(Auxiliary.getInstance(x)));
        return ResponseEntity.ok(getTakenDisksFromUser);
    }

    public void setAuthenticatedId(Long authenticatedId) {
        this.authenticatedId = authenticatedId;
    }
}
