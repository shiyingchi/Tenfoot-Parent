package com.project.domain.system;

public class DeptMenuDO {
    private Long id;
    private Long deptId;
    private Long menuId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "RoleMenuDO{" +
                "id=" + id +
                ", deptId=" + deptId +
                ", menuId=" + menuId +
                '}';
    }
}
