package com.example.newsBlock.entity.enumurated;

import com.example.newsBlock.Exception.EnumException;

public enum Category {
    SPORT("Sport"),
    MUSIC("Music"),
    POLITICS("Politics"),
    TECHNOLOGY("Technology");

    private final String label;

    Category(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    public static Category fromLabel(String label) {
        for (Category category : Category.values()) {
            if (category.getLabel().equalsIgnoreCase(label)) {
                return category;
            }
        }
        throw new EnumException("No category with label " + label + " found");
    }




}
