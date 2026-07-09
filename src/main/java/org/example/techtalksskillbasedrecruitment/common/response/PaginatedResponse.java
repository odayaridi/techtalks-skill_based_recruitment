package org.example.techtalksskillbasedrecruitment.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PaginatedResponse<T> {
    private List<T> data;
    private PaginationMeta pagination;
}