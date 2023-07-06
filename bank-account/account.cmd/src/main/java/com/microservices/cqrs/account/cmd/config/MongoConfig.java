package com.microservices.cqrs.account.cmd.config;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
public class MongoConfig {

  @Bean
  public MongoCustomConversions mongoCustomConversions() {
    List<Converter<?, ?>> converters = new ArrayList<>();
    converters.add(ZonedDateTimeToDate.INSTANCE);
    converters.add(DateToZonedDateTime.INSTANCE);
    return new MongoCustomConversions(converters);
  }

  @ReadingConverter
  enum DateToZonedDateTime implements Converter<Date, ZonedDateTime> {
    INSTANCE;

    @Override
    public ZonedDateTime convert(Date date) {
      return date.toInstant().atZone(ZoneOffset.UTC);
    }
  }

  @WritingConverter
  enum ZonedDateTimeToDate implements Converter<ZonedDateTime, Date> {
    INSTANCE;

    @Override
    public Date convert(ZonedDateTime zonedDateTime) {
      return Date.from(zonedDateTime.toInstant());
    }
  }
}
