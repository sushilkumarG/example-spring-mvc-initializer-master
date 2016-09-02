package com.example.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.enums.MBPatterns;
import com.example.enums.NNAcresPatterns;
import com.example.model.ParsedSmsData;

public class MessageProcessor {

    private static final String FLAT_UNIT        = "flat";
    public static final String  LACS             = "Lakh";
    public static final String  CRORES           = "Crore";

    public static final String  MB_APARTMENT     = "Apartment";
    public static final String  MB_BUILDER_FLOOR = "Builder Floor";

    public ParsedSmsData parseData(String text, boolean isNNacres) {
        text = text.replaceAll("   ", " ");
        text = text.replaceAll("  ", " ");
        if (isNNacres) {
            return parseNNData(text);
        }
        return parseMBData(text);
    }

    public ParsedSmsData parseNNData(String text) {
        boolean isLacs = text.contains(LACS);
        Pattern datePatt1 = Pattern.compile(NNAcresPatterns.SIMPLE_FLAT_WITH_AREA_IN_SQ_FT_PRICE_IN_LAKHS
                .getPattern(isLacs));
        Matcher m1 = datePatt1.matcher(text);

        if (m1.matches()) {
            String agentName = m1.group(2);
            String mobileNumber = m1.group(3);
            String budget = m1.group(4);
            String area = m1.group(5);
            String location = m1.group(6);

            ParsedSmsData pd = new ParsedSmsData();
            pd.setBrokerName(agentName);
            pd.setBrokerMobileNumber(mobileNumber);
            budget = isLacs ? budget + " " + LACS : budget + " " + CRORES;
            pd.setBudget(budget);
            pd.setLocality(location);
            pd.setUnitType(FLAT_UNIT);
            pd.setArea(area);
            pd.setIsNNAcres(true);

            return pd;
        }
        else {
            Pattern datePatt2 = Pattern.compile(NNAcresPatterns.SIMPLE_FLAT_NO_AREA_PRICE_IN_LAKHS.getPattern(isLacs));
            Matcher m2 = datePatt2.matcher(text);

            if (m2.matches()) {
                String agentName = m2.group(2);
                String mobileNumber = m2.group(3);
                String budget = m2.group(4);
                String location = m2.group(5);

                ParsedSmsData pd = new ParsedSmsData();
                pd.setBrokerName(agentName);
                pd.setBrokerMobileNumber(mobileNumber);
                budget = isLacs ? budget + " " + LACS : budget + " " + CRORES;
                pd.setBudget(budget);
                pd.setLocality(location);
                pd.setUnitType(FLAT_UNIT);
                pd.setIsNNAcres(true);

                return pd;
            }
            return null;
        }
    }

    public ParsedSmsData parseMBData(String text) {
        boolean isApartment = text.contains(MB_APARTMENT);

        Pattern datePatternLacs = Pattern.compile(MBPatterns.FLAT_WITH_BHK_PROJECT_PRICE_IN_LACS
                .getPattern(isApartment));
        Matcher mLacs = datePatternLacs.matcher(text);

        Pattern datePatternCrs = Pattern.compile(MBPatterns.FLAT_WITH_BHK_PROJECT_PRICE_IN_CRS.getPattern(isApartment));
        Matcher mCrs = datePatternCrs.matcher(text);

        Pattern datePattern = Pattern.compile(MBPatterns.FLAT_WITH_BHK_PROJECT_PRICE.getPattern(isApartment));
        Matcher m = datePattern.matcher(text);

        BudgetUnit budgetUnit = null;
        Matcher matching = null;
        ParsedSmsData pd = new ParsedSmsData();
        if (mLacs.matches()) {
            budgetUnit = BudgetUnit.LACS;
            matching = mLacs;
        }
        else if (mCrs.matches()) {
            budgetUnit = BudgetUnit.CRS;
            matching = mCrs;
        }
        else if (m.matches()) {
            budgetUnit = BudgetUnit.EXACT;
            matching = m;
        }
        else {
            return null;
        }

        String agentName = matching.group(1);
        String mobileNumber = matching.group(2);
        String bhk = matching.group(3);
        String project = matching.group(4);
        String price = matching.group(5);

        pd.setBrokerName(agentName);
        pd.setBrokerMobileNumber(mobileNumber);
        pd.setBudget(price + budgetUnit.getSuffix());
        pd.setProject(project);
        pd.setUnitType(isApartment ? MB_APARTMENT : MB_BUILDER_FLOOR);
        pd.setBhk(bhk.replaceAll(" BHK", ""));
        pd.setIsNNAcres(false);

        return pd;
    }
}

enum BudgetUnit {
    CRS(" Cr"),
    LACS(" Lakhs"),
    EXACT("");

    private String suffix;

    private BudgetUnit(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }
}
