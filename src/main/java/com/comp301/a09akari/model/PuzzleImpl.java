package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {

  private final int[][] board;

  public PuzzleImpl(int[][] board) {
    this.board = board;
  }

  @Override
  public int getWidth() {
    return board[0].length;
  }

  @Override
  public int getHeight() {
    return board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) {
      throw new IndexOutOfBoundsException();
    }
    if (board[r][c] == 5) {
      return CellType.WALL;
    } else if (board[r][c] == 6) {
      return CellType.CORRIDOR;
    } else {
      return CellType.CLUE;
    }
  }

  @Override
  public int getClue(int r, int c) {
    if (getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    return board[r][c];
  }
}
