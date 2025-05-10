package com.ecommerce.common.model.event.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDeletedEvent {
    private String reviewId;
}
