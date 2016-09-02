package com.example.service;

import java.net.UnknownHostException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.enums.SmsFormat;
import com.example.model.ParsedSmsData;
import com.example.model.SmsData;
import com.example.utils.MessageProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import mongo.serialize.MongoJsonSerializer;

@Service
public class DataParserService {

    MessageProcessor messageProcessor;

    private static Logger logger = LoggerFactory.getLogger(DataParserService.class);

    @Async
    void parseAndSave(Integer userId, List<SmsData> smsData) throws UnknownHostException {
        for (SmsData sd : smsData) {
            ParsedSmsData parsedSmsData = parseAndGet(sd);
            if (parsedSmsData != null) {
                parsedSmsData.setKeys(userId, sd.getDate(), sd.getAddress());
                MongoJsonSerializer serializer = new MongoJsonSerializer("parsed_sms_data");
                try {
                    String parsedSmsDataJson = new ObjectMapper().writeValueAsString(parsedSmsData);
                    serializer.addJson(parsedSmsDataJson);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    logger.error("parsedsmsdata " + e);
                }
            }
        }
    }

    ParsedSmsData parseAndGet(SmsData sd) {
        messageProcessor = new MessageProcessor();
        ParsedSmsData parsedSmsData = new ParsedSmsData();
        if (sd.getAddress().contains(SmsFormat.MB.getAddress())) {
            parsedSmsData = messageProcessor.parseData(sd.getBody(), false);
        }
        else {
            parsedSmsData = messageProcessor.parseData(sd.getBody(), true);
        }
        return parsedSmsData;
    }
}
