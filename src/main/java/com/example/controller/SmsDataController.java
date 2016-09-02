package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.HintsForUser;
import com.example.model.ParsedSmsData;
import com.example.model.SmsData;
import com.example.service.SmsDataService;

@Controller
@RequestMapping(value = "/smsdata/")
public class SmsDataController {

    @Autowired
    private SmsDataService smsDataService;

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    @ResponseBody
    public Boolean addSmsData(@PathVariable Integer id, @RequestBody List<SmsData> smsData) {
        try {
            smsDataService.addOrCreate(id, smsData);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    @ResponseBody
    public List<ParsedSmsData> getParsedSmsData(@PathVariable Integer userId) {
        try {
            return smsDataService.get(userId);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/hints/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public HintsForUser getHints(@PathVariable Integer userId) {
        try {
            return smsDataService.getHints(userId);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/sellers", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getBrokers() {
        try {
            return smsDataService.getBrokers();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
