package com.project.dao.system;

import com.project.domain.system.RoleDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 角色
 */
@Repository
@Mapper
public interface RoleDao {

	RoleDO get(Long roleId);

	List<RoleDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(RoleDO role);

	int update(RoleDO role);

	int remove(Long roleId);

	int batchRemove(Long[] roleIds);
}
