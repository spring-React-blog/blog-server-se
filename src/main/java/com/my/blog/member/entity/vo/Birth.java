package com.my.blog.member.entity.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Birth {

    @Column(name="birth")
    private LocalDate birth;

    @JsonValue
    public LocalDate birth() {
        return birth;
    }

    public static Birth from(LocalDate birth){
        return new Birth(birth);
    }
}
