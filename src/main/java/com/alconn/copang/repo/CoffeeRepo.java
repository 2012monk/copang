package com.alconn.copang.repo;

import com.alconn.copang.models.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepo extends JpaRepository<Coffee, Long> {
}
