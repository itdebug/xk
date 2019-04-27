package com.lrk.xk.xk.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author lrk
 * @date 2019/4/27 上午9:13
 */
@Entity
public class Clazz {
    private int id;
    private String name;
    private int deptId;

    @Id
    @Column(name = "id",
        nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name",
        nullable = false,
        length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "dept_id",
        nullable = false)
    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Clazz clazz = (Clazz) o;

        if (id != clazz.id) {
            return false;
        }
        if (deptId != clazz.deptId) {
            return false;
        }
        if (name != null ? !name.equals(clazz.name) : clazz.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + deptId;
        return result;
    }
}
