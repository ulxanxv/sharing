package ru.ulxanxv.sharing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulxanxv.sharing.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {}
