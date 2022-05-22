package com.serurityapp.springsecurity.service;

import com.serurityapp.springsecurity.entities.AppRole;
import com.serurityapp.springsecurity.entities.AppUser;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);

    void addRoleToUser(String username, String roleName);
    AppUser loadUserByUserName(String username);
    List<AppUser> listUsers();
};
