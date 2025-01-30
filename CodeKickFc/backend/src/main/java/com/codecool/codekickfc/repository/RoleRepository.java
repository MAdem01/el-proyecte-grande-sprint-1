package com.codecool.codekickfc.repository;

import com.codecool.codekickfc.repository.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByType(String type);
}
