package com.everday.lib_base.command;

public interface BindingConsumer<T> {
    void call(T t);
}
