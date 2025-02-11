package com.onlinestore.backend.Converters;

public interface Convert<T,V> {
    public T convert(V obj);
}
