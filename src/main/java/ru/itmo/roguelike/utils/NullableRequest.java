package ru.itmo.roguelike.utils;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class NullableRequest<T> {
    private final @Nullable T result;
    private final boolean exists;

    public NullableRequest() {
        result = null;
        exists = false;
    }

    public NullableRequest(@Nullable T result) {
        this.result = result;
        exists = true;
    }

    public Optional<Optional<T>> toOptional() {
        return exists ? Optional.of(Optional.ofNullable(result)) : Optional.empty();
    }

    public Status getStatus() {
        if (!exists) return Status.FAIL;
        return result == null ? Status.NULL : Status.OK;
    }

    public enum Status {
        FAIL, NULL, OK
    }
}
