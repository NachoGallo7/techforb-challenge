package nacho.gallo.techforbchallenge.configs;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nacho.gallo.techforbchallenge.entities.RevokedTokenEntity;
import nacho.gallo.techforbchallenge.entities.UserEntity;
import nacho.gallo.techforbchallenge.models.RevokedToken;
import nacho.gallo.techforbchallenge.models.User;
import nacho.gallo.techforbchallenge.services.JwtService;
import nacho.gallo.techforbchallenge.services.RevokedTokensService;
import nacho.gallo.techforbchallenge.services.UserService;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Objects;

@Component
public class LogoutHandlerImpl implements LogoutHandler {

  private final RevokedTokensService revokedTokensService;
  private final JwtService jwtService;
  private final UserService userService;
  private final ModelMapper modelMapper;

  @Autowired
  public LogoutHandlerImpl(RevokedTokensService revokedTokensService, JwtService jwtService, UserService userService, ModelMapper modelMapper) {
    this.revokedTokensService = revokedTokensService;
    this.jwtService = jwtService;
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    String authorizationHeader = request.getHeader("Authorization");
    if(Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
      return;
    }

    String jwtToken = authorizationHeader.substring(7);
    Claims claims = jwtService.getTokenClaims(jwtToken);

    if(Strings.isEmpty(claims.getSubject())) {
      return;
    }

    User user = userService.findByEmail(claims.getSubject());

    RevokedToken revokedToken = RevokedToken.builder()
        .token(jwtToken)
        .canDeleteAfterDate(claims.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
        .userId(user.getId())
        .build();
    revokedTokensService.revoke(revokedToken);

  }
}
