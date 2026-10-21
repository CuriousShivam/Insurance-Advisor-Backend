package com.insurance.advisor.backend.service;

import com.insurance.advisor.backend.entity.AdminUser;
import com.insurance.advisor.backend.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("UserDetailsService LoadUserBYUsername");
        Optional<AdminUser> user = userRepo.findByEmail(email);
        if(user.isPresent()){
            AdminUser userResp = user.get();
            System.out.println("LoadUserByUserName User: " + user);
            return new org.springframework.security.core.userdetails.User(
                    email,
                    userResp.getPasswordHash(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userResp.getRole()))
            );
        } else{
            throw new UsernameNotFoundException("User not found....");
        }
    }
}
