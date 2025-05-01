package com.Wasfa.front_end.repository;

import com.Wasfa.front_end.Entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
     Optional<Employe> findByEmail(String email);
}
