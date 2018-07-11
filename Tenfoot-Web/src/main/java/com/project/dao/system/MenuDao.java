package com.project.dao.system;


import com.project.domain.system.MenuDO;
import com.project.domain.system.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 */
@Repository
@Mapper
public interface MenuDao {

	MenuDO get(Long menuId);

	List<MenuDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(MenuDO menu);

	int update(MenuDO menu);

	int remove(Long menuId);

	int batchRemove(Long[] menuIds);

	List<MenuDO> listMenuByUserId(Long userId);

	List<MenuDO> listMenuByDeptId(Long deptId);

	List<String> listUserPerms(UserDO user);

	List<String> listUserDeptPerms(UserDO user);
}
