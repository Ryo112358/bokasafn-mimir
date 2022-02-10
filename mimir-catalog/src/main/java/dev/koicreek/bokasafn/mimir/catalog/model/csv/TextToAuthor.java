package dev.koicreek.bokasafn.mimir.catalog.model.csv;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import dev.koicreek.bokasafn.mimir.catalog.model.AuthorCM;


public class TextToAuthor extends AbstractCsvConverter {
    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException {
        return new AuthorCM(Long.parseLong(value));
    }
}
