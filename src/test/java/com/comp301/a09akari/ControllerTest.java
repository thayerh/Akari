package com.comp301.a09akari;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTest {

  private Model makeModel() {
    PuzzleLibrary pl = new PuzzleLibraryImpl();
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_05));
    return new ModelImpl(pl);
  }

  @Test
  public void testControllerConstructor() {
    Model m = makeModel();
    ClassicMvcController c = new ControllerImpl(m);
  }

  @Test
  public void testNextPZero() {
    Model m = makeModel();
    ClassicMvcController c = new ControllerImpl(m);
    c.clickNextPuzzle();
    assertEquals(m.getActivePuzzleIndex(), 1);
  }

  @Test
  public void testNextPMax() {
    Model m = makeModel();
    ClassicMvcController c = new ControllerImpl(m);
    m.setActivePuzzleIndex(4);
    c.clickNextPuzzle();
    assertEquals(m.getActivePuzzleIndex(), 4);
  }

  @Test
  public void testPrevPZero() {
    Model m = makeModel();
    ClassicMvcController c = new ControllerImpl(m);
    c.clickPrevPuzzle();
    assertEquals(m.getActivePuzzleIndex(), 0);
  }

  @Test
  public void testPrevPMax() {
    Model m = makeModel();
    ClassicMvcController c = new ControllerImpl(m);
    m.setActivePuzzleIndex(4);
    c.clickPrevPuzzle();
    assertEquals(m.getActivePuzzleIndex(), 3);
  }

  @Test
  public void testClickRandom() {
    Model m = makeModel();
    ClassicMvcController c = new ControllerImpl(m);
    m.setActivePuzzleIndex(3);
    c.clickRandPuzzle();
    assertNotEquals(m.getActivePuzzleIndex(), 3);
  }

  @Test
  public void testClickCellEmpty() {
    Model m = makeModel();
    ClassicMvcController c = new ControllerImpl(m);
    c.clickCell(0, 0);
    assertTrue(m.isLamp(0, 0));
  }

  @Test
  public void testClickCellLamp() {
    Model m = makeModel();
    ClassicMvcController c = new ControllerImpl(m);
    c.clickCell(0, 0);
    assertTrue(m.isLamp(0, 0));
    c.clickCell(0, 0);
    assertFalse(m.isLamp(0, 0));
  }

  @Test
  public void testClickCellClue() {
    Model m = makeModel();
    ClassicMvcController c = new ControllerImpl(m);
    c.clickCell(3, 5);
    assertEquals(m.getActivePuzzle().getCellType(3, 5), CellType.CLUE);
  }
}
