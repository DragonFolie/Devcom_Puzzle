
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import puzzle.util.PuzzlePiece;
import puzzle.util.PuzzleSolver;


public class Main {
  public static void main(String[] args) {

    PuzzleSolver puzzleSolver = new PuzzleSolver();

    BufferedImage originalImage = puzzleSolver.loadImage("img/input/input.jpg"); // Завантаження вихідного зображення
    List<PuzzlePiece> puzzlePieces = puzzleSolver.splitImageIntoPuzzle(originalImage); // Розбиття зображення на пазли

    // Перемішування пазлів у випадковому порядку
    Collections.shuffle(puzzlePieces);

    // Збереження інформації про пазли
    puzzleSolver.savePuzzlePieces(puzzlePieces);

    // Відображення зібраних пазлів
    BufferedImage solvedImage = puzzleSolver.solvePuzzle(puzzlePieces);
    puzzleSolver.saveImage(solvedImage, "img/output/output.jpg");
  }
}
