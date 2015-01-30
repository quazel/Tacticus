package com.bramble.kickback.models;

public enum CountryCode {

    UNITED_STATES(1),
    CANADA(-1),
    AFGHANISTAN(93),
    ALBANIA(355),
    ALGERIA(213),
    ANDORRA(376),
    ANGOLA(244),
    ANTARCTICA(672),
    ARGENTINA(54);

    private final int code;

    CountryCode(final int code) {
        this.code = code;
    }


    public String getCanonicalName() {
        switch (code) {
            case -1:
                return "Canada - CA";
            case 1:
                return "United States - US";
            case 93:
                return "Afghanistan - AF";
            case 355:
                return "Albania - AL";
            case 213:
                return "Algeria - DZ";
            case 376:
                return "Andorra - AD";
            case 244:
                return "Angola - AO";
            case 672:
                return "Antarctica - AQ";
            case 54:
                return "Argentina - AR";
            default:
                return "Unspecified";
        }
    }

    public CharSequence[] getAllCanonicalNames() {
        CharSequence[] toReturn = new CharSequence[CountryCode.values().length];
        for (int i = 0; i < CountryCode.values().length; i++) {
            toReturn[i] = CountryCode.values()[i].getCanonicalName();
        }
        return toReturn;
    }

    public int getCountryCodeByID(int id) {
        CountryCode countryCode = CountryCode.values()[id];
        if (countryCode == CANADA)
            return UNITED_STATES.code;
        return countryCode.code;
    }

}
