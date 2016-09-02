package com.example.enums;

import com.example.utils.MessageProcessor;

public enum MBPatterns {

    FLAT_WITH_BHK_PROJECT_PRICE(
            "You contacted - ([\\w. ]*)\\(Agent\\), ([\\S]*) for ([\\d \\w]*) Apartment in ([\\d ,.\\w]*) Price: Rs.([\\S,]*) Be the ([\\S \n]*)"),
    FLAT_WITH_BHK_PROJECT_PRICE_IN_LACS(
            "You contacted - ([\\w. ]*)\\(Agent\\), ([\\S]*) for ([\\d \\w]*) Apartment in ([\\d ,.\\w]*) Price: Rs.([\\S.]*) Lac([\\S \n]*)"),
    FLAT_WITH_BHK_PROJECT_PRICE_IN_CRS(
            "You contacted - ([\\w. ]*)\\(Agent\\), ([\\S]*) for ([\\d \\w]*) Apartment in ([\\d ,.\\w]*) Price: Rs.([\\S.]*) Cr- ([\\S \n]*)");

    ;
    private String pattern;

    private MBPatterns(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern(boolean apartment) {
        if (apartment) {
            return pattern;
        }
        else {
            return pattern.replaceAll(MessageProcessor.MB_APARTMENT, MessageProcessor.MB_BUILDER_FLOOR);
        }
    }
}
