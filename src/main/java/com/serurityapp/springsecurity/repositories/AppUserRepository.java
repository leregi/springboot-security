package com.serurityapp.springsecurity.repositories;

import com.serurityapp.springsecurity.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);
}
