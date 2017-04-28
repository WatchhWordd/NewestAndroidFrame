package com.example.newestandroidframe.network.converter;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;


public class DecorationResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Converter<ResponseBody, T> converter;
    private final List<ResponseDecorator> decorators;

    public DecorationResponseBodyConverter(Converter<ResponseBody, T> converter, List<ResponseDecorator> decorators) {
        this.converter = converter;
        this.decorators = decorators;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        T response = converter.convert(value);
        for (ResponseDecorator decorator : decorators) {
            response = decorator.decorate(response);
        }
        return response;
    }
}
