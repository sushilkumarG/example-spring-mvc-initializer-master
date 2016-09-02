package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.Lead;

@Repository
public interface LeadDao extends JpaRepository<Lead, Integer> {

    @Query(
            nativeQuery = true,
            value = "SELECT DISTINCT L.client_id FROM ptigercrm.LEAD_TMP_UPLOAD LTU LEFT JOIN crm.leads L ON LTU.ICRM_LEAD_ID = L.id WHERE LTU.ENQUIRY_TABLE_ID = ?1")
    List<Integer> getClinentByEnquiryId(Integer id);

    @Query(
            nativeQuery = true,
            value = "select contact_number from user.user_contact_numbers where contact_number like %?1%")
    String getFromProptigerDB(String contactNumber);

}
