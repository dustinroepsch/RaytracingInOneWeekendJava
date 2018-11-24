import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static Color color(Ray ray, HittableList world) {
        HitRecord record = world.hit(ray, 0, Double.MAX_VALUE);

        Vector3 cVect = null;

        if (record.hit()) {
            cVect = Vector3.create(record.normal().x() + 1, record.normal().y() + 1, record.normal().z() + 1).scale(.5);

        } else {
            Vector3 unitDirection = ray.direction();
            double t = .5 * (unitDirection.y() + 1);
            cVect = Vector3.create(1 - t, 1 - t, 1 - t).add(Vector3.create(.5, .7, 1).scale(t));
        }

        return new Color((int) (cVect.x() * 255), (int) (cVect.y() * 255), (int) (cVect.z() * 255));

    }

    public static void main(String[] args) throws IOException {

        int ny = 1080;
        int nx = ny * 2;


        Vector3 lowerLeftCorner = Vector3.create(-2, -1, -1);
        Vector3 horizontal = Vector3.create(4, 0, 0);
        Vector3 vertical = Vector3.create(0, 2, 0);
        Vector3 origin = Vector3.create(0, 0, 0);

        BufferedImage image = new BufferedImage(nx, ny, BufferedImage.TYPE_3BYTE_BGR);

        HittableList world = HittableList.of(
                Sphere.create(Vector3.create(0, 0, -1), .5),
                Sphere.create(Vector3.create(0, -100.5, -1), 100)
        );

        for (int row = 0; row < ny; row++) {
            for (int col = 0; col < nx; col++) {
                double u = col / (float) nx;
                double v = row / (float) ny;

                Ray r = Ray.create(origin, lowerLeftCorner.add(horizontal.scale(u)).add(vertical.scale(v)));

                image.setRGB(col, ny - row - 1, color(r, world).getRGB());
            }
        }

        ImageIO.write(image, "png", new File("out.png"));

    }
}
