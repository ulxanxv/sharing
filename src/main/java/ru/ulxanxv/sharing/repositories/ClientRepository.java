package ru.ulxanxv.sharing.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ulxanxv.sharing.models.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
