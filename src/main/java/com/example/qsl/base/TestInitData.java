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

            SiteUser u3 = SiteUser.builder()
                    .username("user3")
                    .email("user3@test.com")
                    .password("{noop}1234")
                    .build();

            SiteUser u4 = SiteUser.builder()
                    .username("user4")
                    .email("user4@test.com")
                    .password("{noop}1234")
                    .build();

            SiteUser u5 = SiteUser.builder()
                    .username("user5")
                    .email("user5@test.com")
                    .password("{noop}1234")
                    .build();

            SiteUser u6 = SiteUser.builder()
                    .username("user6")
                    .email("user6@test.com")
                    .password("{noop}1234")
                    .build();

            SiteUser u7 = SiteUser.builder()
                    .username("user7")
                    .email("user7@test.com")
                    .password("{noop}1234")
                    .build();

            SiteUser u8 = SiteUser.builder()
                    .username("user8")
                    .email("user8@test.com")
                    .password("{noop}1234")
                    .build();

            u1.addInterestKeywordContent("??????");
            u1.addInterestKeywordContent("??????");

            u2.addInterestKeywordContent("??????");
            u2.addInterestKeywordContent("????????????");
            u2.addInterestKeywordContent("?????????");

            // 8?????? following ?????? ??????
            u3.addInterestKeywordContent("?????????");
            u4.addInterestKeywordContent("????????????");
            u5.addInterestKeywordContent("?????????");
            u6.addInterestKeywordContent("??????");
            u6.addInterestKeywordContent("?????????");
            u7.addInterestKeywordContent("EDM");
            // 8?????? ????????? ?????? ????????? ????????? ????????? ?????? : ?????????, ????????????, ?????????, ??????, EDM

            // ????????? ?????? ??????
            userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5, u6, u7, u8));

            u8.follow(u7);  // ????????? ????????? ???????????? ??????
            u8.follow(u7);
            u8.follow(u6);
            u8.follow(u5);
            u8.follow(u4);
            u8.follow(u3);

            u7.follow(u6);
            u7.follow(u5);
            u7.follow(u4);
            u7.follow(u3);

            // ????????? follw ????????? ????????????.
            userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5, u6, u7, u8));
        };
    }
}
