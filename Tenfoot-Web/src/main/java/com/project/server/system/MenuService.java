package com.project.server.system;

import com.project.domain.commom.Tree;
import com.project.domain.system.MenuDO;
import com.project.domain.system.UserDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface MenuService {
	Tree<MenuDO> getSysMenuTree(UserDO user);

	List<Tree<MenuDO>> listMenuTree(UserDO user);

	Tree<MenuDO> getTree();

	Tree<MenuDO> getTree(Long id);

	Tree<MenuDO> getTreeDept(Long id);

	List<MenuDO> list();

	int remove(Long id);

	int save(MenuDO menu);

	int update(MenuDO menu);

	MenuDO get(Long id);

	Set<String> listPerms(UserDO user);
}
