package com.dubatovka.app.entity;

//TODO может пока вообще в проекте не нужен
public class OutcomeType {
    private String type;
    private String description;
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OutcomeType)) return false;
        
        OutcomeType that = (OutcomeType) o;
        
        return type != null ? type.equals(that.type) : that.type == null;
        
    }
    
    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "OutcomeType{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
