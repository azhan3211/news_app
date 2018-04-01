package com.azhan.news.Model;

import java.util.ArrayList;
import java.util.List;

public class NewsAssets {

    private static final List<String> Country =  new ArrayList<String>(){{
        add("Argentina-ar");
        add("Australia-au");
        add("Austria-at");
        add("Belgium-be");
        add("Brazil-br");
        add("Bulgaria-bg");
        add("Canada-ca");
        add("China-cn");
        add("Colombia-co");
        add("Cuba-cu");
        add("Czech Republic-cz");
        add("Egypt-eg");
        add("France-fr");
        add("Germany-de");
        add("Greece-gr");
        add("Hong Kong-hk");
        add("Hungary-hu");
        add("India-in");
        add("Indonesia-id");
        add("Ireland-ie");
        add("Israel-il");
        add("Italy-it");
        add("Japan-jp");
        add("Latvia-lv");
        add("Lithuania-lt");
        add("Malaysia-my");
        add("Mexico-mx");
        add("Morocco-ma");
        add("Netherlands-nl");
        add("New Zealand-nz");
        add("Nigeria-ng");
        add("Norway-no");
        add("Philippines-ph");
        add("Poland-pl");
        add("Portugal-pt");
        add("Romania-ro");
        add("Russia-ru");
        add("Saudi Arabia-sa");
        add("Serbia-rs");
        add("Singapore-sg");
        add("Slovakia-sk");
        add("Slovenia-si");
        add("South Africa-za");
        add("South Korea-kr");
        add("Sweden-se");
        add("Switzerland-ch");
        add("Taiwan-tw");
        add("Thailand-th");
        add("Turkey-tr");
        add("UAE-ae");
        add("Ukraine-ua");
        add("United Kingdom-gb");
        add("United States-us");
        add("Vanezuela-ve");
    }};

    public static List<String> getCountry() {
        return Country;
    }
}