package com.dotmatics.mstenant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

public abstract class BaseDto {
    public BaseDtoResult apiResult;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long id;
    @JsonIgnore
    public String idColumns;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BaseDtoResult {
        public boolean success = true;
        public String username;
        public String message;
        public String action;
        public String entity;
        public String id;
        public Long count;
        public Date datetime = new Date();
        public List<BaseDtoError> errors;

        public BaseDtoResult() {
        }

        public BaseDtoResult(boolean success, List<BaseDtoError> errors) {
            this.success = success;
            this.errors = errors;
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class BaseDtoError {
            public String column;
            public String type;
            public String url;
            public String code;
            public String error;
            public Object[] args;

            public BaseDtoError(String column, String error, String code) {
                this.column = column;
                this.error = error;
                this.code = code;
            }

            public BaseDtoError(String column, String error, String code, Object[] args) {
                this.column = column;
                this.error = error;
                this.code = code;
                this.args = args;
            }

            public BaseDtoError() {
            }
        }
    }
}
