package com.has.elastic.base.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>标点符号</p>
 *
 * @author wanghanzhe
 * @version 1.0.1
 * @date 2019.4.26
 * @since jdk1.8 and elasticsearch 6.3
 */
@AllArgsConstructor
public enum SymbolType {

    /**
     *
     */
    COMMA(",", "逗号分隔符"),
    POINT(".", "点分隔符"),
    COLON(":", "冒号分隔符"),
    UNDERLINE("_", "下划线分隔符");

    /**
     * 符号
     */
    @Getter
    private String symbol;
    /**
     * 描述
     */
    @Getter
    private String desc;

    /**
     * value of SymbolType
     *
     * @param symbol
     * @return
     */
    public static SymbolType valueof(String symbol) {
        SymbolType[] symbolTypes = SymbolType.values();
        for (SymbolType symbolType : symbolTypes) {
            if (symbolType.getSymbol().equals(symbol)) {
                return symbolType;
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
        return this.symbol;
    }
}
