package com.project.server.system.impl;

import com.project.dao.system.RoleDao;
import com.project.dao.system.RoleMenuDao;
import com.project.dao.system.UserRoleDao;
import com.project.domain.system.RoleDO;
import com.project.domain.system.RoleMenuDO;
import com.project.server.system.RoleService;
import com.project.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("AlibabaRemoveCommentedCode")
@Service
public class RoleServiceImpl implements RoleService {

	public static final String ROLE_ALL_KEY = "\"role_all\"";


	@Autowired
	RoleDao roleMapper;
	@Autowired
	RoleMenuDao roleMenuMapper;
	@Autowired
	UserRoleDao userRoleMapper;

	@Override
	public List<RoleDO> list() {
		List<RoleDO> roles = roleMapper.list(new HashMap<String,Object>(16));
		return roles;
	}

	@Cacheable(value = Constant.CACHE_NAME, key = ROLE_ALL_KEY)
	@Override
	public List<RoleDO> list(Long userId) {
		List<Long> rolesIds = userRoleMapper.listRoleId(userId);
		List<RoleDO> roles = roleMapper.list(new HashMap<String,Object>(16));
		for (RoleDO roleDO : roles) {
			roleDO.setRoleSign("false");
			for (Long roleId : rolesIds) {
				if (Objects.equals(roleDO.getRoleId(), roleId)) {
					roleDO.setRoleSign("true");
					break;
				}
			}
		}
		return roles;
	}

	@Transactional
	@Override
	public int save(RoleDO role) {
		int count = roleMapper.save(role);
		List<Long> menuIds = role.getMenuIds();
		Long roleId = role.getRoleId();
		List<RoleMenuDO> rms = new ArrayList<>();
		for (Long menuId : menuIds) {
			RoleMenuDO rmDo = new RoleMenuDO();
			rmDo.setRoleId(roleId);
			rmDo.setMenuId(menuId);
			rms.add(rmDo);
		}
		roleMenuMapper.removeByRoleId(roleId);
		if (rms.size() > 0) {
			roleMenuMapper.batchSave(rms);
		}
		return count;
	}

	@CacheEvict(value = Constant.CACHE_NAME, key = ROLE_ALL_KEY)
	@Transactional
	@Override
	public int remove(Long id) {
		int count = roleMapper.remove(id);
		roleMenuMapper.removeByRoleId(id);
		return count;
	}

	@Override
	public RoleDO get(Long id) {
		RoleDO roleDO = roleMapper.get(id);
		return roleDO;
	}

	@CacheEvict(value = Constant.CACHE_NAME, key = ROLE_ALL_KEY)
	@Transactional
	@Override
	public int update(RoleDO role) {
		int r = roleMapper.update(role);
		List<Long> menuIds = role.getMenuIds();
		Long roleId = role.getRoleId();
		roleMenuMapper.removeByRoleId(roleId);
		List<RoleMenuDO> rms = new ArrayList<>();
		for (Long menuId : menuIds) {
			RoleMenuDO rmDo = new RoleMenuDO();
			rmDo.setRoleId(roleId);
			rmDo.setMenuId(menuId);
			rms.add(rmDo);
		}
		 roleMenuMapper.removeByRoleId(roleId);
		if (rms.size() > 0) {
			roleMenuMapper.batchSave(rms);
		}
		return r;
	}

	@Override
	public int batchremove(Long[] ids) {
		int r = roleMapper.batchRemove(ids);
		return r;
	}

}
