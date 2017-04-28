package com.example.newestandroidframe.network.converter;

import java.io.IOException;

public interface ResponseDecorator {
    <T> T decorate(T response) throws IOException;
}
