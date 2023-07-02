package puzzle.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PuzzleSolver {
  private static final int PUZZLE_SIZE = 4; // Розмір пазлу (кількість рядків або стовпців)


  public BufferedImage loadImage(String imagePath) {
    try {
      return ImageIO.read(new File(imagePath));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void saveImage(BufferedImage image, String outputPath) {
    try {
      File outputFile = new File(outputPath);
      ImageIO.write(image, "jpg", outputFile);
      System.out.println("Зображення збережено у файл: " + outputPath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<PuzzlePiece> splitImageIntoPuzzle(BufferedImage image) {
    int pieceWidth = image.getWidth() / PUZZLE_SIZE;
    int pieceHeight = image.getHeight() / PUZZLE_SIZE;
    List<PuzzlePiece> puzzlePieces = new ArrayList<>();

    for (int row = 0; row < PUZZLE_SIZE; row++) {
      for (int col = 0; col < PUZZLE_SIZE; col++) {
        BufferedImage pieceImage = image.getSubimage(col * pieceWidth, row * pieceHeight, pieceWidth, pieceHeight);
        PuzzlePiece puzzlePiece = new PuzzlePiece(pieceImage, row * PUZZLE_SIZE + col);
        puzzlePieces.add(puzzlePiece);
      }
    }

    return puzzlePieces;
  }

  public void savePuzzlePieces(List<PuzzlePiece> puzzlePieces) {
    // Збереження інформації про пазли (можна використовувати будь-який зручний спосіб, наприклад, зберігати в файл або базу даних)
    for (PuzzlePiece puzzlePiece : puzzlePieces) {
      // Збереження пазлу puzzlePiece
    }
  }

  public BufferedImage solvePuzzle(List<PuzzlePiece> puzzlePieces) {
    int puzzleWidth = puzzlePieces.get(0).getImage().getWidth() * PUZZLE_SIZE;
    int puzzleHeight = puzzlePieces.get(0).getImage().getHeight() * PUZZLE_SIZE;
    BufferedImage solvedImage = new BufferedImage(puzzleWidth, puzzleHeight, BufferedImage.TYPE_INT_RGB);

    for (int row = 0; row < PUZZLE_SIZE; row++) {
      for (int col = 0; col < PUZZLE_SIZE; col++) {
        PuzzlePiece puzzlePiece = puzzlePieces.get(row * PUZZLE_SIZE + col);
        solvedImage.createGraphics().drawImage(puzzlePiece.getImage(), col * puzzlePiece.getImage().getWidth(), row * puzzlePiece.getImage().getHeight(), null);
      }
    }

    return solvedImage;
  }
}


