package com.comp301.a09akari.model;

import java.util.ArrayList;

public class ModelImpl implements Model {

  private final PuzzleLibrary library;
  private int activePuzzleIndex;
  private int[][] board;
  private final ArrayList<ModelObserver> observers;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) {
      throw new IllegalArgumentException("Library cannot be null");
    }
    this.library = library;
    this.activePuzzleIndex = 0;
    this.observers = new ArrayList<>();
    resetPuzzle();
  }

  @Override
  public void addLamp(int r, int c) {
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) {
      throw new IndexOutOfBoundsException();
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    board[r][c] = 2;
    notifyObservers();
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) {
      throw new IndexOutOfBoundsException();
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    board[r][c] = 0;
    setPathIsLit(r, c);
    notifyObservers();
  }

  private void setPathIsLit(int r, int c) {
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) {
      throw new IndexOutOfBoundsException();
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }

    int cur;
    int width = getActivePuzzle().getWidth();
    int height = getActivePuzzle().getHeight();

    cur = r;
    while (cur < height) {
      if (board[cur][c] == -1) {
        break;
      }
      if (isLit(cur, c) && !isLamp(cur, c)) {
        board[cur][c] = 1;
      }
      cur++;
    }

    cur = r - 1;
    while (cur >= 0) {
      if (board[cur][c] == -1) {
        break;
      }
      if (isLit(cur, c) && !isLamp(cur, c)) {
        board[cur][c] = 1;
      }
      cur--;
    }

    cur = c + 1;
    while (cur < width) {
      if (board[r][cur] == -1) {
        break;
      }
      if (isLit(r, cur) && !isLamp(r, cur)) {
        board[r][cur] = 1;
      }
      cur++;
    }

    cur = c - 1;
    while (cur >= 0) {
      if (board[r][cur] == -1) {
        break;
      }
      if (isLit(r, cur) && !isLamp(r, cur)) {
        board[r][cur] = 1;
      }
      cur--;
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) {
      throw new IndexOutOfBoundsException();
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }

    int height = getActivePuzzle().getHeight();
    int width = getActivePuzzle().getWidth();

    int cur = r;
    while (cur < height) {
      if (board[cur][c] == -1) {
        break;
      } else if (isLamp(cur, c)) {
        return true;
      }
      cur++;
    }

    cur = r - 1;
    while (cur >= 0) {
      if (board[cur][c] == -1) {
        break;
      } else if (isLamp(cur, c)) {
        return true;
      }
      cur--;
    }

    cur = c + 1;
    while (cur < width) {
      if (board[r][cur] == -1) {
        break;
      } else if (isLamp(r, cur)) {
        return true;
      }
      cur++;
    }

    cur = c - 1;
    while (cur >= 0) {
      if (board[r][cur] == -1) {
        break;
      } else if (isLamp(r, cur)) {
        return true;
      }
      cur--;
    }

    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) {
      throw new IndexOutOfBoundsException();
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    return board[r][c] == 2;
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) {
      throw new IndexOutOfBoundsException();
    }
    if (!isLamp(r, c)) {
      throw new IllegalArgumentException();
    }
    // Temporarily remove lamp to use isLit() to test if slot would otherwise be lit
    board[r][c] = 0;
    boolean result = isLit(r, c);
    board[r][c] = 2;
    return result;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return library.getPuzzle(activePuzzleIndex);
  }

  @Override
  public int getActivePuzzleIndex() {
    return activePuzzleIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= library.size()) {
      throw new IndexOutOfBoundsException("Tried to access puzzle that doesn't exist");
    }
    activePuzzleIndex = index;
    resetPuzzle();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    // For every cell, set to -1 if wall or clue, 0 if empty
    Puzzle puzzle = getActivePuzzle();
    this.board = new int[puzzle.getHeight()][puzzle.getWidth()];
    for (int row = 0; row < puzzle.getHeight(); row++) {
      for (int clm = 0; clm < puzzle.getWidth(); clm++) {
        if (puzzle.getCellType(row, clm) == CellType.WALL
            || puzzle.getCellType(row, clm) == CellType.CLUE) {
          board[row][clm] = -1;
        } else {
          board[row][clm] = 0;
        }
      }
    }
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    Puzzle puz = getActivePuzzle();
    for (int r = 0; r < puz.getHeight(); r++) {
      for (int c = 0; c < puz.getWidth(); c++) {
        if (puz.getCellType(r, c) == CellType.CORRIDOR) {
          if (isLamp(r, c)) {
            if (isLampIllegal(r, c)) {
              return false;
            }
          } else if (!isLit(r, c)) {
            return false;
          }
        } else if (puz.getCellType(r, c) == CellType.CLUE) {
          if (board[r][c] != -1) {
            throw new RuntimeException("Board clue illegally manipulated");
          }
          if (!isClueSatisfied(r, c)) {
            return false;
          }
        } else {
          if (board[r][c] != -1) {
            throw new RuntimeException("Board wall illegally manipulated");
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) {
      throw new IndexOutOfBoundsException();
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    int count = 0;
    try {
      if (isLamp(r + 1, c)) {
        count++;
      }
    } catch (RuntimeException ignored) {
    }
    try {
      if (isLamp(r - 1, c)) {
        count++;
      }
    } catch (RuntimeException ignored) {
    }
    try {
      if (isLamp(r, c + 1)) {
        count++;
      }
    } catch (RuntimeException ignored) {
    }
    try {
      if (isLamp(r, c - 1)) {
        count++;
      }
    } catch (RuntimeException ignored) {
    }

    return count == getActivePuzzle().getClue(r, c);
  }

  @Override
  public void addObserver(ModelObserver observer) {
    if (observer == null) {
      throw new NullPointerException();
    }
    this.observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    if (observer == null) {
      throw new NullPointerException();
    }
    this.observers.remove(observer);
  }

  private void notifyObservers() {
    for (ModelObserver o : observers) {
      o.update(this);
    }
  }
}
