import java.util.ArrayList;

/**
 * Created by el on 10.08.2016.
 */
public class KmeansEasySample {
  // Total number of clusters
  private static final int numberOfClusters = 2;

  // Initial data for future dataSet
  private static final double samples[][] = new double[][]{{1.0, 1.0},
      {1.5, 2.0},
      {3.0, 4.0},
      {5.0, 7.0},
      {3.5, 5.0},
      {4.5, 5.0},
      {3.5, 4.5}};

  public static void main(String[] args) {
    // Will contain centroid values for all clusters
    ArrayList<Centroid> centroids = initialize_centroids(new double[][]{{1.0, 1.0}, {5.0, 7.0}});
    System.out.println("Centroids initialized at:");
    printCentroids(centroids);
    // Will contain data points
    ArrayList<Data> dataSet = addData(centroids);
    recalculateCentroids(dataSet, centroids);
    // Print out clustering results
    System.out.println("Clustering results:");
    printClusters(dataSet);
    System.out.println("Centroids finalized at:");
    printCentroids(centroids);
  }

  protected static ArrayList<Centroid> initialize_centroids(double[][] initialCentroidCoordinates) {
    ArrayList<Centroid> centroids = new ArrayList<Centroid>();
    for (int i = 0; i < initialCentroidCoordinates.length; i++) {
      centroids.add(new Centroid(initialCentroidCoordinates[i][0],
                                 initialCentroidCoordinates[i][1]));
    }
    return centroids;
  }

  private static ArrayList<Data> addData(ArrayList<Centroid> centroids) {
    // Add initial data to dataSet, one at a time, recalculating centroids with each new one
    ArrayList<Data> dataSet = new ArrayList<Data>();
    int sampleNumber = 0;
    while (dataSet.size() < samples.length) {
      Data newPoint = new Data(samples[sampleNumber][0], samples[sampleNumber][1]);
      dataSet.add(newPoint);
      addPointToNearestCluster(newPoint, centroids);
      // calculate new centroids
      calculateCentroids(dataSet, centroids);
      sampleNumber++;
    }
    return dataSet;
  }

  // keep shifting centroids until equilibrium occurs
  protected static ArrayList<Centroid> recalculateCentroids(ArrayList<Data> dataSet,
                                                            ArrayList<Centroid> centroids) {
    boolean condition = true;
    ArrayList<Centroid> newCentroids = null;
    while (condition) {
      // Assign all data to the new centroids
      condition = false;
      for (int i = 0; i < dataSet.size(); i++) {
        Data newPoint = dataSet.get(i);
        int oldNearestCluster = newPoint.getClusterNumber();
        int newNearestCluster = addPointToNearestCluster(newPoint, centroids);
        System.out.println("i=" + i);
        printClusters(dataSet);
        printCentroids(centroids);
        if (oldNearestCluster != newNearestCluster) {
          newCentroids = calculateCentroids(dataSet, centroids);
          condition = true; //continue while loop if any point changes its cluster
        }
      }
    }
    return newCentroids;
  }

  protected static int addPointToNearestCluster(final Data newPoint,
                                                ArrayList<Centroid> centroids) {
    // some big number that's sure to be larger than our data range
    final double bigNumber = Math.pow(10,
                                      10);
    // additional variable to compare distances between newPoint and each cluster
    double minimum;
    // Euclidean distance between point and cluster centroid
    double distance;
    // Number of a nearest cluster
    int nearest_cluster = 0;
    minimum = bigNumber;
    for (int i = 0; i < numberOfClusters; i++) {
      // Calculate distance between current point and current cluster centroid
      distance = calculateEuclideanDistance(newPoint, centroids.get(i));
      if (distance < minimum) {
        minimum = distance;
        nearest_cluster = i;
      }
    }
    // Add newPoint to nearest cluster
    newPoint.setClusterNumber(nearest_cluster);
    return nearest_cluster;
  }

  protected static ArrayList<Centroid> calculateCentroids(ArrayList<Data> dataSet,
                                                          ArrayList<Centroid> centroids) {
    for (int i = 0; i < numberOfClusters; i++) {
      // summ of x coordinate in current cluster
      double totalXSumm = 0;
      // summ of y coordinate in current cluster
      double totalYSumm = 0;
      // number of point in current cluster
      int totalPointsInCluster = 0;
      for (int j = 0; j < dataSet.size(); j++) {
        if (dataSet.get(j).getClusterNumber() == i) {
          totalXSumm += dataSet.get(j).getX();
          totalYSumm += dataSet.get(j).getY();
          totalPointsInCluster++;
        }
      }
      // calcualate new centroid coordinates
      if (totalPointsInCluster > 0) {
        centroids.get(i).setX(totalXSumm / totalPointsInCluster);
        centroids.get(i).setY(totalYSumm / totalPointsInCluster);
      }
    }
    return centroids;
  }

  /**
   * // Calculate Euclidean distance.
   * @param d - Data object.
   * @param c - Centroid object.
   * @return - double value.
   */
  private static double calculateEuclideanDistance(Data d, Centroid c) {
    return Math.sqrt(Math.pow((c.getY() - d.getY()), 2.0) + Math.pow((c.getX() - d.getX()), 2.0));
  }

  private static void printCentroids(ArrayList<Centroid> centroids) {
    for (int i = 0; i < numberOfClusters; i++) {
      System.out.println("Centroid coordinates for cluster "+ i + ":");
      System.out.println("     (" + centroids.get(i).getX() + ", " + centroids.get(i).getY() + ")");
    }
    System.out.print("\n");
  }

  private static void printClusters(ArrayList<Data> dataSet) {
    for (int i = 0; i < numberOfClusters; i++) {
      System.out.println("Cluster " + i + " includes:");
      for (int j = 0; j < dataSet.size(); j++) {
        if (dataSet.get(j).getClusterNumber() == i) {
          System.out.println("     (" + dataSet.get(j).getX() + ", " + dataSet.get(j).getY() + ")");
        }
      } // j
      System.out.println();
    } // i
  }

  protected static class Data {
    private double x = 0;

    private double y = 0;

    private int clusterNumber = 0;

    public Data(double x, double y) {
      this.setX(x);
      this.setY(y);
    }

    public Data(double x, double y, int clusterNumber) {
      this.setX(x);
      this.setY(y);
      this.setClusterNumber(clusterNumber);
    }

    public void setX(double x) {
      this.x = x;
    }

    public void setY(double y) {
      this.y = y;
    }

    public double getX() {
      return this.x;
    }

    public double getY() {
      return this.y;
    }

    public void setClusterNumber(int clusterNumber) {
      this.clusterNumber = clusterNumber;
    }

    public int getClusterNumber() {
      return this.clusterNumber;
    }
  }


  protected static class Centroid {
    private double x = 0.0;

    private double y = 0.0;

    public Centroid(double newX, double newY) {
      this.x = newX;
      this.y = newY;
    }

    public void setX(final double x) {
      this.x = x;
    }

    public void setY(final double y) {
      this.y = y;
    }

    public double getX() {
      return this.x;
    }

    public double getY() {
      return this.y;
    }
  }
}
