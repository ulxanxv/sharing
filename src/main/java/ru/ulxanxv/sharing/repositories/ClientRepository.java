package ru.ulxanxv.sharing.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ulxanxv.sharing.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
