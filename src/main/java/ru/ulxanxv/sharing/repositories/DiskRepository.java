package ru.ulxanxv.sharing.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.ulxanxv.sharing.models.Disk;

import java.util.List;


public interface DiskRepository extends CrudRepository<Disk, Long> {

    @Query("SELECT d FROM TakenItem t JOIN Disk d ON t.disk.id = d.id WHERE t.owner.id = :id")
    List<Disk> findAllDisks(@Param("id") Long id);

    @Query("SELECT d FROM Disk d JOIN TakenItem t ON d.id = t.disk.id WHERE t.isFree = true AND t.owner.id <> :id")
    List<Disk> findFreeDisks(@Param("id") Long id);

}
