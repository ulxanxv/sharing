package ru.ulxanxv.sharing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ulxanxv.sharing.models.Disk;
import ru.ulxanxv.sharing.models.TakenItem;

import java.util.List;


public interface TakenItemRepository extends JpaRepository<TakenItem, Long> {

    @Query("SELECT d FROM TakenItem t JOIN Disk d ON t.disk.id = d.id WHERE t.debtor.id = :id")
    List<Disk> findTakenDisks(@Param("id") Long id);

    @Query("SELECT d FROM Disk d JOIN TakenItem t ON d.id = t.disk.id WHERE d.id = :id AND t.isFree = true")
    Disk findFreeDisk(@Param("id") Long id);

    @Query("SELECT t FROM TakenItem t WHERE t.disk.id = :id")
    TakenItem findByDiskId(@Param("id") Long id);

    @Query("SELECT d.id, d.name, t.debtor.name FROM Disk d JOIN TakenItem t ON d.id = t.disk.id WHERE t.owner.id = :id AND t.debtor IS NOT NULL")
    List<Object[]> findTakenItemFromUser(@Param("id") Long id);

    @Query("SELECT t FROM TakenItem t WHERE t.disk.id = :id")
    TakenItem findReturningDisk(@Param("id") Long id);

}
