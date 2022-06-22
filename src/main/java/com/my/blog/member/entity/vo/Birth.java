package com.my.blog.member.entity.vo;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Birth {

    private LocalDate birth;

    public static Birth from(LocalDate birth){
        return new Birth(birth);
    }
}
