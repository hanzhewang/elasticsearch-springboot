package com.has.elastic.base.annotation.search.geo;

import java.lang.annotation.*;

/**
 * <p>com.has.elastic.base.annotation.search.geo</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/23
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GeoPolygon {

}

/*
{
  "query": {
    "bool": {
      "must": {
        "match_all": {}
      },
      "filter": {
        "geo_polygon": {
          "person.location": {
            "points": [
              "40, -70",
              "30, -80",
              "20, -90"
            ]
          }
        }
      }
    }
  }
}
 */
