package com.spg.applicationTask.extension;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Validator<T> {

    private final T object;

    private final Collection<Throwable> exceptions = new LinkedList<>();

    private Validator(final T object) {
        this.object = object;
    }

    public static <T> Validator<T> of(final T object) {
        return new Validator<>(Objects.requireNonNull(object));
    }

    public Validator<T> validate(final Predicate<T> validation, final String message) {
        if (!validation.test(object)) {
            exceptions.add(new IllegalStateException(message));
        }

        return this;
    }

    public <U> Validator<T> validate(final Function<T, U> projection, final Predicate<U> validation, final String message) {
        return validate(projection.andThen(validation::test)::apply, message);
    }

    public T get() throws IllegalStateException {
        if (exceptions.isEmpty()) {
            return object;
        }

        final IllegalStateException exception = new IllegalStateException();
        exceptions.forEach(exception::addSuppressed);
        throw exception;
    }
}
