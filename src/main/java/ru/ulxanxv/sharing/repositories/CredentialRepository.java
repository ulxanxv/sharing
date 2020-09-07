package ru.ulxanxv.sharing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulxanxv.sharing.entities.Credential;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Credential findByName(String name);

}
