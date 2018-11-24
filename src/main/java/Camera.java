import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Camera {

    public static Camera create(Vector3 origin, Vector3 lowerLeftCorner, Vector3 horizontal, Vector3 vertical) {
        return new AutoValue_Camera(origin, lowerLeftCorner, horizontal, vertical);
    }

    public abstract Vector3 origin();

    public abstract Vector3 lowerLeftCorner();

    public abstract Vector3 horizontal();

    public abstract Vector3 vertical();

    public Ray getRay(double u, double v) {
        return Ray.create(origin(), lowerLeftCorner().add(horizontal().scale(u)).add(vertical().scale(v)).subtract(origin()));
    }

    public static final Camera DEFAULT = Camera.create(
            Vector3.ZERO,
            Vector3.create(-2, -1, -1),
            Vector3.create(4, 0, 0),
            Vector3.create(0, 2, 0)
    );
}
