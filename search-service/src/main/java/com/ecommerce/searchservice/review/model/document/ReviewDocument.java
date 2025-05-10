package com.ecommerce.searchservice.review.model.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "reviews")
public class ReviewDocument {

    @Id
    private String reviewId;

    @Field(type = FieldType.Long, name = "product_id")
    private Long productId;

    @Field(type = FieldType.Long, name = "user_id")
    private Long userId;

    @Field(type = FieldType.Integer, name = "rating")
    private Integer rating;

    @Field(type = FieldType.Text, name = "comment")
    private String comment;
}