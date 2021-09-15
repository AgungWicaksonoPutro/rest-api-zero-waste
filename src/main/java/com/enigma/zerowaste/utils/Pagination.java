package com.enigma.zerowaste.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class Pagination<T> {
    private List<T> content;
    private Long count;
    private Integer totalPages;
    private Integer page;
    private Integer size;

    public Pagination(Page<T> page){
        this.content = page.getContent();
        this.count = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.page = page.getNumber();
        this.size = page.getSize();
    }
}
