package dev.koicreek.bokasafn.mimir.catalog.models.converters.csv.acc;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import dev.koicreek.bokasafn.mimir.catalog.models.AuthorCM;


public class ToAuthorCM extends AbstractCsvConverter {
    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException {
        return new AuthorCM(Long.parseLong(value));
    }
}
