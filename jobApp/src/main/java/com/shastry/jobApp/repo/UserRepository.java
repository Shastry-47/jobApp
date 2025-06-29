package com.shastry.jobApp.repo;

import com.shastry.jobApp.model.Role;
import com.shastry.jobApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndRole(String email, Role role);

}

