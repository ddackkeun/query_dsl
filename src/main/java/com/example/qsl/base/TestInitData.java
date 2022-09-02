package com.example.qsl.base;

import com.example.qsl.user.entity.SiteUser;
import com.example.qsl.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("test")
public class TestInitData {
    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            SiteUser u1 = SiteUser.builder()
                    .username("user1")
                    .email("user1@test.com")
                    .password("{noop}1234")
                    .build();

            SiteUser u2 = SiteUser.builder()
                    .username("user2")
                    .email("user2@test.com")
                    .password("{noop}1234")
                    .build();


            List<SiteUser> siteUsers = userRepository.saveAll(Arrays.asList(u1, u2));
        };
    }
}