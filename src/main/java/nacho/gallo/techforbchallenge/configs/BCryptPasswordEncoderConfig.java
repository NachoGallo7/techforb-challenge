package nacho.gallo.techforbchallenge.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BCryptPasswordEncoderConfig {

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    // I USED A SMALL STRENGTH VALUE TO NOT OVERLOAD MY FREE HOSTING SITE. 10-12 WOULD BE A GOOD STRENGTH.
    return new BCryptPasswordEncoder(4);
  }
}
