package com.example.newestandroidframe.network.converter;

import java.util.ArrayList;
import java.util.List;


public class ResponseDecoratorList {
    private final List<ResponseDecorator> list = new ArrayList<>();

    public List<ResponseDecorator> list() {
        List<ResponseDecorator> copy = new ArrayList<>(list.size());
        copy.addAll(list);
        return copy;
    }

    public void add(ResponseDecorator decorator) {
        if (decorator != null) {
            list.add(decorator);
        }
    }

    public void remove(ResponseDecorator decorator) {
        list.remove(decorator);
    }

    public void clear() {
        list.clear();
    }
}
