package com.project.domain.product;

import java.io.Serializable;

/**
 * Created by jome on 2017/12/29.
 */
public class ProductCategory implements Serializable {
    private static final long serialVersionUID = 1L;


    private String id;
    private String productId;
    private String categoryId;
    private Long userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
