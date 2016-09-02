package com.example.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "crm.leads")
@JsonFilter("fieldFilter")
@JsonInclude(Include.NON_NULL)
public class Lead implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "client_id")
    private int clientId;

    @Column(name = "makaan_client_id")
    private Integer makaanClientId;

    @Column(name = "city_id")
    private int cityId;

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "client_type_id")
    private Integer clientTypeId;

    @Column(name = "notes")
    private String notes;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "flexible_budget_flag")
    private boolean flexibleBudget;

    @Column(name = "show_similar")
    private Boolean showSimilar;

    @Column(name = "sale_type_id", insertable = false, updatable = false, nullable = true)
    private Integer saleTypeId;

    @Column(name = "loan_required")
    private Boolean loanRequired;

    @Column(name = "client_furious")
    private Boolean clientFurious;

    @Column(name = "lead_hot")
    private Boolean leadHot;

    @Column(name = "latest_lead_assigment")
    private Integer latestLeadAssigment;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "time_frame_id")
    private Integer timeFrameId;

    @Column(name = "view_ids")
    private String viewIds;

    @Column(name = "direction_ids")
    private String directionIds;

    @Column(name = "min_floor")
    private Integer minFloor;

    @Column(name = "max_floor")
    private Integer maxFloor;

    @Column(name = "top_floor")
    private Boolean topFloor;

    @Column(name = "property_type_id")
    private Integer propertyTypeId;

    @Column(name = "budget_not_provided")
    private Boolean budgetNotProvided;

    @Column(name = "sale_type_not_sure")
    private Boolean saleTypeNotSure;

    @Column(name = "client_first_enquiry_time")
    private Date clientFirstEnquiryTime;

    @Column(name = "directly_assign_to_sales")
    private Boolean directlyAssignToSales;

    @Column(name = "company_id")
    private Integer companyId;

    @PrePersist
    private void addCreatedDate() {// NOPMD
        Date currentDate = new Date();
        setCreatedAt(currentDate);
        setUpdatedAt(currentDate);
    }

    @PreUpdate
    private void addUpdatedDate() {// NOPMD
        setUpdatedAt(new Date());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Integer getMakaanClientId() {
        return makaanClientId;
    }

    public void setMakaanClientId(Integer makaanClientId) {
        this.makaanClientId = makaanClientId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getClientTypeId() {
        return clientTypeId;
    }

    public void setClientTypeId(Integer clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean isFlexibleBudget() {
        return flexibleBudget;
    }

    public void setFlexibleBudget(boolean flexibleBudget) {
        this.flexibleBudget = flexibleBudget;
    }

    public Boolean getShowSimilar() {
        return showSimilar;
    }

    public void setShowSimilar(Boolean showSimilar) {
        this.showSimilar = showSimilar;
    }

    public Integer getSaleTypeId() {
        return saleTypeId;
    }

    public void setSaleTypeId(Integer saleTypeId) {
        this.saleTypeId = saleTypeId;
    }

    public Boolean getLoanRequired() {
        return loanRequired;
    }

    public void setLoanRequired(Boolean loanRequired) {
        this.loanRequired = loanRequired;
    }

    public Boolean getClientFurious() {
        return clientFurious;
    }

    public void setClientFurious(Boolean clientFurious) {
        this.clientFurious = clientFurious;
    }

    public Boolean getLeadHot() {
        return leadHot;
    }

    public void setLeadHot(Boolean leadHot) {
        this.leadHot = leadHot;
    }

    public Integer getLatestLeadAssigment() {
        return latestLeadAssigment;
    }

    public void setLatestLeadAssigment(Integer latestLeadAssigment) {
        this.latestLeadAssigment = latestLeadAssigment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getTimeFrameId() {
        return timeFrameId;
    }

    public void setTimeFrameId(Integer timeFrameId) {
        this.timeFrameId = timeFrameId;
    }

    public String getViewIds() {
        return viewIds;
    }

    public void setViewIds(String viewIds) {
        this.viewIds = viewIds;
    }

    public String getDirectionIds() {
        return directionIds;
    }

    public void setDirectionIds(String directionIds) {
        this.directionIds = directionIds;
    }

    public Integer getMinFloor() {
        return minFloor;
    }

    public void setMinFloor(Integer minFloor) {
        this.minFloor = minFloor;
    }

    public Integer getMaxFloor() {
        return maxFloor;
    }

    public void setMaxFloor(Integer maxFloor) {
        this.maxFloor = maxFloor;
    }

    public Boolean getTopFloor() {
        return topFloor;
    }

    public void setTopFloor(Boolean topFloor) {
        this.topFloor = topFloor;
    }

    public Integer getPropertyTypeId() {
        return propertyTypeId;
    }

    public void setPropertyTypeId(Integer propertyTypeId) {
        this.propertyTypeId = propertyTypeId;
    }

    public Boolean getBudgetNotProvided() {
        return budgetNotProvided;
    }

    public void setBudgetNotProvided(Boolean budgetNotProvided) {
        this.budgetNotProvided = budgetNotProvided;
    }

    public Boolean getSaleTypeNotSure() {
        return saleTypeNotSure;
    }

    public void setSaleTypeNotSure(Boolean saleTypeNotSure) {
        this.saleTypeNotSure = saleTypeNotSure;
    }

    public Date getClientFirstEnquiryTime() {
        return clientFirstEnquiryTime;
    }

    public void setClientFirstEnquiryTime(Date clientFirstEnquiryTime) {
        this.clientFirstEnquiryTime = clientFirstEnquiryTime;
    }

    public Boolean getDirectlyAssignToSales() {
        return directlyAssignToSales;
    }

    public void setDirectlyAssignToSales(Boolean directlyAssignToSales) {
        this.directlyAssignToSales = directlyAssignToSales;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
