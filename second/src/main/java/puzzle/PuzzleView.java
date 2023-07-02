package puzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PuzzleView implements ActionListener {
  JFrame frame;
  JPanel mainPanel;
  JButton[][] buttonGrid;
  int gridSize;
  int[][] puzzleGrid;
  JLabel[][] labelGrid;
  int emptyRow;
  int emptyCol;

  public PuzzleView(int gridSize) {
    this.gridSize = gridSize;
    this.puzzleGrid = new int[gridSize][gridSize];
    initUI();
    initializePuzzle();
  }

  private void initUI() {
    frame = new JFrame("Puzzle Game");
    mainPanel = new JPanel();
    mainPanel.setBackground(Color.white);
    mainPanel.setLayout(new GridLayout(gridSize, gridSize));
    buttonGrid = new JButton[gridSize][gridSize];
    labelGrid = new JLabel[gridSize][gridSize];

    for (int row = 0; row < gridSize; row++) {
      for (int col = 0; col < gridSize; col++) {
        buttonGrid[row][col] = new JButton();
        buttonGrid[row][col].setFont(new Font("TimesRoman", Font.PLAIN, 0));
        buttonGrid[row][col].addActionListener(this);

        labelGrid[row][col] = new JLabel("", JLabel.CENTER);
        buttonGrid[row][col].add(labelGrid[row][col]);
        buttonGrid[row][col].setBorder(BorderFactory.createLineBorder(Color.black, 2));
        buttonGrid[row][col].setBackground(Color.LIGHT_GRAY);
        mainPanel.add(buttonGrid[row][col]);
      }
    }

    frame.add(mainPanel);
    frame.setSize(500, 500);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  private void initializePuzzle() {
    int count = 1;
    for (int row = 0; row < gridSize; row++) {
      for (int col = 0; col < gridSize; col++) {
        puzzleGrid[row][col] = count;
        count++;
      }
    }
    emptyRow = gridSize - 1;
    emptyCol = gridSize - 1;
    puzzleGrid[emptyRow][emptyCol] = 0;
    updateButtonLabels();
  }

  private void updateButtonLabels() {
    for (int row = 0; row < gridSize; row++) {
      for (int col = 0; col < gridSize; col++) {
        int value = puzzleGrid[row][col];
        if (value == 0) {
          labelGrid[row][col].setIcon(null);
        } else {
          String fileName = "img/" + value + ".jpg";
          ImageIcon icon = new ImageIcon(fileName);
          labelGrid[row][col].setIcon(icon);
        }
      }
    }
  }

  private void movePiece(int row, int col) {
    if (isValidMove(row, col)) {
      puzzleGrid[emptyRow][emptyCol] = puzzleGrid[row][col];
      puzzleGrid[row][col] = 0;
      emptyRow = row;
      emptyCol = col;
      updateButtonLabels();
      if (isSolved()) {
        displayWinMessage();
      }
    }
  }

  private boolean isValidMove(int row, int col) {
    if (row >= 0 && row < gridSize && col >= 0 && col < gridSize) {
      return (Math.abs(row - emptyRow) + Math.abs(col - emptyCol) == 1);
    }
    return false;
  }

  private boolean isSolved() {
    int count = 1;
    for (int row = 0; row < gridSize; row++) {
      for (int col = 0; col < gridSize; col++) {
        if (puzzleGrid[row][col] != count) {
          return false;
        }
        count++;
        if (count == gridSize * gridSize) {
          return true;
        }
      }
    }
    return false;
  }

  private void displayWinMessage() {
    JFrame winFrame = new JFrame("Game Win");
    JLabel label = new JLabel("Congratulations! You solved the puzzle.", JLabel.CENTER);
    label.setFont(new Font("TimesRoman", Font.BOLD, 20));
    winFrame.add(label);
    winFrame.setSize(300, 300);
    winFrame.setBackground(Color.white);
    winFrame.setLocationRelativeTo(null);
    winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    winFrame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton source = (JButton) e.getSource();
    for (int row = 0; row < gridSize; row++) {
      for (int col = 0; col < gridSize; col++) {
        if (source == buttonGrid[row][col]) {
          movePiece(row, col);
          return;
        }
      }
    }
  }

}
