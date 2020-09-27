package ru.ulxanxv.sharing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulxanxv.sharing.models.Credential;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Credential findByName(String name);

}
