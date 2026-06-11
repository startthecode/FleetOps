package org.samtar.warehouse.products.dto.res;

import java.util.List;

public record ProductPaginatedResDto(List<ProductResDto> products,
 long totalCount,
        int currentPage,
        int pageSize,
        long skip) {


}
