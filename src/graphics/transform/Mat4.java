package graphics.transform;

public class Mat4 {
    //Vec4 array with 4 elements
    public Vec4[] m = new Vec4[4];

    public Mat4() {
        identity();
    }
    public Mat4(Vec4[] row)
    {
        this.m[0] = row[0];
        this.m[1] = row[1];
        this.m[2] = row[2];
        this.m[3] = row[3];
    }

    public  Mat4(Vec4 r0, Vec4 r1, Vec4 r2, Vec4 r3) {
        this.m[0] = r0;
        this.m[1] = r1;
        this.m[2] = r2;
        this.m[3] = r3;

    }

    public void identity() {
        m[0] = new Vec4(1, 0, 0, 0);
        m[1] = new Vec4(0, 1, 0, 0);
        m[2] = new Vec4(0, 0, 1, 0);
        m[3] = new Vec4(0, 0, 0,1);
    }

    public Mat4 multiply(Mat4 other) {
        Mat4 result = new Mat4();
        for (int i = 0; i < 4; i++) {
            result.m[i] = new Vec4(
                    m[i].x * other.m[0].x + m[i].y * other.m[1].x + m[i].z * other.m[2].x + m[i].w * other.m[3].x,
                    m[i].x * other.m[0].y + m[i].y * other.m[1].y + m[i].z * other.m[2].y + m[i].w * other.m[3].y,
                    m[i].x * other.m[0].z + m[i].y * other.m[1].z + m[i].z * other.m[2].z + m[i].w * other.m[3].z,
                    m[i].x * other.m[0].w + m[i].y * other.m[1].w + m[i].z * other.m[2].w + m[i].w * other.m[3].w
            );
        }
        return result;
    }

    //transform
    public Vec3 transform(Vec3 other) {
        return new Vec3(
                m[0].x * other.x + m[0].y * other.y + m[0].z * other.z,
                m[1].x * other.x + m[1].y * other.y + m[1].z * other.z,
                m[2].x * other.x + m[2].y * other.y + m[2].z * other.z
        );
    }

    public Mat4 translate(float tx, float ty) {
        return new Mat4(
                new Vec4(1, 0, 0, 0),
                new Vec4(0, 1, 0, 0),
                new Vec4(0, 0, 1, 0),
                new Vec4(tx, ty, 0, 1)
        );
    }

    public Mat4 scale(float sx, float sy) {
        return new Mat4(
                new Vec4(sx, 0, 0, 0),
                new Vec4(0, sy, 0, 0),
                new Vec4(0, 0, 1, 0),
                new Vec4(0, 0, 0, 1)
        );
    }

    public Mat4 rotate(float angle) {
    	angle = (float) Math.toRadians(angle);
        return new Mat4(
                new Vec4((float) Math.cos(angle), (float) Math.sin(angle), 0, 0),
                new Vec4((float) -Math.sin(angle), (float) Math.cos(angle), 0, 0),
                new Vec4(0, 0, 1, 0),
                new Vec4(0, 0, 0, 1)
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(m[i]).append(" ");
        }
        return sb.toString();
    }
}
