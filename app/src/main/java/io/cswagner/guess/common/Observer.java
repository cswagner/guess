package io.cswagner.guess.common;

public interface Observer<T> {

    void next(T item);
}
