package com.bramble.kickback.models;

public enum CountryCode {

    UNITED_STATES_AND_CANADA(1),
    ARGENTINA(343),
    UNSPECIFIED(-1);

    private final int code;

    CountryCode(final int code) {
        this.code = code;
    }


    public String getCanonicalName() {
        switch (code) {
            case 1:
                return "US - United States (and Canada)";
            default:
                return "Unspecified";
        }
    }


}
