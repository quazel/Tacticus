package com.bramble.kickback.models;

public enum CountryCode {

    UNITED_STATES(1),
    AFGHANISTAN(93),
    ALBANIA(355),
    ALGERIA(213),
    ANDORRA(376),
    ANGOLA(244),
    ANTARCTICA(672),
    ARGENTINA(54),
    ARMENIA(374),
    ARUBA(297),
    AUSTRALIA(61),
    AUSTRIA(43),
    AZERBAIJAN(994),
    BAHRAIN(973),
    BANGLADESH(880),
    BELARUS(375),
    BELGIUM(32),
    BELIZE(501),
    BENIN(229),
    BHUTAN(975),
    BOLIVIA(591),
    BOSNIA_HERZEGOVINA(387),
    BOTSWANA(267),
    BRAZIL(55),
    BRUNEI(673),
    BULGARIA(359),
    BURKINA_FASO(226),
    BURUNDI(257),
    CAMBODIA(855),
    CAMEROON(237),
    CANADA(-1),
    CAPE_VERDE(238),
    CENTRAL_AFRICAN_REPUBLIC(236),
    CHAD(235),
    CHILE(56),
    CHINA(86),
    CHRISTMAS_ISLAND(-3),
    COCOS_ISLANDS(-4),
    COLOMBIA(57),
    COMOROS(269),
    CONGO_REPUBLIC(242),
    CONGO_DRC(243),
    COOK_ISLANDS(682),
    COSTA_RICA(506),
    CROATIA(385),
    CUBA(53),
    CYPRUS(357),
    CZECH_REPUBLIC(420),
    DENMARK(45),
    DJIBOUTI(253),
    ECUADOR(593),
    EGYPT(20),
    EL_SALVADOR(503),
    EQUATORIAL_GUINEA(240),
    ERITREA(291),
    ESTONIA(372),
    ETHIOPIA(251),
    FALKLAND_ISLANDS(500),
    FAROE_ISLANDS(298),
    FIJI(679),
    FINLAND(358),
    FRANCE(33),
    FRENCH_POLYNESIA(689),
    GABON(241),
    GAMBIA(220),
    GEORGIA(995),
    GERMANY(49),
    GHANA(233),
    GIBRALTAR(350),
    GREECE(30),
    GRENNLAND(299),
    GUATEMALA(502),
    GUINEA(224),
    GUINEA_BISSAU(245),
    GUYANA(592),
    HAITI(509),
    HONDURAS(504),
    HONG_KONG(852),
    HUNGARY(36),
    INDIA(91),
    INDONESIA(62),
    IRAN(98),
    IRAQ(964),
    IRELAND(353),
    ISLE_OF_MAN(-44),
    ISRAEL(972),
    ITALY(39),
    COTE_DIVOIRE(225),
    JAPAN(81),
    JORDAN(962),
    KAZAKHSTAN(-7),
    KENYA(254),
    KIRIBATI(686),
    KUWAIT(965),
    KYRGYZSTAN(996),
    LAOS(856),
    LATVIA(371),
    LEBANON(961),
    LESOTHO(266),
    LIBERIA(231),
    LIBYA(218),
    LIECHTENSTEIN(423),
    LITHUANIA(370),
    LUXEMBOURG(352),
    MACAU(853),
    MACEDONIA_FYROM(389),
    MADAGASCAR(261),
    MALAWI(265),
    MALAYSIA(60),
    MALDIVES(960),
    MALI(223),
    MALTA(356),
    MARSHALL_ISLANDS(692),
    MAURITANIA(222),
    MAURITIUS(230),
    MAYOTTE(262),
    MEXICO(52),
    MICRONESIA(691),
    MOLDOVA(373),
    MONACO(377),
    MONGOLIA(976),
    MONTENEGRO(382),
    MOROCCO(212),
    MOZAMBIQUE(258),
    MYANMAR_BURMA(95),
    NAMIBIA(264),
    NAURU(674),
    NEPAL(977),
    NETHERLANDS(31),
    NETHERLANDS_ANTILLES(599),
    NEW_CALEDONIA(687),
    NEW_ZEALAND(64),
    NICARAGUA(505),
    NIGER(227),
    NIGERIA(234),
    NIUE(683),
    NORTH_KOREA(850),
    NORWAY(47),
    OMAN(968),
    PAKISTAN(92),
    PALAU(680),
    PANAMA(507),
    PAPUA_NEW_GUINEA(675),
    PARAGUAY(595),
    PERU(51),
    PHILIPPINES(63),
    PITCAIRN_ISLANDS(870),
    POLAND(48),
    PORTUGAL(351),
    PUERTO_RICO(-2),
    QATAR(974),
    ROMANIA(40),
    RUSSIA(7),
    RWANDA(250),
    SAINT_BARTHELEMY(590),
    SAMOA(685),
    SAN_MARINO(378),
    SAO_TOME_AND_PRINCIPE(239),
    SAUDI_ARABIA(966),
    SENEGAL(221),
    SERBIA(381),
    SEYCHELLES(248),
    SIERRA_LEONE(232),
    SINGAPORE(65),
    SLOVAKIA(421),
    SLOVENIA(386),
    SOLOMON_ISLANDS(677),
    SOMALIA(252),
    SOUTH_AFRICA(27),
    SOUTH_KOREA(82),
    SPAIN(34),
    SRI_LANKA(94),
    SAINT_HELENA(290),
    SAINT_PIERRE_AND_MIQUELON(508),
    SUDAN(249),
    SURINAME(597),
    SWAZILAND(268),
    SWEDEN(46),
    SWITZERLAND(41),
    SYRIA(963),
    TAIWAN(886),
    TAJIKISTAN(992),
    TANZANIA(255),
    THAILAND(66),
    TIMOR_LESTE(670),
    TOGO(228),
    TOKELAU(690),
    TONGA(676),
    TUNISIA(216),
    TURKEY(90),
    TURKMENISTAN(993),
    TUVALU(688),
    UNITED_ARAB_EMIRATES(971),
    UGANDA(256),
    UNITED_KINGDOM(44),
    UKRAINE(380),
    URUGUAY(598),
    UZBEKISTAN(998),
    VANUATU(678),
    VATICAN_CITY(-39),
    VENEZUELA(58),
    VIETNAM(84),
    WALLIS_AND_FUTUNA(681),
    YEMEN(967),
    ZAMBIA(260),
    ZIMBABWE(263);

    private final int code;

    CountryCode(final int code) {
        this.code = code;
    }


    public String getCanonicalName() {
        switch (code) {
            case 1:
                return "United States";
            case 93:
                return "Afghanistan";
            case 355:
                return "Albania";
            case 213:
                return "Algeria";
            case 376:
                return "Andorra";
            case 244:
                return "Angola";
            case 672:
                return "Antarctica";
            case 54:
                return "Argentina";
            case 374:
                return "Armenia";
            case 297:
                return "Aruba";
            case 61:
                return "Australia";
            case 43:
                return "Austria";
            case 994:
                return "Azerbaijan";
            case 973:
                return "Bahrain";
            case 880:
                return "Bangladesh";
            case 375:
                return "Belarus";
            case 32:
                return "Belgium";
            case 501:
                return "Belize";
            case 229:
                return "Benin";
            case 975:
                return "Bhutan";
            case 591:
                return "Bolivia";
            case 387:
                return "Bosnia and Herzegovina";
            case 267:
                return "Botswana";
            case 55:
                return "Brazil";
            case 673:
                return "Brunei";
            case 359:
                return "Bulgaria";
            case 226:
                return "Burkina Faso";
            case 257:
                return "Burundi";
            case 855:
                return "Cambodia";
            case 237:
                return "Cameroon";
            case -1:
                return "Canada";
            case 238:
                return "Cape Verde";
            case 236:
                return "Central African Republic";
            case 235:
                return "Chad";
            case 56:
                return "Chile";
            case 86:
                return "China";
            case -3:
                return "Christmas Island";
            case -4:
                return "Cocos (Keeling) Islands";
            case 57:
                return "Colombia";
            case 269:
                return "Comoros";
            case 242:
                return "Congo (Republic)";
            case 243:
                return "Congo (DRC)";
            case 682:
                return "Cook Islands";
            case 506:
                return "Costa Rica";
            case 385:
                return "Croatia";
            case 53:
                return "Cuba";
            case 357:
                return "Cyprus";
            case 420:
                return "Czech Republic";
            case 45:
                return "Denmark";
            case 253:
                return "Djibouti";
            case 593:
                return "Ecuador";
            case 20:
                return "Egypt";
            case 503:
                return "El Salvador";
            case 240:
                return "Equatorial Guinea";
            case 291:
                return "Eritrea";
            case 372:
                return "Estonia";
            case 251:
                return "Ethiopia";
            case 500:
                return "Falkland Islands";
            case 298:
                return "Faroe Islands";
            case 679:
                return "Fiji";
            case 358:
                return "Finland";
            case 33:
                return "France";
            case 689:
                return "French Polynesia";
            case 241:
                return "Gabon";
            case 220:
                return "Gambia";
            case 995:
                return "Georgia";
            case 49:
                return "Germany";
            case 233:
                return "Ghana";
            case 350:
                return "Gibraltar";
            case 30:
                return "Greece";
            case 299:
                return "Greenland";
            case 502:
                return "Guatemala";
            case 224:
                return "Guinea";
            case 245:
                return "Guinea-Bissau";
            case 592:
                return "Guyana";
            case 509:
                return "Haiti";
            case 504:
                return "Honduras";
            case 852:
                return "Hong Kong";
            case 36:
                return "Hungary";
            case 91:
                return "India";
            case 62:
                return "Indonesia";
            case 98:
                return "Iran";
            case 964:
                return "Iraq";
            case 353:
                return "Ireland";
            case -44:
                return "Isle of Man";
            case 972:
                return "Israel";
            case 39:
                return "Italy";
            case 225:
                return "Cote d'Ivoire";
            case 81:
                return "Japan";
            case 962:
                return "Jordan";
            case -7:
                return "Kazakhstan";
            case 254:
                return "Kenya";
            case 686:
                return "Kiribati";
            case 965:
                return "Kuwait";
            case 996:
                return "Kyrgyzstan";
            case 856:
                return "Laos";
            case 371:
                return "Latvia";
            case 961:
                return "Lebanon";
            case 266:
                return "Lesotho";
            case 231:
                return "Liberia";
            case 218:
                return "Libya";
            case 423:
                return "Liechtenstein";
            case 370:
                return "Lithuania";
            case 352:
                return "Luxembourg";
            case 853:
                return "Macau";
            case 389:
                return "Macedonia (FYROM)";
            case 261:
                return "Madagascar";
            case 265:
                return "Malawi";
            case 60:
                return "Malaysia";
            case 960:
                return "Maldives";
            case 223:
                return "Mali";
            case 356:
                return "Malta";
            case 692:
                return "Marshall Islands";
            case 222:
                return "Mauritania";
            case 230:
                return "Mauritius";
            case 262:
                return "Mayotte";
            case 52:
                return "Mexico";
            case 691:
                return "Micronesia";
            case 373:
                return "Moldova";
            case 377:
                return "Monaco";
            case 976:
                return "Mongolia";
            case 382:
                return "Montenegro";
            case 212:
                return "Morocco";
            case 258:
                return "Mozambique";
            case 95:
                return "Myanmar (Burma)";
            case 264:
                return "Namibia";
            case 674:
                return "Nauru";
            case 977:
                return "Nepal";
            case 31:
                return "Netherlands";
            case 599:
                return "Netherlands Antilles";
            case 687:
                return "New Caledonia";
            case 64:
                return "New Zealand";
            case 505:
                return "Nicaragua";
            case 227:
                return "Niger";
            case 234:
                return "Nigeria";
            case 683:
                return "Niue";
            case 850:
                return "North Korea";
            case 47:
                return "Norway";
            case 968:
                return "Oman";
            case 92:
                return "Pakistan";
            case 680:
                return "Palau";
            case 507:
                return "Panama";
            case 675:
                return "Papua New Guinea";
            case 595:
                return "Paraguay";
            case 51:
                return "Peru";
            case 63:
                return "Philippines";
            case 870:
                return "Pitcairn Islands";
            case 48:
                return "Poland";
            case 351:
                return "Portugal";
            case -2:
                return "Puerto Rico";
            case 974:
                return "Qatar";
            case 40:
                return "Romania";
            case 7:
                return "Russia";
            case 250:
                return "Rwanda";
            case 590:
                return "Saint Barthelemy";
            case 685:
                return "Samoa";
            case 378:
                return "San Marino";
            case 239:
                return "Sao Tome and Principe";
            case 966:
                return "Saudi Arabia";
            case 221:
                return "Senegal";
            case 381:
                return "Serbia";
            case 248:
                return "Seychelles";
            case 232:
                return "Sierra Leone";
            case 65:
                return "Singapore";
            case 421:
                return "Slovakia";
            case 386:
                return "Slovenia";
            case 677:
                return "Solomon Islands";
            case 252:
                return "Somalia";
            case 27:
                return "South Africa";
            case 82:
                return "South Korea";
            case 34:
                return "Spain";
            case 94:
                return "Sri Lanka";
            case 290:
                return "Saint Helena";
            case 508:
                return "Saint Pierre and Miquelon";
            case 249:
                return "Sudan";
            case 597:
                return "Suriname";
            case 268:
                return "Swaziland";
            case 46:
                return "Sweden";
            case 41:
                return "Switzerland";
            case 963:
                return "Syria";
            case 886:
                return "Taiwan";
            case 992:
                return "Tajikistan";
            case 255:
                return "Tanzania";
            case 66:
                return "Thailand";
            case 670:
                return "Timor-Leste";
            case 228:
                return "Togo";
            case 690:
                return "Tokelau";
            case 676:
                return "Tonga";
            case 216:
                return "Tunisia";
            case 90:
                return "Turkey";
            case 993:
                return "Turkmenistan";
            case 688:
                return "Tuvalu";
            case 971:
                return "United Arab Emirates";
            case 256:
                return "Uganda";
            case 44:
                return "United Kingdom";
            case 380:
                return "Ukraine";
            case 598:
                return "Uruguay";
            case 998:
                return "Uzbekistan";
            case 678:
                return "Vanuatu";
            case -39:
                return "Vatican City";
            case 58:
                return "Venezuela";
            case 84:
                return "Vietnam";
            case 681:
                return "Wallis and Futuna";
            case 967:
                return "Yemen";
            case 260:
                return "Zambia";
            case 263:
                return "Zimbabwe";
            default:
                return "Unspecified";
        }
    }

    public static CharSequence[] getAllCanonicalNames() {
        CharSequence[] toReturn = new CharSequence[CountryCode.values().length];
        for (int i = 0; i < CountryCode.values().length; i++) {
            toReturn[i] = CountryCode.values()[i].getCanonicalName();
        }
        return toReturn;
    }

    public static int getCountryCodeByID(int id) {
        CountryCode countryCode = CountryCode.values()[id];
        if (countryCode == CANADA || countryCode == PUERTO_RICO)
            return UNITED_STATES.code;

        if(countryCode == CHRISTMAS_ISLAND || countryCode == COCOS_ISLANDS)
            return AUSTRALIA.code;

        if(countryCode == KAZAKHSTAN)
            return RUSSIA.code;

        if(countryCode == ISLE_OF_MAN)
            return UNITED_KINGDOM.code;

        if(countryCode == VATICAN_CITY)
            return ITALY.code;

        return countryCode.code;
    }

}
