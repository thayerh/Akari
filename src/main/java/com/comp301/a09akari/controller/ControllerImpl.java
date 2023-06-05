package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;

public class ControllerImpl implements ClassicMvcController {

  private final Model model;

  public ControllerImpl(Model model) {
    if (model == null) {
      throw new IllegalArgumentException("Model can't be null");
    }
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    int cur = model.getActivePuzzleIndex() + 1;
    if (cur < model.getPuzzleLibrarySize()) {
      model.setActivePuzzleIndex(cur);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    int cur = model.getActivePuzzleIndex() - 1;
    if (cur >= 0) {
      model.setActivePuzzleIndex(cur);
    }
  }

  @Override
  public void clickRandPuzzle() {
    int newInd = model.getActivePuzzleIndex();
    while (newInd == model.getActivePuzzleIndex()) {
      newInd = (int) (model.getPuzzleLibrarySize() * Math.random());
    }
    model.setActivePuzzleIndex(newInd);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (r < 0
        || r >= model.getActivePuzzle().getHeight()
        || c < 0
        || c >= model.getActivePuzzle().getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      if (model.isLamp(r, c)) {
        model.removeLamp(r, c);
      } else {
        model.addLamp(r, c);
      }
    }
  }
}
