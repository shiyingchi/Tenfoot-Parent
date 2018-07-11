package com.project.server.product;

import java.util.Map;

public interface ProductService {

	Map<String,Object> findShopProduct(Long userId);

	Map<String,Object> findProductSpec(String productId);
}
