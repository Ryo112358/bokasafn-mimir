package dev.koicreek.bokasafn.mimirs.users;

import java.util.Collection;
import java.util.Iterator;

public class StringifyUtil {

    /**
     * Wrap the given string in double quotes and return.
     */
    public static String wrap(String s) {
        if(s != null) {
            return "\"" + s + "\"";
        }

        return null;
    }

    /**
     * Add a tab after all new line characters and return.
     */
    public static String indent(Object o) {
        if (o == null) {
            return "null";
        }

        return o.toString().replace("\n", "\n\t");
    }

    public static <T> String toString(Collection<T> collection) {
        if(collection.size() == 0) return "[ <empty> ]";

        Iterator<T> itr = collection.iterator();

        StringBuilder sb = new StringBuilder("[\n\t");
        sb.append(indent(itr.next()));

        while(itr.hasNext()) {
            sb.append(",\n\t").append(indent(itr.next()));
        }
        sb.append("\n]");

        return sb.toString();
    }
}
