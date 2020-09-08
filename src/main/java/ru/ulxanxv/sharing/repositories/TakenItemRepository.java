package ru.ulxanxv.sharing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ulxanxv.sharing.entities.Disk;
import ru.ulxanxv.sharing.entities.TakenItem;

import java.util.List;

public interface TakenItemRepository extends JpaRepository<TakenItem, Long> {

    @Query("SELECT t FROM TakenItem t WHERE t.isFree = :isFree")
    List<TakenItem> findByFree(@Param("isFree") boolean isFree);

    @Query("SELECT d.id, d.name, d.debtor.name FROM Disk d INNER JOIN TakenItem t ON d.id = t.disk.id WHERE d.owner.id = :id AND d.debtor IS NOT NULL")
    List<Object[]> findTakenItemFromUser(@Param("id") Long id);

    List<TakenItem> findByDebtorId(Long id);

}
