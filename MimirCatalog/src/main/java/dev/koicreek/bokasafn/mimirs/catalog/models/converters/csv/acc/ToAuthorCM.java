package dev.koicreek.bokasafn.mimirs.catalog.models.converters.csv.acc;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import dev.koicreek.bokasafn.mimirs.catalog.models.AuthorCM;


public class ToAuthorCM extends AbstractCsvConverter {
    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException {
        return new AuthorCM(Long.parseLong(value));
    }
}
