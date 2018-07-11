package com.project.dao.system;

import com.project.domain.system.DeptMenuDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门与菜单对应关系
 */
@Repository
@Mapper
public interface DeptMenuDao {

	int removeByDeptId(Long roleId);

	int batchSave(List<DeptMenuDO> list);

	List<Long> listMenuIdByDeptId(Long deptId);
	
}
