package com.example.demo.core.util;

import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.validation.constraints.NotNull;

public class SpecificationUtil {
    public static <T> Join<T, ?> getJoin(@NotNull From<?, T> from, @NotNull String alias) {
        return from.getJoins().stream()
                .filter(join -> alias.equals(join.getAlias()))
                .findFirst().orElseThrow();
    }
}
