package com.lomig.secretsanta;

public class LanguageChoice {

    private String languageName;
    private int flagImage;

    public LanguageChoice(String languageName, int flagImage){
        this.languageName = languageName;
        this.flagImage = flagImage;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public int getFlagImage() {
        return flagImage;
    }

    public void setFlagImage(int flagImage) {
        this.flagImage = flagImage;
    }
}
