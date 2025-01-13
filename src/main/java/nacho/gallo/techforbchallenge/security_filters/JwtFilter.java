package nacho.gallo.techforbchallenge.security_filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nacho.gallo.techforbchallenge.models.User;
import nacho.gallo.techforbchallenge.services.JwtService;
import nacho.gallo.techforbchallenge.services.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtFilter extends OncePerRequestFilter {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserService userService;

  @Autowired
  public JwtFilter(@Lazy AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String authorizationHeader = request.getHeader("Authorization");
    if (Objects.isNull(authorizationHeader)
        || !authorizationHeader.startsWith("Bearer ")
        || Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())) {
      filterChain.doFilter(request, response); //TODO: NO REPETIR ESTO
      return;
    }

    String jwtToken = authorizationHeader.substring(7);
    String username = jwtService.extractUsername(jwtToken);

    if(Strings.isEmpty(username)) {
      filterChain.doFilter(request, response);
      return;
    }

    User user = userService.findByEmail(username);

    if (!jwtService.validateToken(jwtToken, user)) {
      filterChain.doFilter(request, response);
      return;
    }

    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authToken);

    filterChain.doFilter(request, response);
  }
}
