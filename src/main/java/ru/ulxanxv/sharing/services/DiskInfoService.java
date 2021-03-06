package ru.ulxanxv.sharing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulxanxv.sharing.models.Auxiliary;
import ru.ulxanxv.sharing.models.Client;
import ru.ulxanxv.sharing.models.Disk;
import ru.ulxanxv.sharing.repositories.ClientRepository;
import ru.ulxanxv.sharing.repositories.CredentialRepository;
import ru.ulxanxv.sharing.repositories.DiskRepository;
import ru.ulxanxv.sharing.repositories.TakenItemRepository;
import ru.ulxanxv.sharing.security.DefineId;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DiskInfoService extends DefineId {

    private final ClientRepository clientRepository;
    private final TakenItemRepository takenItemRepository;
    private final DiskRepository diskRepository;

    @Autowired
    public DiskInfoService(ClientRepository clientRepository,
                           TakenItemRepository takenItemRepository,
                           DiskRepository diskRepository,
                           CredentialRepository credentialRepository) {
        super(credentialRepository);
        this.clientRepository = clientRepository;
        this.takenItemRepository = takenItemRepository;
        this.diskRepository = diskRepository;
    }

    @Transactional(readOnly = true)
    public List<Disk> userDisks() {
        Optional<Client> byId = clientRepository.findById(authenticatedId);

        if (byId.isPresent()) {
            return diskRepository.findAllDisks(byId.get().getId());
        }

        throw new NoSuchElementException("User not found!");
    }

    @Transactional(readOnly = true)
    public List<Disk> freeDisks() {
        return diskRepository.findFreeDisks(authenticatedId);
    }

    @Transactional(readOnly = true)
    public List<Disk> takenDisks() {
        return takenItemRepository.findTakenDisks(authenticatedId);
    }

    @Transactional(readOnly = true)
    public List<Auxiliary> givenDisks() {
        List<Auxiliary> takenDisksFromUser = new ArrayList<>();
        takenItemRepository.findTakenItemFromUser(authenticatedId).forEach(x -> takenDisksFromUser.add(Auxiliary.getInstance(x)));
        return takenDisksFromUser;
    }

}
