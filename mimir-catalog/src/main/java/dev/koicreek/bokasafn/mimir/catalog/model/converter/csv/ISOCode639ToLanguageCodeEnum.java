package dev.koicreek.bokasafn.mimir.catalog.model.converter.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import dev.koicreek.bokasafn.mimir.catalog.constant.LanguageCode;

public class ISOCode639ToLanguageCodeEnum extends AbstractBeanField<String,LanguageCode> {

    @Override
    protected LanguageCode convert(String value) throws CsvDataTypeMismatchException {
        return LanguageCode.from(value);
    }
}
