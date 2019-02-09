package com.chinatel.caur2cdoauth2.service;

import com.chinatel.caur2cdoauth2.models.Authority;
import com.chinatel.caur2cdoauth2.models.Role;
import com.chinatel.caur2cdoauth2.models.User;
import com.chinatel.caur2cdoauth2.repository.AuthorityRepository;
import com.chinatel.caur2cdoauth2.repository.RoleRepository;
import com.chinatel.caur2cdoauth2.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JPAUserDetailsService implements UserDetailsService {

    private static final Log log = LogFactory.getLog(JPAUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @PostConstruct
    public void init() {
        log.info("WingUserDetailsService init......");
        if (userRepository.findUserByUsername("user") == null) {
            Authority authority = authorityRepository.save(new Authority("USER","/api/**"));
            Role role = roleRepository.save(new Role("USER"));
            User user = userRepository.save(User.builder()
                    .withUsername("user")
                    .withPassword(
                            PasswordEncoderFactories
                                    .createDelegatingPasswordEncoder()
                                    .encode("1"))
            );

            List<Authority> authorties = new ArrayList<>();
            Set<Role> roles = new HashSet<>();
            Set<User> users = new HashSet<>();

            authorties.add(authority);
            roles.add(role);
            users.add(user);

            authority.setRoles(roles);
            role.setAuthorities(authorties);
            role.setUsers(users);
            user.setRoles(roles);

            authorityRepository.save(authority);
            roleRepository.save(role);
            userRepository.save(user);

        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
    }
}
