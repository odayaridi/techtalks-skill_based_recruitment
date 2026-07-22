package org.example.techtalksskillbasedrecruitment.common.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PaginatedResponse<T> {
    private List<T> data;
    private PaginationMeta pagination;
}