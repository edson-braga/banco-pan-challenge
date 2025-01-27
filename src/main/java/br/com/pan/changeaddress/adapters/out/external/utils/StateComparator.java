package br.com.pan.changeaddress.adapters.out.external.utils;

import java.util.Comparator;

public final class StateComparator {

    private StateComparator() {}

    public static final Comparator<String> PRIORITY_THEN_ALPHABETIC = Comparator
            .comparingInt((String s) -> switch (s.toLowerCase()) {
                case "são paulo"      -> 0;
                case "rio de janeiro" -> 1;
                default -> 2;
            })
            .thenComparing(String::compareToIgnoreCase);
}
