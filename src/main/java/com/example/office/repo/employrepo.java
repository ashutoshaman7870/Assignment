package com.example.office.repo;

import com.example.office.Entity.employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface employrepo extends JpaRepository<employee,Integer> {

    employee findByEmail(String email);
}
