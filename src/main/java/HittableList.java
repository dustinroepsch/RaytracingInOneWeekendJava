import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class HittableList extends ArrayList<Hittable> implements Hittable {

    public static HittableList  of(Hittable... hittables) {
        HittableList list = new HittableList();
        list.addAll(Arrays.asList(hittables));
        return list;
    }

    public HitRecord hit(Ray ray, final double tMin, final double tMax) {
        return this.stream().map(
                hittable -> hittable.hit(ray, tMin, tMax)
        ).filter(HitRecord::hit)
                .min(Comparator.comparingDouble(HitRecord::t)).orElse(HitRecord.MISS);
    }
}
