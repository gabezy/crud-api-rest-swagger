package com.gabezy.projects.api.repositories;

import com.gabezy.projects.api.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
