package com.tacocloud.authorizationserver;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Chapter08SpringAuthorizationServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(Chapter08SpringAuthorizationServerApplication.class, args);
  }

  @Bean
  public ApplicationRunner dataLoader(UserRepository userRepo, PasswordEncoder passwordEncoder) {
    return args -> {
      userRepo.save(new User("habuma", passwordEncoder.encode("password"), "ROLE_ADMIN" ));
      userRepo.save(new User("tacochef", passwordEncoder.encode("password"), "ROLE_ADMIN"));
    };
  }

}
