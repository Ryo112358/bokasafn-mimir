package dev.koicreek.bokasafn.mimir.catalog.models.converters.enums;

import dev.koicreek.bokasafn.mimir.catalog.constants.BookFormat;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BookFormatConverter implements AttributeConverter<BookFormat, Integer> {
    @Override
    public Integer convertToDatabaseColumn(BookFormat attribute) {
        return attribute.ordinal();
    }

    @Override
    public BookFormat convertToEntityAttribute(Integer dbData) {
        return BookFormat.values[dbData];
    }
}
