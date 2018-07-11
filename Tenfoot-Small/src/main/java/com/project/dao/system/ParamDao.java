package com.project.dao.system;


import com.project.domain.system.ParamDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 参数表
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-05 16:13:19
 */
@Repository
@Mapper
public interface ParamDao {

	List<ParamDO> list();

}
