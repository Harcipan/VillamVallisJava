package graphics.transform;
import org.junit.jupiter.api.*;

/**Test mat4, vec3, vec4, vec2**/
public class TransformTest {

    /**Test vec2 default constructor**/
    @Test
    void vec2DefaultConstructor() {
        Vec2 vec = new Vec2();
        Assertions.assertEquals(0, vec.x);
        Assertions.assertEquals(0, vec.y);
    }

    /**Test vec2 addition**/
    @Test
    void vec2Addition() {
        Vec2 vec1 = new Vec2(1, 2);
        Vec2 vec2 = new Vec2(3, 4);
        Vec2 result = vec1.add(vec2);
        Assertions.assertEquals(4, result.x);
        Assertions.assertEquals(6, result.y);
    }

    /**Test vec2 subtraction**/
    @Test
    void vec2Subtraction() {
        Vec2 vec1 = new Vec2(1, 2);
        Vec2 vec2 = new Vec2(3, 4);
        Vec2 result = vec1.subtract(vec2);
        Assertions.assertEquals(-2, result.x);
        Assertions.assertEquals(-2, result.y);
    }

    /**Test vec2 scaling**/
    @Test
    void vec2Scaling() {
        Vec2 vec = new Vec2(1, 2);
        Vec2 result = vec.scale(2);
        Assertions.assertEquals(2, result.x);
        Assertions.assertEquals(4, result.y);
    }

    /**Test vec2 dot product**/
    @Test
    void vec2DotProduct() {
        Vec2 vec1 = new Vec2(1, 2);
        Vec2 vec2 = new Vec2(3, 4);
        float result = vec1.dot(vec2);
        Assertions.assertEquals(11.0f, result);
    }

    /** Test vec2 normalization**/
    @Test
    void vec2Normalization() {
        Vec2 vec = new Vec2(1, 2);
        Vec2 normalized = vec.normalize();
        Assertions.assertEquals(1.0f, normalized.length(), 0.0001f);
    }

    /**Test vec2 toString**/
    @Test
    void vec2ToString() {
        Vec2 vec = new Vec2(1, 2);
        Assertions.assertEquals("Vec2(1.0, 2.0)", vec.toString());
    }

    /**Test vec3 default constructor**/
    @Test
    void vec3DefaultConstructor() {
        Vec3 vec = new Vec3();
        Assertions.assertEquals(0, vec.x);
        Assertions.assertEquals(0, vec.y);
        Assertions.assertEquals(0, vec.z);
    }

    /**Test vec3 addition**/
    @Test
    void vec3Addition() {
        Vec3 vec1 = new Vec3(1, 2, 3);
        Vec3 vec2 = new Vec3(4, 5, 6);
        Vec3 result = vec1.add(vec2);
        Assertions.assertEquals(5, result.x);
        Assertions.assertEquals(7, result.y);
        Assertions.assertEquals(9, result.z);
    }

    /**Test vec3 subtraction**/
    @Test
    void vec3Subtraction() {
        Vec3 vec1 = new Vec3(1, 2, 3);
        Vec3 vec2 = new Vec3(4, 5, 6);
        Vec3 result = vec1.subtract(vec2);
        Assertions.assertEquals(-3, result.x);
        Assertions.assertEquals(-3, result.y);
        Assertions.assertEquals(-3, result.z);
    }

    /**Test vec3 scaling**/
    @Test
    void vec3Scaling() {
        Vec3 vec = new Vec3(1, 2, 3);
        Vec3 result = vec.scale(2);
        Assertions.assertEquals(2, result.x);
        Assertions.assertEquals(4, result.y);
        Assertions.assertEquals(6, result.z);
    }

    /**Test vec3 dot product**/
    @Test
    void vec3DotProduct() {
        Vec3 vec1 = new Vec3(1, 2, 3);
        Vec3 vec2 = new Vec3(4, 5, 6);
        float result = vec1.dot(vec2);
        Assertions.assertEquals(32.0f, result);
    }

    /**Test vec3 cross product**/
    @Test
    void vec3CrossProduct() {
        Vec3 vec1 = new Vec3(1, 0, 0);
        Vec3 vec2 = new Vec3(0, 1, 0);
        Vec3 result = vec1.cross(vec2);
        Assertions.assertEquals(0, result.x);
        Assertions.assertEquals(0, result.y);
        Assertions.assertEquals(1, result.z);
    }

    /**Test vec3 normalization**/
    @Test
    void vec3Normalization() {
        Vec3 vec = new Vec3(1, 2, 3);
        Vec3 normalized = vec.normalize();
        Assertions.assertEquals(1.0f, normalized.length(), 0.0001f);
    }

    /**Test vec3 toString**/
    @Test
    void vec3ToString() {
        Vec3 vec = new Vec3(1, 2, 3);
        Assertions.assertEquals("Vec3(1.0, 2.0, 3.0)", vec.toString());
    }

    /**Test vec4 default constructor**/
    @Test
    void vec4DefaultConstructor() {
        Vec4 vec = new Vec4();
        Assertions.assertEquals(0, vec.x);
        Assertions.assertEquals(0, vec.y);
        Assertions.assertEquals(0, vec.z);
        Assertions.assertEquals(0, vec.w);
    }

    /**Test vec4 addition**/
    @Test
    void vec4Addition() {
        Vec4 vec1 = new Vec4(1, 2, 3, 4);
        Vec4 vec2 = new Vec4(5, 6, 7, 8);
        Vec4 result = vec1.add(vec2);
        Assertions.assertEquals(6, result.x);
        Assertions.assertEquals(8, result.y);
        Assertions.assertEquals(10, result.z);
        Assertions.assertEquals(12, result.w);
    }

    /**Test vec4 subtraction**/
    @Test
    void vec4Subtraction() {
        Vec4 vec1 = new Vec4(1, 2, 3, 4);
        Vec4 vec2 = new Vec4(5, 6, 7, 8);
        Vec4 result = vec1.subtract(vec2);
        Assertions.assertEquals(-4, result.x);
        Assertions.assertEquals(-4, result.y);
        Assertions.assertEquals(-4, result.z);
        Assertions.assertEquals(-4, result.w);
    }

    /**Test vec4 scaling**/
    @Test
    void vec4Scaling() {
        Vec4 vec = new Vec4(1, 2, 3, 4);
        Vec4 result = vec.scale(2);
        Assertions.assertEquals(2, result.x);
        Assertions.assertEquals(4, result.y);
        Assertions.assertEquals(6, result.z);
        Assertions.assertEquals(8, result.w);
    }

    /**Test vec4 dot product**/
    @Test
    void vec4DotProduct() {
        Vec4 vec1 = new Vec4(1, 2, 3, 4);
        Vec4 vec2 = new Vec4(5, 6, 7, 8);
        float result = vec1.dot(vec2);
        Assertions.assertEquals(70.0f, result);
    }

    /**Test vec4 normalization**/
    @Test
    void vec4Normalization() {
        Vec4 vec = new Vec4(1, 2, 3, 4);
        Vec4 normalized = vec.normalize();
        Assertions.assertEquals(1.0f, normalized.length(), 0.0001f);
    }

    /**Test vec4 toString**/
    @Test
    void vec4ToString() {
        Vec4 vec = new Vec4(1, 2, 3, 4);
        Assertions.assertEquals("Vec4(1.0, 2.0, 3.0, 4.0)", vec.toString());
    }

    /**Test mat4 identity**/
    @Test
    void mat4Identity() {
        Mat4 mat = new Mat4();
        Assertions.assertEquals(1, mat.m[0].x);
        Assertions.assertEquals(1, mat.m[1].y);
        Assertions.assertEquals(1, mat.m[2].z);
    }

    /**Test mat4 translation**/
    @Test
    void mat4Translation() {
        Mat4 mat = new Mat4();
        Mat4 result = mat.translate(1, 2);
        Assertions.assertEquals(1, result.m[3].x);
        Assertions.assertEquals(2, result.m[3].y);
    }

    /**Test mat4 scaling**/
    @Test
    void mat4Scaling() {
        Mat4 mat = new Mat4();
        Mat4 result = mat.scale(2, 3);
        Assertions.assertEquals(2, result.m[0].x);
        Assertions.assertEquals(3, result.m[1].y);
    }

    /**Test mat4 transformation**/
    @Test
    void mat4Transformation() {
        Mat4 mat = new Mat4();
        Vec3 vec = new Vec3(1, 2, 3);
        Vec3 result = mat.transform(vec);
        Assertions.assertEquals(1, result.x);
        Assertions.assertEquals(2, result.y);
        Assertions.assertEquals(3, result.z);
    }

    @Test
    void mat4Multiplication() {
        Mat4 mat1 = new Mat4(new Vec4[]{
                new Vec4(1, 0, 0, 0),
                new Vec4(0, 1, 0, 0),
                new Vec4(0, 0, 1, 0),
                new Vec4(0, 0, 0, 1)
        });
        Mat4 mat2 = new Mat4(new Vec4[]{
                new Vec4(1, 0, 0, 0),
                new Vec4(3, 3, 3, 3),
                new Vec4(2, 2, 2, 2),
                new Vec4(0, 0, 0, 1)
        });
        Mat4 result = mat1.multiply(mat2);

        Assertions.assertEquals(1, result.m[0].x);
        Assertions.assertEquals(3, result.m[1].y);
        Assertions.assertEquals(2, result.m[2].z);
        Assertions.assertEquals(1, result.m[3].w);
    }

    /**Test Mat4 rotate**/
    @Test
    void mat4Rotate() {
        Mat4 mat = new Mat4();
        Mat4 result = mat.rotate(90);
        
        System.out.println(result.toString());
        Assertions.assertEquals(0, result.m[0].x,0.001);
        Assertions.assertEquals(-1.0, result.m[1].x,0.001);
        Assertions.assertEquals(1.0, result.m[0].y,0.001);
        Assertions.assertEquals(0, result.m[1].y,0.001);
    }

    /**Test Mat4 toString**/
    @Test
    void mat4ToString() {
        Mat4 mat = new Mat4();
        Assertions.assertEquals("Vec4(1.0, 0.0, 0.0, 0.0) Vec4(0.0, 1.0, 0.0, 0.0) Vec4(0.0, 0.0, 1.0, 0.0) Vec4(0.0, 0.0, 0.0, 1.0) ", mat.toString());
    }
}
