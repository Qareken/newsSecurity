package com.example.newsBlock.restController;

import com.example.newsBlock.mapper.NewsCategoryMapper;
import com.example.newsBlock.service.impl.NewsCategoryServiceImpl;
import com.example.newsBlock.web.model.NewsCategoryDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class NewsCategoryController {
    private final NewsCategoryServiceImpl categoryService;
    private final   NewsCategoryMapper categoryMapper;



    @PostMapping("/create")
    ResponseEntity<NewsCategoryDTO> createCategory(@Valid @RequestBody NewsCategoryDTO categoryDTO){
        var temp = categoryService.save(categoryMapper.toEntity(categoryDTO));

        return ResponseEntity.ok(categoryMapper.toDto(temp));
    }
}
