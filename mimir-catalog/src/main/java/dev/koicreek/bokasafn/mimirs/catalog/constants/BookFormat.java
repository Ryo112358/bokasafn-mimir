package dev.koicreek.bokasafn.mimirs.catalog.constants;

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

    public static final BookFormat[] VALUES = values();
    private static final Map<String, BookFormat> BOOK_FORMAT_MAP = new HashMap<>(VALUES.length);

    static {
        for(BookFormat bookFormat : VALUES) {
            BOOK_FORMAT_MAP.put(bookFormat.getFormat(), bookFormat);
        }
    }

    public static BookFormat from(String bookFormat) {
        return BOOK_FORMAT_MAP.get(bookFormat);
    }

    //#endRegion
}
