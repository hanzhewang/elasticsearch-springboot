package com.has.elastic.base.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>异常类型</p>
 *
 * @author wanghanzhe
 * @version 1.0.1
 * @date 2019.4.28
 */
@AllArgsConstructor
public enum ErrorType {
    UNKNOWN(1000, "未知异常"),
    CLIENT_CREATE(1001, "创建客户端异常"),
    BUSINESS(1002, "业务异常"),
    INSERT_UPDATE_DOCUMENT(1003, "插入更新数据异常"),
    JSON_PARSER(1010, "解析JSON异常"),
    INDEX_IS_NULL(1020, "索引不能为空"),
    TYPE_IS_NULL(1021, "类型不能为空"),
    UUID_IS_NULL(1022, "主键不能为空"),
    CONTENT_IS_NULL(1023, "文档不能为空"),
    NESTED_NOT_DELETE(1024, "嵌套对象不允许删除文档"),
    JOIN_PARENT_ID_IS_NULL(1024, "父子结构外键不能为空"),
    JOIN_PATH_IS_NULL(1025, "父子结构路径不能为空"),
    JOIN_NAME_IS_NULL(1026, "父子结构名称不能为空"),
    JOIN_TYPE(1027, "父子结构类型异常"),
    ;
    /**
     * 错误编码
     */
    @Getter
    private Integer code;

    /**
     * 错误描述
     */
    @Getter
    private String message;

    /**
     * value of ErrorType
     *
     * @param code
     * @return
     */
    public static ErrorType valueof(Integer code) {
        ErrorType[] errorTypes = ErrorType.values();
        for (ErrorType errorType : errorTypes) {
            if (errorType.getCode().equals(code)) {
                return errorType;
            }
        }
        return null;
    }

    /**
     * to string
     *
     * @return
     */
    @Override
    public String toString() {
        return String.valueOf(this.code);
    }
}
