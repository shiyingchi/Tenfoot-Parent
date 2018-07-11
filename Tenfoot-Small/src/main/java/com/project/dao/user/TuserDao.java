package com.project.dao.user;


import com.project.domain.user.TuserDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 会员表
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-05 17:56:44
 */
@Mapper
@Repository
public interface TuserDao {

	TuserDO get(String openId);
	
	int save(TuserDO user);
	
	int update(TuserDO user);
	
}
