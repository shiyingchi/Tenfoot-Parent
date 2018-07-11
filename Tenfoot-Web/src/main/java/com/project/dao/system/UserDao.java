package com.project.dao.system;

import com.project.domain.system.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 */
@Repository
@Mapper
public interface UserDao {


	UserDO get(Long userId);

	List<UserDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(UserDO user);

	int update(UserDO user);

	int remove(Long userId);

	int batchRemove(Long[] userIds);

	Long[] listAllDept();

	int uploadHeadImg(Map<String, Object> map);

}
