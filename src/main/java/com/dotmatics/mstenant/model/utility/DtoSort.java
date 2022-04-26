package com.dotmatics.mstenant.model.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.domain.Sort;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoSort {
    public String direction;
    public String column;
    public Boolean ignoreCase;
    public String nullHandling;
    public Boolean ascending;
    public Boolean descending;

    public DtoSort() {
    }

    public DtoSort(Sort.Order order) {
        direction = order.getDirection().name();
        column = order.getProperty();
        ignoreCase = order.isIgnoreCase();
    }
}
