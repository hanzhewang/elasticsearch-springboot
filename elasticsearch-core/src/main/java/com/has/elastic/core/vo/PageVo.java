package com.has.elastic.core.vo;

import lombok.Data;

import java.util.List;

/**
 * <p>com.has.elastic.core.vo</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/12
 */
@Data
public class PageVo<T> {

    /**
     * page index
     */
    private int pageIndex;

    /**
     * page size
     */
    private int pageSize;

    private List<T> c;
}
