package com.webmuffins.rtsx.messenger.converter;

public interface Converter<SOURCE, TARGET> {
    TARGET convert(SOURCE source);
}
