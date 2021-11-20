package com.lomig.secretsanta;

public class CountryRegion {

    private String countryName;
    private int flagImage;

    public CountryRegion(String countryName, int flagImage) {
        this.countryName = countryName;
        this.flagImage = flagImage;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getFlagImage() {
        return flagImage;
    }

    public void setFlagImage(int flagImage) {
        this.flagImage = flagImage;
    }
}
