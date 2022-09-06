package ua.goit.hw4.service.conventer;

public interface Converter<E, T> {
    E from(T entity);

    T to(E entity);
}
