package com.example.qsl.user.repository;

import com.example.qsl.interestKeyword.entity.InterestKeyword;
import com.example.qsl.user.entity.SiteUser;
import net.bytebuddy.TypeCache;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 생성")
    void t1() {
        SiteUser user3 = SiteUser.builder()
                .username("user3")
                .email("user3@test.com")
                .password("{noop}1234")
                .build();

        SiteUser user4 = SiteUser.builder()
                .username("user4")
                .email("user4@test.com")
                .password("{noop}1234")
                .build();

        userRepository.saveAll(Arrays.asList(user3, user4));
    }


    @Test
    @DisplayName("1번 회원을 Qsl로 가져오기")
    void t2() {
        SiteUser u1 = userRepository.getQslUser(1L);

        assertThat(u1.getId()).isEqualTo(1L);
        assertThat(u1.getUsername()).isEqualTo("user1");
        assertThat(u1.getEmail()).isEqualTo("user1@test.com");
        assertThat(u1.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("2번 회원을 Qsl로 가져오기")
    void t3() {
        SiteUser u1 = userRepository.getQslUser(2L);

        assertThat(u1.getId()).isEqualTo(2L);
        assertThat(u1.getUsername()).isEqualTo("user2");
        assertThat(u1.getEmail()).isEqualTo("user2@test.com");
        assertThat(u1.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("모든 회원 수")
    void t4() {
        int count = userRepository.getQslCount();

//        assertThat(count).isEqualTo(2);
        assertThat(count).isGreaterThan(0);
    }


    @Test
    @DisplayName("가장 오래된 회원")
    void t5() {
        SiteUser u1 = userRepository.getQslUserOrderByIdAscOne();

        assertThat(u1.getId()).isEqualTo(1L);
        assertThat(u1.getUsername()).isEqualTo("user1");
        assertThat(u1.getEmail()).isEqualTo("user1@test.com");
        assertThat(u1.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("전체회원, 오래된 순")
    void t6() {
        List<SiteUser> users = userRepository.getQslUserOrderByIdAsc();

        SiteUser u1 = users.get(0);
        assertThat(u1.getId()).isEqualTo(1L);
        assertThat(u1.getUsername()).isEqualTo("user1");
        assertThat(u1.getEmail()).isEqualTo("user1@test.com");
        assertThat(u1.getPassword()).isEqualTo("{noop}1234");

        SiteUser u2 = users.get(1);
        assertThat(u2.getId()).isEqualTo(2L);
        assertThat(u2.getUsername()).isEqualTo("user2");
        assertThat(u2.getEmail()).isEqualTo("user2@test.com");
        assertThat(u2.getPassword()).isEqualTo("{noop}1234");

    }

    @Test
    @DisplayName("검색, List 리턴")
    void t7() {
        List<SiteUser> users = userRepository.searchQsl("user1");

        assertThat(users.size()).isEqualTo(1);

        SiteUser u = users.get(0);
        assertThat(u.getId()).isEqualTo(1L);
        assertThat(u.getUsername()).isEqualTo("user1");
        assertThat(u.getEmail()).isEqualTo("user1@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");


        users = userRepository.searchQsl("user2");

        assertThat(users.size()).isEqualTo(1);

        u = users.get(0);
        assertThat(u.getId()).isEqualTo(2L);
        assertThat(u.getUsername()).isEqualTo("user2");
        assertThat(u.getEmail()).isEqualTo("user2@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("검색, Page리턴, id ASC, pageSize=1, page=0")
    void t8() {
        long totalCount = userRepository.count();
        int pageSize = 1;
        int totalPages = (int) Math.ceil(totalCount / (double) pageSize);
        int page = 1;
        String kw = "user";

        ArrayList<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("id"));                // 역순 정렬 -> user2, user1

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));
        Page<SiteUser> usersPage = userRepository.searchQsl(kw, pageable);
        assertThat(usersPage.getTotalPages()).isEqualTo(totalPages);    // 총 페이지 개수
        assertThat(usersPage.getNumber()).isEqualTo(page);              // 페이지 시작 위치
        assertThat(usersPage.getSize()).isEqualTo(pageSize);            // 한 페이지의 요소 개수

        List<SiteUser> users = usersPage.get().toList();        // 한 페이지 가져옴
        assertThat(users.size()).isEqualTo(pageSize);           // 현재 한페이지의 요소 1개

        SiteUser u1 = users.get(0);

        assertThat(u1.getId()).isEqualTo(2L);
        assertThat(u1.getUsername()).isEqualTo("user2");
        assertThat(u1.getEmail()).isEqualTo("user2@test.com");
        assertThat(u1.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("검색, Page 리턴, id DESC, paseSize=1, page=0")
    void t9() {
        long totalCount = userRepository.count();
        int pageSize = 1;
        int totalPages = (int) Math.ceil(totalCount / (double) pageSize);
        int page = 1;
        String kw = "user";

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by(sorts));
        Page<SiteUser> userPage = userRepository.searchQsl(kw, pageable);

        assertThat(userPage.getTotalPages()).isEqualTo(totalPages);
        assertThat(userPage.getNumber()).isEqualTo(page);
        assertThat(userPage.getSize()).isEqualTo(pageSize);

        List<SiteUser> users = userPage.get().toList();
        assertThat(users.size()).isEqualTo(pageSize);

        SiteUser u = users.get(0);
        assertThat(u.getId()).isEqualTo(1L);
        assertThat(u.getUsername()).isEqualTo("user1");
        assertThat(u.getEmail()).isEqualTo("user1@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("검색, Page리턴, id DESC, pageSize=1, page=0")
    void t10() {
        SiteUser u2 = userRepository.getQslUser(2L);

        u2.addInterestKeywordContent("축구");
        u2.addInterestKeywordContent("롤");
        u2.addInterestKeywordContent("헬스");
        u2.addInterestKeywordContent("헬스"); //중복은 무시

        userRepository.save(u2);

        // 엔티티 클래스 : interestKeyword
        // Interest_keyword 테이블블
        // 회원과 키는 다대다 관계

    }

    @Test
    @DisplayName("축구에 관심 있는 회원들 검색")
    void t11() {
        List<SiteUser> users = userRepository.getQslUserByInterestKeyword("축구");

        assertThat(users.size()).isEqualTo(1);

        SiteUser u = users.get(0);

        assertThat(u.getId()).isEqualTo(1L);
        assertThat(u.getUsername()).isEqualTo("user1");
        assertThat(u.getEmail()).isEqualTo("user1@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("no sql 축구에 관심 있는 회원들 검색")
    void t12() {
        List<SiteUser> users = userRepository.findByInterestKeywords_content("축구");

        assertThat(users.size()).isEqualTo(1);

        SiteUser u = users.get(0);

        assertThat(u.getId()).isEqualTo(1L);
        assertThat(u.getUsername()).isEqualTo("user1");
        assertThat(u.getEmail()).isEqualTo("user1@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");
    }
}