package org.example.spring_demo_app.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.spring_demo_app.models.User;
import org.example.spring_demo_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
public class UserAccessFilter extends OncePerRequestFilter {

    private final UserService userService;
    @Autowired
    public UserAccessFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/user")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    UserDetails userDetails = (UserDetails) principal;
                    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

                    // Проверяем, что у пользователя только одна роль и эта роль - USER
                    if (authorities.size() == 1 && authorities.stream()
                            .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"))) {

                        String username = userDetails.getUsername();
                        User user = userService.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                        String userIdParam = request.getParameter("id");
                        if (userIdParam != null && !userIdParam.equals(String.valueOf(user.getId()))) {
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                            return;
                        }
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
