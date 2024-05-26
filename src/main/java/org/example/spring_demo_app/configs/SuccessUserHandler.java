package org.example.spring_demo_app.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.spring_demo_app.models.User;
import org.example.spring_demo_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    private final UserService userService;
    @Autowired
    public SuccessUserHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")){
            httpServletResponse.sendRedirect("/admin");
        }
        else if (roles.contains("ROLE_USER")) {
            // Получаем имя пользователя из authentication.getPrincipal()
            org.springframework.security.core.userdetails.User userDetails =
                    (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Используем сервис для получения пользователя по имени пользователя
            User user = userService.findByUsername(username).orElseThrow();

            long userId = user.getId();
            httpServletResponse.sendRedirect("/user?id=" + userId);
        }
        else {
            httpServletResponse.sendRedirect("/");
        }
    }
}
