package com.example.newestandroidframe.network.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


public class DecorationConverterFactory extends Converter.Factory {
    private final Converter.Factory factory;
    private final List<ResponseDecorator> decorators;

    public DecorationConverterFactory(Converter.Factory factory, List<ResponseDecorator> decorators) {
        this.factory = factory;
        this.decorators = decorators;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type,
                                                            Annotation[] annotations,
                                                            Retrofit retrofit) {
        Converter<ResponseBody, ?> converter = factory.responseBodyConverter(type, annotations, retrofit);
        return new DecorationResponseBodyConverter<>(converter, decorators);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations,
                                                          Retrofit retrofit) {
        Converter<?, RequestBody> converter = factory.requestBodyConverter(type, parameterAnnotations,
            methodAnnotations, retrofit);
        return new DecorationRequestBodyConverter<>(converter);
    }
}
