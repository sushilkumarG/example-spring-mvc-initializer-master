package com.example.utils;

import org.jcodec.common.Assert;
import org.junit.Test;

import com.example.model.ParsedSmsData;

public class MB {

    @Test
    public void test1() {
        String message = "You contacted - Ocean(Agent), 9891007653  for 2 BHK  Apartment in Unitech Uniworld Garden II, Malibu To... Price: Rs.72.97 Lac . Ocean is a Magicbricks Certified Agent.Get best deals on the go. Download the Magicbricks App http://goo.gl/1L4A6b";

        MessageProcessor mp = new MessageProcessor();
        ParsedSmsData parseData = mp.parseData(message, false);
        System.out.println(parseData);
        Assert.assertNotNull(parseData);
    }

    @Test
    public void test2() {
        String message = "You contacted - Ajai Singh..(Agent), 9810126102 for 3 BHK Apartment in Bestech Park View City 2, Sohna Road. Price: Rs.1.76 Cr- Magicbricks.com Get best deals on the go. Download the Magicbricks App";

        MessageProcessor mp = new MessageProcessor();
        ParsedSmsData parseData = mp.parseData(message, false);
        System.out.println(parseData);
        Assert.assertNotNull(parseData);
    }
}
