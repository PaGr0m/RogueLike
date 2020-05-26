package ru.itmo.roguelike.utils;

/**
 * Mutable pair of two objects with specified types
 * @param <F> type of first object in Pair
 * @param <S> type of second object in pair
 */
public class Pair<F, S> {
    private F first;
    private S second;

    /**
     * Constructor for Pair
     *
     * @param first  --- coordinate x
     * @param second --- cooredinate y
     */
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public S getSecond() {
        return second;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    @Override
    public int hashCode() {
        return first.hashCode() * 239 + second.hashCode();
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Pair)) return false;
        Pair<F, S> other = (Pair) obj;
        return first.equals(other.first) && second.equals(other.second);
    }
}