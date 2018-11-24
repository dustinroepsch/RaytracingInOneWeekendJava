import com.google.auto.value.AutoValue;


@AutoValue
public abstract class Vector3 {
    public static Vector3 create(double x, double y, double z) {
        return new AutoValue_Vector3(x, y, z);
    }

    public abstract double x();

    public abstract double y();

    public abstract double z();

    public Vector3 scale(double s) {
        return Vector3.create(x() * s, y() * s, z() * s);
    }

    public Vector3 add(Vector3 other) {
        return Vector3.create(this.x() + other.x(), this.y() + other.y(), this.z() + other.z());
    }

    public double mag() {
        return Math.sqrt(x() * x() + y() * y() + z() * z());
    }

    public Vector3 unit() {
        return this.scale(1 / mag());
    }

    public Vector3 subtract(Vector3 other) {
        return Vector3.create(x() - other.x(), y() - other.y(), z() - other.z());
    }

    public double dot(Vector3 other) {
        return this.x() * other.x() + this.y() * other.y() + this.z() * other.z();
    }

    public static final Vector3 ZERO = Vector3.create(0, 0, 0);

    public double squaredMag() {
        return x() * x() + y() * y() + z() * z();
    }
}
