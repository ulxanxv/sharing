package ru.ulxanxv.sharing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulxanxv.sharing.entities.TakenItem;

public interface TakenItemRepository extends JpaRepository<TakenItem, Long> {
}
