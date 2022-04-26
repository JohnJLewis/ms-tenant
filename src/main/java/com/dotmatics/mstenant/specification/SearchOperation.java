package com.dotmatics.mstenant.specification;

public enum SearchOperation {
    EQUAL,
    NOT_EQUAL,
    GREATER_THAN,
    GREATER_THAN_EQUAL,
    LESS_THAN,
    LESS_THAN_EQUAL,
    MATCH,
    MATCH_START,
    MATCH_END,
    IN,
    NOT_IN,
    DATE_MINUS_DAYS,
    DATE_MINUS_MONTHS,
    IS_NULL,
    IS_NOT_NULL
}
