package com.example.utils;

import org.jcodec.common.Assert;
import org.junit.Test;

import com.example.model.ParsedSmsData;

public class NNAcres {

    @Test
    public void test1() {
        String message = "Dear Vishal Rewri, you may contact Rakesh at +91-9818295266 for INR 19 Lakh 500 Sq. ft. Flat in Sector-41 Noida. Thank You";

        MessageProcessor mp = new MessageProcessor();
        ParsedSmsData parseData = mp.parseData(message, true);
        System.out.println(parseData);
        Assert.assertNotNull(parseData);
    }

    @Test
    public void test2() {
        String message = "Dear Vishal Rewri, you may contact Jasvinder Singh at +91-8527336998 for INR 6.01 Lakh Flat in Sector-81 Gurgaon. Thank You";

        MessageProcessor mp = new MessageProcessor();
        ParsedSmsData parseData = mp.parseData(message, true);
        System.out.println(parseData);
        Assert.assertNotNull(parseData);
    }

    @Test
    public void test3() {
        String message = "Dear Sskk, you may contact Aman Ahuja at +91-8010222666 for INR 1.36 Crore Flat in Sector-62 Gurgaon. Thank You";

        MessageProcessor mp = new MessageProcessor();
        ParsedSmsData parseData = mp.parseData(message, true);
        System.out.println(parseData);
        Assert.assertNotNull(parseData);
    }

    @Test
    public void test4() {
        String message = "Dear Anurag, you may contact MrSummit Jadwani at +91-9810979973 for INR 1.25 Crore Flat in Sector-90 Gurgaon. Top Dealers in Sector-90 Gurgaon are\r\nPriyankas Housing Consultants Pvt. Ltd. +91-8527540404\r\nReal Infra Solution +91-9810802935. Thank You";

        MessageProcessor mp = new MessageProcessor();
        ParsedSmsData parseData = mp.parseData(message, true);
        System.out.println(parseData);
        Assert.assertNotNull(parseData);
    }

    @Test
    public void test5() {
        String message = "Dear Anurag, you may contact Kapil Arora at +91-9711142395 for INR 62 Lakh 1200 Sq. ft. Flat in Sector-81 Gurgaon. Top Dealers in Sector-81 Gurgaon are\r\nDream World Properties +91-9810056546\r\nReal Infra Solution +91-9810802935. Thank You";

        MessageProcessor mp = new MessageProcessor();
        ParsedSmsData parseData = mp.parseData(message, true);
        System.out.println(parseData);
        Assert.assertNotNull(parseData);
    }

}
