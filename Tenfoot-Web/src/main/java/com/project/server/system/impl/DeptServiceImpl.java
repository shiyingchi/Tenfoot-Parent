package com.project.server.system.impl;

import com.project.dao.system.DeptDao;
import com.project.dao.system.DeptMenuDao;
import com.project.domain.commom.BuildTree;
import com.project.domain.commom.Tree;
import com.project.domain.system.DeptDO;
import com.project.domain.system.DeptMenuDO;
import com.project.server.system.DeptService;
import com.project.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptDao sysDeptMapper;
	@Autowired
	private DeptMenuDao deptMenuMapper;

	@Override
	public DeptDO get(Long deptId){
		return sysDeptMapper.get(deptId);
	}

	@Override
	public List<DeptDO> list(Map<String, Object> map){
		return sysDeptMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map){
		return sysDeptMapper.count(map);
	}

	@Override
	public int save(DeptDO sysDept){
		sysDept.setUserIdCreate(ShiroUtils.getUserId());
		int count = sysDeptMapper.save(sysDept);
		List<Long> menuIds = sysDept.getMenuIds();
		Long deptId = sysDept.getDeptId();
		List<DeptMenuDO> dms = new ArrayList<>();
		for (Long menuId : menuIds) {
			DeptMenuDO rmDo = new DeptMenuDO();
			rmDo.setDeptId(deptId);
			rmDo.setMenuId(menuId);
			dms.add(rmDo);
		}
		deptMenuMapper.removeByDeptId(deptId);
		if (dms.size() > 0) {
			deptMenuMapper.batchSave(dms);
		}
		return count;
	}

	@Override
	public int update(DeptDO sysDept){
		int count = sysDeptMapper.update(sysDept);
		List<Long> menuIds = sysDept.getMenuIds();
		Long deptId = sysDept.getDeptId();
		List<DeptMenuDO> dms = new ArrayList<>();
		for (Long menuId : menuIds) {
			DeptMenuDO rmDo = new DeptMenuDO();
			rmDo.setDeptId(deptId);
			rmDo.setMenuId(menuId);
			dms.add(rmDo);
		}
		deptMenuMapper.removeByDeptId(deptId);
		if (dms.size() > 0) {
			deptMenuMapper.batchSave(dms);
		}

		return count;
	}

	@Override
	public int remove(Long deptId){
		return sysDeptMapper.remove(deptId);
	}

	@Override
	public int batchRemove(Long[] deptIds){
		return sysDeptMapper.batchRemove(deptIds);
	}

	@Override
	public Tree<DeptDO> getTree() {
		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
		List<DeptDO> sysDepts = sysDeptMapper.list(new HashMap<String,Object>(16));
		for (DeptDO sysDept : sysDepts) {
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(sysDept.getDeptId().toString());
			tree.setParentId(sysDept.getParentId().toString());
			tree.setText(sysDept.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<DeptDO> t = BuildTree.build(trees);
		return t;
	}

}
