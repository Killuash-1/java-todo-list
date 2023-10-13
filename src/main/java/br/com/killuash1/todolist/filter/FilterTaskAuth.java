package br.com.killuash1.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.killuash1.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks/")){
            var auth = request.getHeader("Authorization");

            var authEncoded = auth.substring("Basic".length()).trim();
            String authDecoded = new String(Base64.getDecoder().decode(authEncoded));

            var separator = authDecoded.indexOf(":"); // pega a posição da vírgula

            var username = authDecoded.substring(0, separator); // pega o usuário
            var password = authDecoded.substring(separator + 1); // pega a senha

            var user = this.userRepository.findByUsername(username);

            if(user != null) {
                var passVerifier = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                if(passVerifier.verified) {
                    request.setAttribute("userId", user.getId());
                  filterChain.doFilter(request, response);
                }else  response.sendError(401);

            }else  response.sendError(401);




        }else filterChain.doFilter(request, response);
    }
}
