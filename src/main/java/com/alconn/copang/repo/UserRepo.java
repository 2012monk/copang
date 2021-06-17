package com.alconn.copang.repo;

import com.alconn.copang.common.WeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<WeUser, Long> {

    WeUser findByEmailAndPassword(String email, String password);

    Optional<WeUser> findByEmail(String username);
}
