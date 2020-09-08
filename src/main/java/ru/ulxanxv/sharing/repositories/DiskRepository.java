package ru.ulxanxv.sharing.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ulxanxv.sharing.entities.Disk;

public interface DiskRepository extends CrudRepository<Disk, Long> {
}
