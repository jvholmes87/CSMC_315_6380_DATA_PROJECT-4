// Name: Jason Holmes
// Project: CMSC 315 - Project 4
// Date: 7/9/2025
// Description: This class defines an immutable vertex with a name and (x, y) coordinates.

public class Vertex {
    private final int x;
    private final int y;
    private final String name;

    public Vertex(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    // Equals and hashCode are critical if using Vertex as map keys
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vertex)) return false;
        Vertex other = (Vertex) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
