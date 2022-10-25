package com.has.elastic.core.paser;

import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.ScoreSort;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import com.has.elastic.base.enums.search.SortRule;
import com.has.elastic.core.bo.ElasticDocumentBo;
import com.has.elastic.core.bo.ElasticSortBo;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>The elasticsearch sort annotation parser</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2018.7.9
 * @since JDK1.8 and Elasticsearch 6.3
 */
public class QuerySortParser {

    public <T extends Serializable> List<SortOptions> parser(ElasticDocumentBo bo, T target) {
        List<ElasticSortBo> sorts = bo.getSorts();
        if (CollectionUtils.isEmpty(sorts)) {
            return null;
        }
        return sorts.stream().sorted(Comparator.comparing(sort -> sort.getSort())).map(sort -> {
            SortOptions.Builder builder = new SortOptions.Builder();
            // 排序属性
            FieldSort.Builder fieldSortBuilder = new FieldSort.Builder();
            fieldSortBuilder.field(sort.getElasticName());
            builder.field(fieldSortBuilder.build());
            // 排序规则
            ScoreSort.Builder sortBuilder = new ScoreSort.Builder();
            if (SortRule.DESC.equals(sort.getSortRule())) {
                sortBuilder.order(SortOrder.Desc);
            } else {
                sortBuilder.order(SortOrder.Asc);
            }
            builder.score(sortBuilder.build());
            return builder.build();
        }).collect(Collectors.toList());
    }
}