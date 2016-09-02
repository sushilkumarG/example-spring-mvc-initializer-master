package com.example.service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.HintsForUser;
import com.example.model.ParsedSmsData;
import com.example.model.SmsData;
import com.example.utils.MessageProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mongo.serialize.MongoJsonSerializer;

@Service
public class SmsDataService {

    // @Autowired
    // MongoOperations mongo;

    @PostConstruct
    void createCollection() throws UnknownHostException {
        MongoJsonSerializer serializer;
        serializer = new MongoJsonSerializer("sms_data");
        serializer.addUniqueConstraint("userId", "date", "address");

        serializer = new MongoJsonSerializer("parsed_sms_data");
        serializer.addUniqueConstraint("userId", "date", "address");
    }

    @Autowired
    DataParserService dataParserService;

    @Autowired
    LeadService       leadService;

    public void addOrCreate(Integer id, List<SmsData> smsData) throws UnknownHostException, JsonProcessingException {
        MongoJsonSerializer serializer = new MongoJsonSerializer("sms_data");
        for (SmsData sd : smsData) {
            sd.setEnquiryId(id);
            try {
                String userSmsDataJson = new ObjectMapper().writeValueAsString(sd);
                serializer.addJson(userSmsDataJson);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        Integer userId = leadService.getClinentByEnquiryId(id);
        dataParserService.parseAndSave(userId, smsData);
    }

    public List<ParsedSmsData> get(Integer userId) throws IOException {
        MongoJsonSerializer serializer = new MongoJsonSerializer("parsed_sms_data");
        //ParsedSmsData query = new ParsedSmsData(userId);
        String queryJson = "{\"userId\":" + userId + "}"; //new ObjectMapper().writeValueAsString(query);
        List<String> jsons = serializer.getJson(queryJson);
        List<ParsedSmsData> parsedSmsData = new ArrayList<ParsedSmsData>();
        for (String json : jsons) {
            ParsedSmsData psd = new ObjectMapper().readValue(json, ParsedSmsData.class);
            parsedSmsData.add(psd);
        }
        return parsedSmsData;
    }

    private Integer parseBudget(String budgetString) {
        Integer budget;
        if (budgetString.contains(MessageProcessor.LACS)) {
            budget = (int) (Double.valueOf(budgetString.split(" ")[0]) * 100000);
        }
        else if (budgetString.contains(MessageProcessor.CRORES)) {
            budget = (int) (Double.valueOf(budgetString.split(" ")[0]) * 10000000);
        }
        else {
            budget = Integer.valueOf(budgetString.replace(",", ""));
        }
        return budget;
    }

    public HintsForUser getHints(Integer userId) throws IOException {
        List<ParsedSmsData> parsedSmsData = get(userId);
        HintsForUser hintsForUser = new HintsForUser();
        if (parsedSmsData != null) {
            if (parsedSmsData.size() < 2) {
                hintsForUser.setFlag(1);
            }
            else if (parsedSmsData.size() >= 2 && parsedSmsData.size() < 6) {
                hintsForUser.setFlag(2);
            }
            else {
                hintsForUser.setFlag(3);
            }
            Set<String> locations = new HashSet<String>();
            Set<String> propretyTypes = new HashSet<String>();
            if (!parsedSmsData.isEmpty()) {
                Integer minBudget = Integer.MAX_VALUE;
                Integer maxBudget = 0;
                for(ParsedSmsData psd : parsedSmsData) {
                    Integer budget = parseBudget(psd.getBudget());
                    if(budget > maxBudget) {
                        maxBudget = budget;
                    }
                    if(budget < minBudget) {
                        minBudget = budget;
                    }
                    if (psd.getLocality() != null) {
                        locations.add(psd.getLocality());
                    }
                    if (psd.getProject() != null) {
                        locations.add(psd.getProject());
                    }
                    if (psd.getUnitType() != null) {
                        propretyTypes.add(psd.getUnitType());
                    }
                }
                hintsForUser.setMaxBudget(maxBudget);
                hintsForUser.setMinBudget(minBudget);
            }
            hintsForUser.setLocations(locations);
            hintsForUser.setPropretyTypes(propretyTypes);
        }

        return hintsForUser;
    }

    public Map<String, String> getBrokers() throws IOException {
        Map<String, String> brokers = new HashMap<String, String>();
        List<ParsedSmsData> parsedSmsData = getAll();
        if (parsedSmsData != null) {
            for (ParsedSmsData psd : parsedSmsData) {
                if (psd.getBrokerMobileNumber() != null && !leadService.isInProptigerDB(psd.getBrokerMobileNumber())) {
                    brokers.put(psd.getBrokerName(), psd.getBrokerMobileNumber());
                }
            }
        }
        return brokers;
    }

    private List<ParsedSmsData> getAll() throws IOException {
        MongoJsonSerializer serializer = new MongoJsonSerializer("parsed_sms_data");
        List<String> jsons = serializer.getJson(null);
        List<ParsedSmsData> parsedSmsData = new ArrayList<ParsedSmsData>();
        for (String json : jsons) {
            ParsedSmsData psd = new ObjectMapper().readValue(json, ParsedSmsData.class);
            parsedSmsData.add(psd);
        }
        return parsedSmsData;
    }

}
