package org.example.e_commerce_api.Config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.e_commerce_api.Entity.Role;
import org.example.e_commerce_api.Entity.Users;
import org.example.e_commerce_api.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmailAndEnabled(username, true)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return buildUserDetails(user);
    }

    private UserDetails buildUserDetails(Users users) {
        Set<GrantedAuthority> authorities = buildAuthorities(users);
        AuthUser authUser = AuthUser.builder()
                .id(users.getId())
                .email(users.getEmail())
                .password(users.getPassword())
                .authorities(authorities)
                .enabled(users.isEnabled())
                .fullName(users.getFullName())
                .build();
        return new CustomUserDetails(authUser);
    }

    private Set<GrantedAuthority> buildAuthorities(Users user) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        if (!CollectionUtils.isEmpty(user.getRoles())) {
            for (Role role : user.getRoles()) {
                //Add role
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                //Add permission
                if (role.getPermissions() != null) {
                    role.getPermissions()
                            .forEach(
                                    permissions -> authorities.add(
                                            new SimpleGrantedAuthority(String.valueOf(permissions.getName()))
                                    )
                            );
                }
            }
        }
        return authorities;
    }
}
