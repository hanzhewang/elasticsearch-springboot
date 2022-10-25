package com.has.elastic.base.annotation.search.geo;

import com.has.elastic.base.enums.search.RelationEnum;

import java.lang.annotation.*;

/**
 * <p>Geo shape query</p>
 * Requires the geo_shape mapping or the geo_point mapping.
 * <p>
 * The geo_shape query uses the same grid square representation as the geo_shape mapping to find documents that have a shape that intersects with the query shape.
 * It will also use the same Prefix Tree configuration as defined for the field mapping.
 * <p>
 * The query supports two ways of defining the query shape, either by providing a whole shape definition,
 * or by referencing the name of a shape pre-indexed in another index.
 * Both formats are defined below with examples.
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/23
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GeoShape {

    /**
     * The geo_shape strategy mapping parameter determines which spatial relation operators may be used at search time.
     * <p>
     * The following is a complete list of spatial relation operators available when searching a geo field:
     * <p>
     * INTERSECTS - (default) Return all documents whose geo_shape or geo_point field intersects the query geometry.
     * DISJOINT - Return all documents whose geo_shape or geo_point field has nothing in common with the query geometry.
     * WITHIN - Return all documents whose geo_shape or geo_point field is within the query geometry. Line geometries are not supported.
     * CONTAINS - Return all documents whose geo_shape or geo_point field contains the query geometry.
     *
     * @return
     */
    RelationEnum relation() default RelationEnum.intersects;
}

/*
{
  "query": {
    "bool": {
      "must": {
        "match_all": {}
      },
      "filter": {
        "geo_shape": {
          "location": {
            "shape": {
              "type": "envelope",
              "coordinates": [ [ 13.0, 53.0 ], [ 14.0, 52.0 ] ]
            },
            "relation": "within"
          }
        }
      }
    }
  }
}
 */
