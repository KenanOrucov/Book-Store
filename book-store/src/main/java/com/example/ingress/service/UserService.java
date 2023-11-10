package com.example.ingress.service;

import com.example.ingress.domain.AuthorityEntity;
import com.example.ingress.domain.UserAuthority;
import com.example.ingress.domain.UserEntity;
import com.example.ingress.mapper.ManualIngressMapping;
import com.example.ingress.repository.AuthorityRepository;
import com.example.ingress.repository.UserRepository;
import com.example.ingress.security.JwtAuthService;
import com.example.ingress.service.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final StudentService studentService;
    private final AuthorService authorService;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final ManualIngressMapping manualIngressMapping;
    private final JwtAuthService jwtAuthService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       return userRepository.findUserEntityByEmail(email);
    }

    @Transactional
    public String saveUser(UserDto userDto, String role){
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
        log.info("authenticationToken: {}", authenticationToken);
        String token = jwtAuthService.issueToken(authenticationToken, Duration.ofDays(1), role);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity user = manualIngressMapping.convertToUser(userDto);
        user.setRole(createAuthorities(role));
        createRelatedEntity(userDto, role);
        userRepository.save(user);
        return token;
    }

    private void createRelatedEntity(UserDto userDto, String role){
        if (role.equalsIgnoreCase(UserAuthority.STUDENT)){
            studentService.createStudent(userDto);
        } else if (role.equalsIgnoreCase(UserAuthority.AUTHOR)) {
            authorService.createAuthor(userDto);
        }
    }

    private Set<AuthorityEntity> createAuthorities(String authority) {
        Set<AuthorityEntity> authorities = new HashSet<>();
        authorityRepository.save(new AuthorityEntity(1L, UserAuthority.STUDENT));
        authorityRepository.save(new AuthorityEntity(2L, UserAuthority.AUTHOR));
        AuthorityEntity authorityEntity = authorityRepository
                .findAll()
                .stream()
                .filter(t -> t.getAuthority().equals(authority))
                .findFirst()
                .orElseGet(() -> AuthorityEntity.builder().authority(authority).build());
         authorities.add(authorityEntity);
         return authorities;
    }
}
