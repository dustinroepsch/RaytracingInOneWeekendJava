interface Hittable {
    HitRecord hit(Ray ray, double tMin, double tMax);
}
