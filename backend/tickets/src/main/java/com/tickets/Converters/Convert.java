package com.tickets.Converters;

public interface Convert<T,V> {
    public T convert(V obj);
}
