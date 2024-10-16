package graphics.transform;

public class Vec2 {
    public float x, y;

    public Vec2() {
        this.x = 0;
        this.y = 0;
    }

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2 add(Vec2 other) {
        return new Vec2(this.x + other.x, this.y + other.y);
    }

    public Vec2 subtract(Vec2 other) {
        return new Vec2(this.x - other.x, this.y - other.y);
    }

    public Vec2 scale(float scalar) {
        return new Vec2(this.x * scalar, this.y * scalar);
    }

    public float dot(Vec2 other) {
        return this.x * other.x + this.y * other.y;
    }

    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vec2 normalize() {
        float len = this.length();
        return new Vec2(this.x / len, this.y / len);
    }

    @Override
    public String toString() {
        return "Vec2(" + x + ", " + y + ")";
    }
}
