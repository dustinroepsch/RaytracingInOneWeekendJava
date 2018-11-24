import com.google.auto.value.AutoValue;

@AutoValue
public abstract class HitRecord {
    public static HitRecord create(boolean hit, double t, Vector3 p, Vector3 normal) {
        return new AutoValue_HitRecord(hit, t, p, normal);
    }

    public abstract boolean hit();

    public abstract double t();

    public abstract Vector3 p();

    public abstract Vector3 normal();

    public static final HitRecord MISS = HitRecord.create(false, 0, Vector3.ZERO, Vector3.ZERO);
}
