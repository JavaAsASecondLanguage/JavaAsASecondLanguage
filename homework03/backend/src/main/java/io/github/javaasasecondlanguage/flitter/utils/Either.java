package io.github.javaasasecondlanguage.flitter.utils;

import java.util.Optional;
import java.util.function.Function;

public class Either<T, E> {
  private final T first;
  private final E second;

  private Either(T first, E second) {
    if (first == null && second == null) {
      throw new IllegalArgumentException();
    }
    this.first = first;
    this.second = second;
  }

  public static <T, E> Either<T, E> first(T first) {
    return new Either<>(first, null);
  }

  public static <T, E> Either<T, E> second(E second) {
    return new Either<>(null, second);
  }

  public <U> Either<U, E> mapFirst(Function<T, U> mapper) {
    return map(mapper, Function.identity());
  }

  public <U> Either<T, U> mapSecond(Function<E, U> mapper) {
    return map(Function.identity(), mapper);
  }

  public <U, S> Either<U, S> map(Function<T, U> firstMapper, Function<E, S> secondMapper) {
    if (first != null) {
      return Either.first(firstMapper.apply(first));
    }
    if (second != null) {
      return Either.second(secondMapper.apply(second));
    }
    throw new IllegalStateException();
  }

  public <U, S> Either<U, S> flatMap(Function<T, Either<U, S>> firstMapper, Function<E, Either<U, S>> secondMapper) {
    if (first != null) {
      return firstMapper.apply(first);
    }
    if (second != null) {
      return secondMapper.apply(second);
    }
    throw new IllegalStateException();
  }

  public Optional<T> first() {
    return Optional.ofNullable(first);
  }

  public Optional<E> second() {
    return Optional.ofNullable(second);
  }

  public <R> Optional<R> result(Function<T, R> firstMapper, Function<E, R> secondMapper) {
    if (first != null) {
      return Optional.ofNullable(firstMapper.apply(first));
    }
    if (second != null) {
      return Optional.ofNullable(secondMapper.apply(second));
    }
    return Optional.empty();
  }
}
