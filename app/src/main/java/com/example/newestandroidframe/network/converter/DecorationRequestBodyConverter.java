package com.example.newestandroidframe.network.converter;

import java.io.IOException;

import okhttp3.RequestBody;
import retrofit2.Converter;

public class DecorationRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private final Converter<T, RequestBody> converter;

    public DecorationRequestBodyConverter(Converter<T, RequestBody> converter) {
        this.converter = converter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        return converter.convert(value);
    }
}
