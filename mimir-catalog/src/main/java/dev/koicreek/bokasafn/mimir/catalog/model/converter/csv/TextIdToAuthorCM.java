package dev.koicreek.bokasafn.mimir.catalog.model.converter.csv;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import dev.koicreek.bokasafn.mimir.catalog.model.AuthorCM;


public class TextIdToAuthorCM extends AbstractCsvConverter {
    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException {
        return new AuthorCM(Long.parseLong(value));
    }
}
