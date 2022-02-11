package dev.koicreek.bokasafn.mimir.catalog.model.converter;

import dev.koicreek.bokasafn.mimir.catalog.constants.LanguageCode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LanguageCodeConverter implements AttributeConverter<LanguageCode, String> {

    @Override
    public String convertToDatabaseColumn(LanguageCode attribute) {
        return attribute.getIsoCode639_3();
    }

    @Override
    public LanguageCode convertToEntityAttribute(String dbData) {
        return LanguageCode.from(dbData);
    }
}
