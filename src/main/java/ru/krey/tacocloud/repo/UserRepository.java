package ru.krey.tacocloud.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krey.tacocloud.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
