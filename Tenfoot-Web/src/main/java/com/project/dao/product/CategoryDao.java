package com.project.dao.product;


import com.project.domain.product.CategoryDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author jome
 * @email 925259117@qq.com
 * @date 2017-12-26 18:18:09
 */
@Repository
@Mapper
public interface CategoryDao {

    CategoryDO get(String id);

    List<CategoryDO> list(Map<String, Object> map);

    //商品分类
    List<CategoryDO> categoryList(@Param(value="userIdCreate") Long userIdCreate);

    int count(Map<String, Object> map);

    int save(CategoryDO category);

    int update(CategoryDO category);

    int remove(String id);

    int batchRemove(String[] ids);
}
