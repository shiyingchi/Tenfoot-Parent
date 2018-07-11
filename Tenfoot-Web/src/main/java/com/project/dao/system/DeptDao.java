package com.project.dao.system;

import com.project.domain.system.DeptDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 */
@Repository
@Mapper
public interface DeptDao {

	DeptDO get(Long deptId);

	List<DeptDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(DeptDO dept);

	int update(DeptDO dept);

	int remove(Long deptId);

	int batchRemove(Long[] deptIds);

	Long[] listParentDept();
}
