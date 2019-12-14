package com.mw.distribution.wxpay.support;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type LocalDateTimeXmlAdapter.
 *
 */
public class LocalDateTimeXmlAdapter extends XmlAdapter<String, LocalDateTime> {
    public static final LocalDateTimeXmlAdapter INSTANCE = new LocalDateTimeXmlAdapter();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Override
    public LocalDateTime unmarshal(final String value) {
        if (null == value) {
            return null;
        }
        else {
            return LocalDateTime.parse(value, this.dateTimeFormatter);
        }
    }

    @Override
    public String marshal(final LocalDateTime value) {
        if (null == value) {
            return null;
        }
        else {
            return value.format(this.dateTimeFormatter);
        }
    }
}
