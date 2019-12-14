package com.mw.distribution.wxpay.support;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The type LocalDateTimeXmlAdapter.
 *
 */
public class LocalDateXmlAdapter extends XmlAdapter<String, LocalDate> {
    public static final LocalDateXmlAdapter INSTANCE = new LocalDateXmlAdapter();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public LocalDate unmarshal(final String value) {
        if (null == value) {
            return null;
        }
        else {
            return LocalDate.parse(value, this.dateTimeFormatter);
        }
    }

    @Override
    public String marshal(final LocalDate value) {
        if (null == value) {
            return null;
        }
        else {
            return value.format(this.dateTimeFormatter);
        }
    }
}
