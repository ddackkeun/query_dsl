package com.example.qsl.user.repository;

import com.example.qsl.user.entity.QSiteUser;
import com.example.qsl.user.entity.SiteUser;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.function.LongSupplier;

import static com.example.qsl.user.entity.QSiteUser.siteUser;


@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public SiteUser getQslUser(Long id) {
        /*
         * SELECT *
         * FROM site_user
         * WHERE id = 1
         * */

        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(siteUser.id.eq(id))
                .fetchOne();
    }

    @Override
    public int getQslCount() {
        /*
        * SELECT COUNT(SITE_USER)
        * FROM SITE_USER
        * */
        long count = jpaQueryFactory
                .select(siteUser.count())
                .from(siteUser)
                .fetchOne();

        return (int) count;
    }

    @Override
    public SiteUser getQslUserOrderByIdAscOne() {
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .orderBy(siteUser.id.asc())
                .fetchFirst();
    }

    @Override
    public List<SiteUser> getQslUserOrderByIdAsc() {
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .orderBy(siteUser.id.asc())
                .fetch();
    }

    @Override
    public List<SiteUser> searchQsl(String kw) {
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(
                        siteUser.username.contains(kw)
                                .or(siteUser.email.contains(kw))
                )
                .orderBy(siteUser.id.desc())
                .fetch();
    }

    @Override
    public Page<SiteUser> searchQsl(String kw, Pageable pageable) {
        List<SiteUser> users = jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(
                        siteUser.username.contains(kw)
                                .or(siteUser.email.contains(kw))
                )
                .offset(pageable.getOffset())       // 몇개를 건너띄어야 하는지
                .limit(pageable.getPageSize())      // 한페이지 보이는 개수
                .orderBy(siteUser.id.asc())         // 아이디로 역순 정렬
                .fetch();

        LongSupplier totalSupplier = () -> 2;

        return PageableExecutionUtils.getPage(users, pageable, totalSupplier);
    }
}
