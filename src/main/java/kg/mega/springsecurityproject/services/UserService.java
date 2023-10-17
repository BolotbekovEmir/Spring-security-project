package kg.mega.springsecurityproject.services;

import kg.mega.springsecurityproject.entities.Role;
import kg.mega.springsecurityproject.entities.User;
import kg.mega.springsecurityproject.repositories.RoleRepo;
import kg.mega.springsecurityproject.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void createNewUser(User user) {
        Role role = roleRepo.findByName("ROLE_USER").orElseThrow();
        user.setRoles(List.of(role));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("The user with the name '%s' was not found.", username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }


}
