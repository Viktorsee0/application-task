package com.spg.applicationTask.engine.extension;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Class representing Monad design pattern. Monad is a way of chaining operations on the given
 * object together step by step. In Validator each step results in either success or failure
 * indicator, giving a way of receiving each of them easily and finally getting validated object or
 * list of exceptions.
 *
 * @param <T> Placeholder for an object.
 */
public final class Validator<T> {

    /**
     * Object that is validated.
     */
    private final T object;

    /**
     * List of exception thrown during validation.
     */
    private final Collection<Throwable> exceptions = new LinkedList<>();

    /**
     * Creates a monadic value of given object.
     *
     * @param object object to be validated
     */
    private Validator(final T object) {
        this.object = object;
    }

    /**
     * Creates validator against given object.
     *
     * @param object object to be validated
     * @param <T>    object's type
     * @return new instance of a validator
     */
    public static <T> Validator<T> of(final T object) {
        return new Validator<>(Objects.requireNonNull(object));
    }

    /**
     * Checks if the validation is successful.
     *
     * @param validation one argument boolean-valued function that represents one step of validation.
     *                   Adds exception to main validation exception list when single step validation
     *                   ends with failure.
     * @param message    error message when object is invalid
     * @return this
     */
    public Validator<T> validate(final Predicate<T> validation, final String message) {
        if (!validation.test(object)) {
            exceptions.add(new IllegalStateException(message));
        }
        return this;
    }

    /**
     * Receives validated object or throws exception when invalid.
     *
     * @return object that was validated
     * @throws IllegalStateException when any validation step results with failure
     */
    public T get() throws IllegalStateException {
        if (exceptions.isEmpty()) {
            return object;
        }
        final IllegalStateException exception = new IllegalStateException();
        exceptions.forEach(exception::addSuppressed);
        throw exception;
    }
}
