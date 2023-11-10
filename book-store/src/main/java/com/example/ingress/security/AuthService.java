package com.example.ingress.security;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface AuthService {
    Optional<Authentication> getAuthentication(HttpServletRequest httpServletRequest);
}
