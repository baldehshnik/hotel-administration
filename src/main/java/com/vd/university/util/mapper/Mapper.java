package com.vd.university.util.mapper;

public interface Mapper<T, R> {
    R map(T value);
}