package com.ecommerce.searchservice.product.repository;

import com.ecommerce.searchservice.product.model.document.ProductDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, Long> {

    @Query("""
            {
              "bool": {
                "must": {
                  "multi_match": {
                    "query": "?0",
                    "fields": ["name", "description", "attributes.*"]
                  }
                },
                "filter": {
                  "term": {
                    "is_active": true
                  }
                }
              }
            }
            """)
    Page<ProductDocument> searchByKeyword(String keyword, Pageable pageable);
}
