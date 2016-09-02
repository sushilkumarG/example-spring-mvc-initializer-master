package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.LeadDao;

@Service
public class LeadService {

    @Autowired
    private LeadDao leadDao;

    public Integer getClinentByEnquiryId(Integer id) {
        List<Integer> userIds = leadDao.getClinentByEnquiryId(id);
        if (userIds != null && !userIds.isEmpty()) {
            return userIds.get(0);
        }
        return 0;
    }

    public boolean isInProptigerDB(String contactNumber) {
        return leadDao.getFromProptigerDB(contactNumber) != null ? true : false;
    }
}
