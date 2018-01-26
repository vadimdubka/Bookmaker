package com.dubatovka.app.entity;

import java.io.Serializable;
import java.util.Set;

public class Category implements Serializable {
    private static final long serialVersionUID = 5577191201787646890L;
    private int id;
    private String name;
    private int parentId;
    private Set<Category> childCategorySet;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getParentId() {
        return parentId;
    }
    
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
    
    public Set<Category> getChildCategorySet() {
        return childCategorySet;
    }
    
    public void setChildCategorySet(Set<Category> childCategorySet) {
        this.childCategorySet = childCategorySet;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        
        Category category = (Category) o;
        
        if (id != category.id) return false;
        if (parentId != category.parentId) return false;
        if (name != null ? !name.equals(category.name) : category.name != null) return false;
        return childCategorySet != null ? childCategorySet.equals(category.childCategorySet) : category.childCategorySet == null;
        
    }
    
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + parentId;
        result = 31 * result + (childCategorySet != null ? childCategorySet.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Category{" +
                       "id=" + id +
                       ", name='" + name + '\'' +
                       ", parentId=" + parentId +
                       ", childCategorySet=" + childCategorySet +
                       '}';
    }
}
