package com.example.enums;

import com.example.utils.MessageProcessor;

public enum NNAcresPatterns {

    SIMPLE_FLAT_WITH_AREA_IN_SQ_FT_PRICE_IN_LAKHS(
            "Dear ([\\w. ]*), you may contact ([\\w. ]*) at ([\\S]*) for INR ([\\S]*) Lakh ([\\d\\w.\\s]*) Flat in ([\\d\\w-.\\s]*). Thank You"),

    SIMPLE_FLAT_NO_AREA_PRICE_IN_LAKHS(
            "Dear ([\\w. ]*), you may contact ([\\w. ]*) at ([\\S]*) for INR ([\\S]*) Lakh Flat in ([\\d\\w-.\\s]*). Thank You");

    private String pattern;

    private NNAcresPatterns(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern(boolean priceInLakhs) {
        if (priceInLakhs)
            return pattern;
        else
            return pattern.replaceAll(MessageProcessor.LACS, MessageProcessor.CRORES);
    }
}