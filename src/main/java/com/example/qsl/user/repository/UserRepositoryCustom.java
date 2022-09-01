package com.example.qsl.user.repository;

import com.example.qsl.user.entity.SiteUser;

import java.util.List;

public interface UserRepositoryCustom {
    SiteUser getQslUser(Long id);

    int getQslCount();

    SiteUser getQslUserOrderByIdAscOne();

    List<SiteUser> getQslUserOrderByIdAsc();

    List<SiteUser> searchQsl(String kw);
}
