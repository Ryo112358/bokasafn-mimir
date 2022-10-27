package dev.koicreek.bokasafn.mimirs.catalog.models.converters.csv.acc;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import dev.koicreek.bokasafn.mimirs.catalog.constants.Language;

public class ToLanguageEnum extends AbstractCsvConverter {
    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        return Language.from(value);
    }
}
