package com.my.blog.category.entity;


import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="category")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id" , unique = true, nullable = false)
    private Long id;

    @Column(name="category_name")
    private String name;
    @JsonValue
    public String name() {
        return name;
    }



}
