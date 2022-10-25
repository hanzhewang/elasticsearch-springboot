package com.has.elastic.base.enums.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Relation</p>
 * The geo_shape strategy mapping parameter determines which spatial relation operators may be used at search time.
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/23
 * @since 1.8
 */
@Getter
@AllArgsConstructor
public enum RelationEnum {
    // INTERSECTS - (default) Return all documents whose geo_shape or geo_point field intersects the query geometry.
    intersects,
    // DISJOINT - Return all documents whose geo_shape or geo_point field has nothing in common with the query geometry.
    disjoint,
    // WITHIN - Return all documents whose geo_shape or geo_point field is within the query geometry. Line geometries are not supported.
    within,
    // CONTAINS - Return all documents whose geo_shape or geo_point field contains the query geometry.
    contains,
    ;
}
