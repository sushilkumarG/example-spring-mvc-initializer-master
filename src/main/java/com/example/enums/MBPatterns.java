package com.example.enums;

public enum MBPatterns {

    FLAT_WITH_BHK_PROJECT_PRICE(

    "You contacted - ([\\w. ]*)\\(Agent\\), ([\\S]*) for ([\\d \\w]*) Apartment in ([\\d ,.\\w]*) Price: Rs.([\\S,]*) Be the ([\\S ]*)"),

    FLAT_WITH_BHK_PROJECT_PRICE_IN_LACS(

    "You contacted - ([\\w. ]*)\\(Agent\\), ([\\S]*) for ([\\d \\w]*) Apartment in ([\\d ,.\\w]*) Price: Rs.([\\S.]*) Lac([\\S ]*)"),

    FLAT_WITH_BHK_PROJECT_PRICE_IN_CRS(

    "You contacted - ([\\w. ]*)\\(Agent\\), ([\\S]*) for ([\\d \\w]*) Apartment in ([\\d ,.\\w]*) Price: Rs.([\\S.]*) Cr-([\\S ]*)");

    private String pattern;

    private MBPatterns(String pattern) {

        this.pattern = pattern;

    }

    public String getPattern() {

        return pattern;

    }
}
