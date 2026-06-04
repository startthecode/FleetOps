package org.samtar.warehouse.user.service.imp;

import org.samtar.warehouse.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailImp implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(UserPrincipleImp::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + "Not found "));
    }

}
