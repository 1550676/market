package ru.zakharova.elena.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zakharova.elena.market.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}