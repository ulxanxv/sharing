package ru.ulxanxv.sharing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulxanxv.sharing.models.Auxiliary;
import ru.ulxanxv.sharing.models.Client;
import ru.ulxanxv.sharing.models.Disk;
import ru.ulxanxv.sharing.repositories.ClientRepository;
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
    public List<Disk> allUserDisks() {
        Optional<Client> byId = clientRepository.findById(authenticatedId);

        if (byId.isPresent()) {
            return diskRepository.findAllDisks(byId.get().getId());
        }

        throw new NoSuchElementException("User not found!");
    }

    @Transactional(readOnly = true)
    public List<Disk> allFreeDisks() {
        return diskRepository.findFreeDisks(authenticatedId);
    }

    @Transactional(readOnly = true)
    public List<Disk> allTakenDisksByUser() {
        return takenItemRepository.findTakenDisks(authenticatedId);
    }

    @Transactional(readOnly = true)
    public List<Auxiliary> allTakenDisksFromUser() {
        List<Auxiliary> takenDisksFromUser = new ArrayList<>();
        takenItemRepository.findTakenItemFromUser(authenticatedId).forEach(x -> takenDisksFromUser.add(Auxiliary.getInstance(x)));
        return takenDisksFromUser;
    }

    public void setAuthenticatedId(Long authenticatedId) {
        this.authenticatedId = authenticatedId;
    }
}
