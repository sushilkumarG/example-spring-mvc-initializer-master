package com.example.enums;

public enum SmsFormat {

    MB("MGCBRK", "You contacted - Ocean(Agent), 9891007653  for 2 BHK  Apartment in Unitech Uniworld Garden II, Malibu To... Price: Rs.72.97 Lac . Ocean is a Magicbricks Certified Agent.Get best deals on the go. Download the Magicbricks App http://goo.gl/1L4A6b"), 
    NA("NNacre", "Dear Vishal Rewri, you may contact Rakesh at +91-9818295266 for INR 19 Lakh 500 Sq. ft. Flat in Sector-41 Noida. Thank You");

    String address;

    String format;

    private SmsFormat(String address, String format) {
        this.address = address;
        this.format = format;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

}
