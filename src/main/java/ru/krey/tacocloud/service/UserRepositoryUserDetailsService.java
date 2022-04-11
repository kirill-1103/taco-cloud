package ru.krey.tacocloud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.krey.tacocloud.model.User;
import ru.krey.tacocloud.repo.UserRepository;

@Slf4j
@Service
public class UserRepositoryUserDetailsService
        implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;

    UserRepositoryUserDetailsService(UserRepository repo){
        this.userRepository = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null){
            return user;
        }else{
            throw new UsernameNotFoundException(
                    "User '" + username + "' not found"
            );
        }
    }
}
