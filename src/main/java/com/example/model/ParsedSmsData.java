package com.example.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParsedSmsData implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1286224329796026181L;

    @Id
    @Field("id")
    private Integer           id;

    private Integer           userId;

    private Long              date;

    private String            address;

    private String            city;

    private String            locality;

    private String            project;

    private String            budget;

    private String            bhk;

    private String            unitType;

    private String            brokerMobileNumber;

    private String            brokerName;

    private Boolean           isNNAcres;

    private String            area;

    public ParsedSmsData() {
    }

    public ParsedSmsData(Integer userId) {
        super();
        this.userId = userId;
    }

    public void setKeys(Integer userId, Long date, String address) {
        this.userId = userId;
        this.date = date;
        this.address = address;
    }

    public Integer getId() {

        return id;

    }

    public void setId(Integer id) {

        this.id = id;

    }

    public Integer getUserId() {

        return userId;

    }

    public void setUserId(Integer userId) {

        this.userId = userId;

    }

    public Long getDate() {

        return date;

    }

    public void setDate(Long date) {

        this.date = date;

    }

    public String getAddress() {

        return address;

    }

    public void setAddress(String address) {

        this.address = address;

    }

    public String getCity() {

        return city;

    }

    public void setCity(String city) {

        this.city = city;

    }

    public String getLocality() {

        return locality;

    }

    public void setLocality(String locality) {

        this.locality = locality;

    }

    public String getProject() {

        return project;

    }

    public void setProject(String project) {

        this.project = project;

    }

    public String getBudget() {

        return budget;

    }

    public void setBudget(String budget) {

        this.budget = budget;

    }

    public String getBhk() {

        return bhk;

    }

    public void setBhk(String bhk) {

        this.bhk = bhk;

    }

    public String getUnitType() {

        return unitType;

    }

    public void setUnitType(String unitType) {

        this.unitType = unitType;

    }

    public String getBrokerMobileNumber() {

        return brokerMobileNumber;

    }

    public void setBrokerMobileNumber(String brokerMobileNumber) {

        this.brokerMobileNumber = brokerMobileNumber;

    }

    public Boolean getIsNNAcres() {

        return isNNAcres;

    }

    public void setIsNNAcres(Boolean isNNAcres) {

        this.isNNAcres = isNNAcres;

    }

    public String getBrokerName() {

        return brokerName;

    }

    public void setBrokerName(String brokerName) {

        this.brokerName = brokerName;

    }

    public String getArea() {

        return area;

    }

    public void setArea(String area) {

        this.area = area;

    }

    @Override
    public String toString() {

        return "ParsedSmsData [locality=" + locality

        + ", budget="

        + budget

        + ", unitType="

        + unitType

        + ", brokerMobileNumber="

        + brokerMobileNumber

        + ", brokerName="

        + brokerName + ", project="

        + project

        + ", isNNAcres="

        + isNNAcres

        + ", area="

        + area

        + "]";

    }

}