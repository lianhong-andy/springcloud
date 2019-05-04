package com.andy.consumer.pojo;

public interface BizConstant {
    public interface RespCode{
        public static final Integer SUCCESS = 0;
        public static final Integer FAIL = -1;
    }

    public interface RespMsg{
        public static final String SUCCESS = "SUCCESS";
        public static final String FAIL = "FAIL";
    }
}
