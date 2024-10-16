package graphics.transform;

public class Mat3 {
    public Vec3[] m;  // 3x3 matrix represented by three Vec3 rows

    public Mat3() {
        m = new Vec3[3];
        identity();
    }

    public void identity() {
        m[0] = new Vec3(1, 0, 0);
        m[1] = new Vec3(0, 1, 0);
        m[2] = new Vec3(0, 0, 1);
    }

    public Mat3 multiply(Mat3 other) {
        Mat3 result = new Mat3();
        for (int i = 0; i < 3; i++) {
            result.m[i] = new Vec3(
                    m[i].x * other.m[0].x + m[i].y * other.m[1].x + m[i].z * other.m[2].x,
                    m[i].x * other.m[0].y + m[i].y * other.m[1].y + m[i].z * other.m[2].y,
                    m[i].x * other.m[0].z + m[i].y * other.m[1].z + m[i].z * other.m[2].z
            );
        }
        return result;
    }

    public Vec2 transform(Vec2 v) {
        // Convert Vec2 to Vec3 (homogeneous coordinates with z = 1)
        Vec3 v3 = new Vec3(v.x, v.y, 1);
        // Transform using the matrix
        float x = v3.x * m[0].x + v3.y * m[0].y + v3.z * m[0].z;
        float y = v3.x * m[1].x + v3.y * m[1].y + v3.z * m[1].z;
        return new Vec2(x, y);
    }

    public void translate(float tx, float ty) {
        Mat3 t = new Mat3();
        t.m[0].z = tx;
        t.m[1].z = ty;
        this.m = this.multiply(t).m;
    }

    public void scale(float sx, float sy) {
        Mat3 s = new Mat3();
        s.m[0].x = sx;
        s.m[1].y = sy;
        this.m = this.multiply(s).m;
    }

    public void rotate(float angle) {
        Mat3 r = new Mat3();
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        r.m[0].x = cos;
        r.m[0].y = -sin;
        r.m[1].x = sin;
        r.m[1].y = cos;
        this.m = this.multiply(r).m;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append(m[i]).append("\n");
        }
        return sb.toString();
    }
}
