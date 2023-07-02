package puzzle.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import java.awt.image.BufferedImage;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PuzzlePiece {

  BufferedImage image;
  int pieceNumber;

}