package com.ecommerce.common.util;

import com.ecommerce.common.model.dto.response.PagedResponse;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

import java.util.List;

@UtilityClass
public class PaginationUtil {

    public static <T> PagedResponse<T> buildPagedResponse(Page<?> page, List<T> content) {
        return new PagedResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
