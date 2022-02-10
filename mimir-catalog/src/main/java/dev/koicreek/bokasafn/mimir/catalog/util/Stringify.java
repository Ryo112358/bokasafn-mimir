package dev.koicreek.bokasafn.mimir.catalog.util;

import java.util.List;

public class Stringify {

    public static String wrapInQuotations(String s) {
        if(s != null) {
            return "\"" + s + "\"";
        }

        return null;
    }

    public static String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }

        return o.toString().replace("\n", "\n\t");
    }

    public static <T> String listToString(List<T> list) {
        return listToString(list, null);
    }

    public static <T> String listToString(List<T> list, String label) {
        if(list.size() == 0) return "{ <empty> }";

        StringBuilder sb = new StringBuilder(label == null ? "" : label + " ").append("[\n\t");
        sb.append(toIndentedString(list.get(0)));

        for(int i=1; i < list.size(); ++i) {
            sb.append(",\n\t").append(toIndentedString(list.get(i)));
        }
        sb.append("\n]");

        return sb.toString();
    }
}
