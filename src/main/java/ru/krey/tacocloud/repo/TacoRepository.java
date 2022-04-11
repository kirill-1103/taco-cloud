package ru.krey.tacocloud.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krey.tacocloud.model.Taco;

public interface TacoRepository extends JpaRepository<Taco,Long> {
    Taco save(Taco design);
}
