package graphics.camera;
import graphics.transform.Vec2;
import org.junit.jupiter.api.*;

public class CameraTest {
	Camera camera;
    /**Setup camera**/
    @BeforeEach
    public void setUp() {
        camera = new Camera();
    }

    /**Test camera screenToWorld**/
    @Test
    void testScreenToWorld() {
        Vec2 screen = new Vec2(1, 2);
        Vec2 result = camera.screenToWorld(screen);
        Assertions.assertEquals(250, result.x);
        Assertions.assertEquals(500, result.y);
    }

    /**Test camera V transformation**/
    @Test
    void testV() {
        Assertions.assertEquals(-200, camera.V().m[3].x);
        Assertions.assertEquals(-200, camera.V().m[3].y);
    }

    /**Test camera P transformation**/
    @Test
    void testP() {        
        Assertions.assertEquals(0.0078125, camera.P().m[0].x, 0.0001);
        Assertions.assertEquals(0.0078125, camera.P().m[1].y, 0.0001);
    }

    /**Test camera V inverse transformation**/
    @Test
    void testVinv() {
        Assertions.assertEquals(200, camera.Vinv().m[3].x);
        Assertions.assertEquals(200, camera.Vinv().m[3].y);
    }

    /**Test camera P inverse transformation**/
    @Test
    void testPinv() {
        Assertions.assertEquals(250, camera.Pinv().m[0].x, 0.0001);
        Assertions.assertEquals(250, camera.Pinv().m[1].y, 0.0001);
    }


}
