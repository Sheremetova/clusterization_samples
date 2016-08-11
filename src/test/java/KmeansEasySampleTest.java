import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by el on 10.08.2016.
 */
public class KmeansEasySampleTest {

  @Test
  public void shouldAddPointToNearestCluster() throws Exception {
    KmeansEasySample.Data newPoint = new KmeansEasySample.Data(2.0, 3.0);
    ArrayList<KmeansEasySample.Centroid> centroids = getCentroids();
    final int nearest_cluster = KmeansEasySample.addPointToNearestCluster(newPoint, centroids);
    assertEquals(nearest_cluster, 0);
  }

  private ArrayList<KmeansEasySample.Centroid> getCentroids() {
    ArrayList<KmeansEasySample.Centroid> centroids = new ArrayList<KmeansEasySample.Centroid>();
    centroids.add(new KmeansEasySample.Centroid(1.0, 1.0));
    centroids.add(new KmeansEasySample.Centroid(5.0, 7.0));
    return centroids;
  }

  @Test
  public void shouldCalculateCentroids() throws Exception {
    ArrayList<KmeansEasySample.Data> dataSet = generateDataSet();
    ArrayList<KmeansEasySample.Centroid> centroids = KmeansEasySample.initialize_centroids(new double[][]{{1.0,1.0},{5.0,7.0}});
    centroids = KmeansEasySample.calculateCentroids(dataSet, centroids);
    for (int i = 0; i < centroids.size(); i++) {
      assertEquals(this.calculateCentroids(i)[0], centroids.get(i).getX(),0.0);
      assertEquals(this.calculateCentroids(i)[1], centroids.get(i).getY(),0.0);
    }
  }

  private double[] calculateCentroids(int i) {
    double centroids[][] = new double[][]{{2.0, 1.5}, {8.0, 8.0}};
    return centroids[i];
  }

  private ArrayList<KmeansEasySample.Data> generateDataSet() {
    ArrayList<KmeansEasySample.Data> dataSet = new ArrayList<KmeansEasySample.Data>();
    dataSet.add(new KmeansEasySample.Data(1.0, 1.5, 0));
    dataSet.add(new KmeansEasySample.Data(2.0, 2.0, 0));
    dataSet.add(new KmeansEasySample.Data(3.0, 1.0, 0));
    dataSet.add(new KmeansEasySample.Data(5.0, 5.0, 1));
    dataSet.add(new KmeansEasySample.Data(11.0, 11.0, 1));
    return dataSet;
  }
  @Test
  public void shouldRecalculateCentroids() throws Exception {
    ArrayList<KmeansEasySample.Data> dataSet = new ArrayList<KmeansEasySample.Data>();
    dataSet.add(new KmeansEasySample.Data(0.0, 0.0, 0));
    dataSet.add(new KmeansEasySample.Data(9.0, 9.0, 0));
    dataSet.add(new KmeansEasySample.Data(3.0, 3.0, 1));
    dataSet.add(new KmeansEasySample.Data(1.0, 1.0, 1));
    ArrayList<KmeansEasySample.Centroid> centroids = KmeansEasySample.initialize_centroids(new double[][]{{3.0,3.0},{8.0,8.0}});
    centroids = KmeansEasySample.recalculateCentroids(dataSet, centroids);
    for (int i = 0; i < centroids.size(); i++) {
      assertEquals(this.recalculateCentroids(i)[0], centroids.get(i).getX(),0.1);
      assertEquals(this.recalculateCentroids(i)[1], centroids.get(i).getY(),0.1);
    }
  }

  private double[] recalculateCentroids(final int i) {
    double centroids[][] = new double[][]{{1.3, 1.3}, {9.0, 9.0}};
    return centroids[i];
  }
}
