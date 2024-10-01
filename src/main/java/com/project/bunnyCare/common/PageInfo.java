package com.project.bunnyCare.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PageInfo {
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private Long totalElements;

    public PageInfo(int currentPage, int pageSize, Long totalElements) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / pageSize);
    }
}
