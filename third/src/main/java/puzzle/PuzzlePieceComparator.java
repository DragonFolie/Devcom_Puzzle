package puzzle;

import java.awt.image.BufferedImage;

public class PuzzlePieceComparator {
  private static final int NUM_BINS = 256;

  public static double comparePieces(BufferedImage piece1, BufferedImage piece2) {
    int[] histogram1 = calculateHistogram(piece1);
    int[] histogram2 = calculateHistogram(piece2);

    double similarity = calculateHistogramSimilarity(histogram1, histogram2);

    return similarity;
  }

  private static int[] calculateHistogram(BufferedImage image) {
    int[] histogram = new int[NUM_BINS];
    int width = image.getWidth();
    int height = image.getHeight();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = image.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        int bin = (int) (0.299 * r + 0.587 * g + 0.114 * b);
        histogram[bin]++;
      }
    }

    return histogram;
  }

  private static double calculateHistogramSimilarity(int[] histogram1, int[] histogram2) {
    double sumSquaredDifference = 0;
    double sumHistogram1 = 0;
    double sumHistogram2 = 0;

    for (int i = 0; i < NUM_BINS; i++) {
      sumSquaredDifference += Math.pow(histogram1[i] - histogram2[i], 2);
      sumHistogram1 += histogram1[i];
      sumHistogram2 += histogram2[i];
    }

    double rmse = Math.sqrt(sumSquaredDifference / NUM_BINS);
    double similarity = 1.0 / (1.0 + rmse);

    return similarity;
  }
}
