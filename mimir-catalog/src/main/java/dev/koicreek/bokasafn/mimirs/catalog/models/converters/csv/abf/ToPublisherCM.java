package dev.koicreek.bokasafn.mimirs.catalog.models.converters.csv.abf;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import dev.koicreek.bokasafn.mimirs.catalog.models.PublisherCM;

public class ToPublisherCM extends AbstractBeanField<PublisherCM, Long> {
    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        return new PublisherCM(Long.parseLong(value));
    }
}
