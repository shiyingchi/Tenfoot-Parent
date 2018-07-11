package com.project.dao.system;

import com.project.domain.system.RoleMenuDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 角色与菜单对应关系
 */
@Repository
@Mapper
public interface RoleMenuDao {

	RoleMenuDO get(Long id);

	List<RoleMenuDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(RoleMenuDO roleMenu);

	int update(RoleMenuDO roleMenu);

	int remove(Long id);

	int batchRemove(Long[] ids);

	List<Long> listMenuIdByRoleId(Long roleId);

	int removeByRoleId(Long roleId);
	
	int batchSave(List<RoleMenuDO> list);
}
