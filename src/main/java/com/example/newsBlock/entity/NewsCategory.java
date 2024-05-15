package com.example.newsBlock.entity;

import com.example.newsBlock.entity.enumurated.Category;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldNameConstants
public class NewsCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private Category category;
    @ManyToMany(mappedBy = "categories")
    @ToString.Exclude
    @Builder.Default
    private List<News> news =new ArrayList<>();

    @Override
    public String toString() {

        return "NewsCategory{" +
                "id=" + id +
                ", category=" + category +
                '}';

    }
}
