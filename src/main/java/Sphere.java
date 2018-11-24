import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Sphere implements Hittable {

    public static Sphere create(Vector3 center, double radius) {
        return new AutoValue_Sphere(center, radius);
    }

    public abstract Vector3 center();

    public abstract double radius();

    public HitRecord hit(Ray ray, double tMin, double tMax) {
        Vector3 oc = ray.origin().subtract(center());

        double a = ray.direction().dot(ray.direction());
        double b = oc.dot(ray.direction());
        double c = oc.dot(oc) - radius() * radius();
        double desc = b * b - a * c;

        if (desc > 0) {
            double temp = (-b - Math.sqrt(b * b - a * c)) / a;
            if (temp < tMax && temp > tMin) {
                Vector3 hitPoint = ray.pointAtParemeter(temp);
                return HitRecord.create(true, temp, hitPoint, (hitPoint.subtract(center()).scale(1 / radius())));
            }
            temp = (-b + Math.sqrt(b * b - a * c)) / a;
            if (temp < tMax && temp > tMin) {
                Vector3 hitPoint = ray.pointAtParemeter(temp);
                return HitRecord.create(true, temp, hitPoint, (hitPoint.subtract(center()).scale(1 / radius())));
            }
        }
        return HitRecord.MISS;
    }
}
