package com.example.qsl.interestKeyword.entity;


import com.example.qsl.user.entity.SiteUser;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(InterestKeywordId.class)
public class InterestKeyword {

    @Id
    @ManyToOne
    @EqualsAndHashCode.Include
    private SiteUser user;

    @Id
    @EqualsAndHashCode.Include
    private String content;
}
