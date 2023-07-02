package puzzle;



import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PuzzleSolver {

  private static final int NUM_PIECES_X = 4;
  private static final int NUM_PIECES_Y = 4;
  private static final String INPUT_FOLDER = "img/input/";
  private static final String OUTPUT_FOLDER = "img/output/";


  public void solvePuzzle() {
    BufferedImage[] puzzlePieces = loadPuzzlePieces();
    BufferedImage[][] puzzleGrid = createPuzzleGrid(puzzlePieces);
    BufferedImage solvedPuzzle = solvePuzzle(puzzleGrid);
    saveImage(solvedPuzzle, OUTPUT_FOLDER + "solved_puzzle.jpg");
    System.out.println("Puzzle solved and saved successfully!");
  }


  private BufferedImage solvePuzzle(BufferedImage[][] puzzleGrid) {
    int puzzleWidth = puzzleGrid[0][0].getWidth() * NUM_PIECES_X;
    int puzzleHeight = puzzleGrid[0][0].getHeight() * NUM_PIECES_Y;

    BufferedImage solvedPuzzle = new BufferedImage(puzzleWidth, puzzleHeight, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics = solvedPuzzle.createGraphics();

    for (int i = 0; i < NUM_PIECES_X; i++) {
      for (int j = 0; j < NUM_PIECES_Y; j++) {
        BufferedImage puzzlePiece = puzzleGrid[i][j];
        graphics.drawImage(puzzlePiece, i * puzzlePiece.getWidth(), j * puzzlePiece.getHeight(), null);
      }
    }

    graphics.dispose();
    return solvedPuzzle;
  }


  private BufferedImage[] loadPuzzlePieces() {
    File inputFolder = new File(INPUT_FOLDER);
    File[] imageFiles = inputFolder.listFiles((dir, name) -> name.endsWith(".jpg"));

    BufferedImage[] puzzlePieces = new BufferedImage[NUM_PIECES_X * NUM_PIECES_Y];
    for (int i = 0; i < NUM_PIECES_X * NUM_PIECES_Y; i++) {
      try {
        puzzlePieces[i] = ImageIO.read(imageFiles[i]);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return puzzlePieces;
  }


  private BufferedImage[][] createPuzzleGrid(BufferedImage[] puzzlePieces) {
    BufferedImage[][] puzzleGrid = new BufferedImage[NUM_PIECES_X][NUM_PIECES_Y];

    for (int i = 0; i < NUM_PIECES_X; i++) {
      for (int j = 0; j < NUM_PIECES_Y; j++) {
        puzzleGrid[i][j] = puzzlePieces[j * NUM_PIECES_X + i];
      }
    }
    return puzzleGrid;
  }


  private void saveImage(BufferedImage image, String outputPath) {
    File outputFile = new File(outputPath);
    try {
      ImageIO.write(image, "jpg", outputFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
