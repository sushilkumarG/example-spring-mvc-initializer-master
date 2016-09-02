package com.example.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.model.ParsedSmsData;

public class ParseDemo {

    // private static final String PATTERN = "([0-9]{2})/([0-9]{2})/([0-9]{4})";
    // private static final String DATE = "21/06/1986";

    private static final String NN_ACRES_PATTERN1 = "Dear ([\\w ]*), you may contact ([\\w ]*) at ([\\S]*) for INR ([\\S]*) Lakh ([\\d\\w.\\s]*) Flat in ([\\d\\w-.\\s]*). Thank You";
    private static final String MESSAGE_1         = "Dear Anurag, you may contact Kishore Bansal at +91-9716929377 for INR 18 K 1440 Sq. ft. Builder Floor in Sector-45 Gurgaon. Top Dealer in Sector-45 Gurgaon are\r\nIsha Associates +91-9811331540. Thank You";

    private static final String NN_ACRES_PATTERN2 = "Dear ([\\w ]*), you may contact ([\\w ]*) at ([\\S]*) for INR ([\\S]*) K ([\\S ]*) in ([\\d\\w-\\s]*).";
    private static final String MESSAGE_2         = "Dear Anurag, you may contact Kishore Bansal at +91-9716929377 for INR 18 K 1440 Sq. ft. Builder Floor in Sector-45 Gurgaon.";

    private static final String MESSAGE           = "Dear Anurag, you may contact Kishore Bansal at +91-9716929377 for INR 18 K 1440 Sq. ft. Builder Floor in Sector-45 Gurgaon. Top Dealer in Sector-45 Gurgaon are\r\nIsha Associates +91-9811331540. Thank You";

    // Dear Vishal Rewri, you may contact Rakesh at +91-9818295266 for INR 19
    // Lakh 500 Sq. ft. Flat in Sector-41 Noida. Thank You

    public static void main(String[] args) {

        MessageProcessor mp = new MessageProcessor();
        ParsedSmsData parseData = mp.parseData(MESSAGE, true);
        System.out.println(parseData);

        System.out.println("Demo");
        Pattern datePatt = Pattern.compile(NN_ACRES_PATTERN2);
        Matcher m = datePatt.matcher(MESSAGE_2);

        if (m.matches()) {
            String userName = m.group(1);
            String agentName = m.group(2);
            String mobileNumber = m.group(3);
            String budget = m.group(4);
            String area = m.group(5);
            // String location = m.group(6);
            System.out.println(userName);
            System.out.println(agentName);
            System.out.println(mobileNumber);
            System.out.println(budget);
            System.out.println(area);
            // System.out.println(location);

            // int day = Integer.parseInt(m.group(1));
            // int month = Integer.parseInt(m.group(2));
            // int year = Integer.parseInt(m.group(3));
            //
            // System.out.println(day);
            // System.out.println(month);
            // System.out.println(year);
        }

    }

}
