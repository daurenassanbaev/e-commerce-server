package com.ecommerce.searchservice.review.repository;

import com.ecommerce.searchservice.review.model.document.ReviewDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewSearchRepository extends ElasticsearchRepository<ReviewDocument, String> {

    @Query("""
            {
              "bool": {
                "must": [
                  {
                    "multi_match": {
                      "query": "?0",
                      "type": "bool_prefix",
                      "fields": [
                        "comment",
                        "comment._2gram",
                        "comment._3gram"
                      ]
                    }
                  }
                ]
              }
            }
            """)
    Page<ReviewDocument> searchByKeyword(String keyword, Pageable pageable);

}
