package dev.koicreek.bokasafn.mimirs.catalog.util;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Stringify {

    /**
     * Wrap the given string in double quotes and return string.
     */
    public static String wrap(String s) {
        if(s != null) {
            return "\"" + s + "\"";
        }

        return null;
    }

    /**
     * Add a tab after all new line characters and return string.
     */
    public static String indent(Object o) {
        if (o == null) {
            return "null";
        }

        return o.toString().replace("\n", "\n\t");
    }

    public static <T> String toString(List<T> list) {
        if(list.size() == 0) return "[ <empty> ]";

        StringBuilder sb = new StringBuilder("[\n\t");
        sb.append(indent(list.get(0)));

        for(int i=1; i < list.size(); ++i) {
            sb.append(",\n\t").append(indent(list.get(i)));
        }
        sb.append("\n]");

        return sb.toString();
    }

    public static <T> String toString(Set<T> set) {
        if(set.size() == 0) return "[ <empty> ]";

        Iterator<T> itr = set.iterator();

        StringBuilder sb = new StringBuilder("[\n\t");
        sb.append(indent(itr.next()));

        while(itr.hasNext()) {
            sb.append(",\n\t").append(indent(itr.next()));
        }
        sb.append("\n]");

        return sb.toString();
    }
}
