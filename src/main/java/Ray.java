import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Ray {
    public static Ray create(Vector3 origin, Vector3 direction) {
        return new AutoValue_Ray(origin, direction);
    }

    public abstract Vector3 origin();

    public abstract Vector3 direction();

    public Vector3 pointAtParemeter(double t) {
        return origin().add(direction().scale(t));
    }

}
