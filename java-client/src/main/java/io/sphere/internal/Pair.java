package io.sphere.internal;

class Pair<A, B> {
    private final A x;
    private final B y;
    public Pair(A x, B y) {
        this.x = x;
        this.y = y;
    }

    public A getX() {
        return x;
    }

    public B getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;

        Pair pair = (Pair) o;

        if (!x.equals(pair.x)) return false;
        if (!y.equals(pair.y)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }
}