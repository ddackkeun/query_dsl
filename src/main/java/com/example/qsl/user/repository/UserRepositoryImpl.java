package com.example.qsl.user.repository;

import com.example.qsl.user.entity.QSiteUser;
import com.example.qsl.user.entity.SiteUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

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

}
