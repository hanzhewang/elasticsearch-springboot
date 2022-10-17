package com.has.elastic.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>The elasticsearch page parameter class</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {

    /**
     * page index
     */
    private int pageIndex;

    /**
     * page size
     */
    private int pageSize;

    /**
     * query start number
     *
     * @return
     */
    public int getFrom() {
        int from = pageIndex - 1;
        if (from < 0) {
            from = 0;
        }
        return from * this.pageSize;
    }

}
