package ru.nikulin.graph;

public interface IStringToCurrentTypeConverter<T> {
    T convert(String source);
}
