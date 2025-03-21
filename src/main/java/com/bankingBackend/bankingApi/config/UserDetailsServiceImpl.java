package com.bankingBackend.bankingApi.config;

import com.bankingBackend.bankingApi.entities.User;
import com.bankingBackend.bankingApi.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);

        if(user == null) throw new UsernameNotFoundException("This User does not exist");

        return org.springframework.security.core.userdetails.User.builder().username(user.getEmail()).password(user.getPassword()).roles(user.getRole()).build();
    }
}
