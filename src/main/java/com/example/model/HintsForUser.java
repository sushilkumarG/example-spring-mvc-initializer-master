package com.example.model;

import java.io.Serializable;
import java.util.Set;

public class HintsForUser implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4193906605818896686L;

    Integer     flag;
    Set<String> locations;
    Set<String> propretyTypes;
    Integer     minBudget;
    Integer     maxBudget;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Set<String> getLocations() {
        return locations;
    }

    public void setLocations(Set<String> locations) {
        this.locations = locations;
    }

    public Set<String> getPropretyTypes() {
        return propretyTypes;
    }

    public void setPropretyTypes(Set<String> propretyTypes) {
        this.propretyTypes = propretyTypes;
    }

    public Integer getMinBudget() {
        return minBudget;
    }

    public void setMinBudget(Integer minBudget) {
        this.minBudget = minBudget;
    }

    public Integer getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(Integer maxBudget) {
        this.maxBudget = maxBudget;
    }
}