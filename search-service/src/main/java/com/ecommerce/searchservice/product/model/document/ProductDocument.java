package com.ecommerce.searchservice.product.model.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Document(indexName = "products")
public class ProductDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Field(type = FieldType.Text, name = "description")
    private String description;

    @Field(type = FieldType.Double, name = "price")
    private BigDecimal price;

    @Field(type = FieldType.Long, name = "category_id")
    private Long categoryId;

    @Field(type = FieldType.Object, name = "attributes")
    private Map<String, Object> attributes;

    @Field(type = FieldType.Boolean, name = "is_active")
    private boolean isActive;
}
