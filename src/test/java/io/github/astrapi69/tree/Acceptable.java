package io.github.astrapi69.tree;

public interface Acceptable<V> {
    void accept(V visitor);
}
