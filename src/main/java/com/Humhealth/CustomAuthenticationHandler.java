package com.Humhealth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse; //Have no idea why these imports wouldn't let me use the shortened names
import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response, Authentication authentication)
            throws IOException, jakarta.servlet.ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/dashboard");
        } else if (roles.contains("ROLE_TEACHER")) {
            response.sendRedirect("/teacher/dashboard");
        } else {
            // If we have neither an admin or a teacher we have to redirect to login and try to get one of these types of users again
            response.sendRedirect("/login");
        }
    }
}
