package dev.koicreek.bokasafn.mimir.catalog.constants;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum BookFormat {
    PAPERBACK("Paperback"),                 // 0
    HARDCOVER("Hardcover"),                 // 1
    MASS_MARKET_PAPERBACK("Mass Market");   // 2

    @Getter
    private final String format;

    BookFormat(String format) {
        this.format = format;
    }

    //#region GetEnumByBookFormat

    public static final BookFormat[] values = values();
    private static final Map<String, BookFormat> bookFormatMap = new HashMap<>(values.length);

    static {
        for(BookFormat bookFormat : values()) {
            bookFormatMap.put(bookFormat.getFormat(), bookFormat);
        }
    }

    public static BookFormat from(String bookFormat) {
        return bookFormatMap.get(bookFormat);
    }

    //#endRegion
}
