import com.google.common.base.Stopwatch;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {

    public static Vector3 randomPointInUnitCircle() {
        Vector3 p;

        do {
            p = Vector3.create(Math.random(), Math.random(), Math.random()).scale(2).subtract(Vector3.create(1, 1, 1));
        } while (p.squaredMag() > 1);

        return p;
    }

    public static Vector3 color(Ray ray, HittableList world) {
        HitRecord record = world.hit(ray, 0.001, Double.MAX_VALUE);


        if (record.hit()) {
            Vector3 target = record.p().add(record.normal()).add(randomPointInUnitCircle());
            return color(Ray.create(record.p(), target.subtract(record.p())), world).scale(.5);

        } else {
            Vector3 unitDirection = ray.direction();
            double t = .5 * (unitDirection.y() + 1);
            return Vector3.create(1 - t, 1 - t, 1 - t).add(Vector3.create(.5, .7, 1).scale(t));
        }


    }

    public static void main(String[] args) throws IOException {

        int ny = 1000;
        int nx = ny * 2;
        int numSamples = 200;


        BufferedImage image = new BufferedImage(nx, ny, BufferedImage.TYPE_3BYTE_BGR);

        HittableList world = HittableList.of(
                Sphere.create(Vector3.create(0, 0, -1), .5),
                Sphere.create(Vector3.create(0, -100.5, -1), 100)
        );

        Camera camera = Camera.DEFAULT;

        int computationCount = 0;
        int totalComputations = ny * nx * numSamples;

        Stopwatch stopwatch = Stopwatch.createStarted();

        for (int row = 0; row < ny; row++) {
            for (int col = 0; col < nx; col++) {


                Vector3 samples = Vector3.ZERO;

                for (int i = 0; i < numSamples; i++) {
                    double u = (Math.random() + col) / nx;
                    double v = (Math.random() + row) / ny;

                    Ray r = camera.getRay(u, v);

                    samples = samples.add(color(r, world));
                    computationCount++;
                }

                if (stopwatch.elapsed(TimeUnit.SECONDS) >= 2) {
                    System.out.println("Progress: " + computationCount + "/" + totalComputations + " (" + (100.0 * computationCount) / totalComputations + "%)");
                    stopwatch.reset();
                    stopwatch.start();
                }


                samples = samples.scale(1.0 / numSamples);

                samples = Vector3.create(Math.sqrt(samples.x()), Math.sqrt(samples.y()), Math.sqrt(samples.z()));

                Color c = new Color((int) (samples.x() * 255), (int) (samples.y() * 255), (int) (samples.z() * 255));

                image.setRGB(col, ny - row - 1, c.getRGB());
            }
        }

        ImageIO.write(image, "png", new File("out.png"));

    }
}
