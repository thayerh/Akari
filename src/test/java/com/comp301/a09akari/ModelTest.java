package com.comp301.a09akari;

import com.comp301.a09akari.model.*;
import org.junit.Test;

import static org.junit.Assert.*;

/** Unit test for simple App. */
public class ModelTest {

  /** Rigorous Test :-) */
  @Test
  public void testModelConstructor() {
    PuzzleLibrary pl = new PuzzleLibraryImpl();
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    Model m = new ModelImpl(pl);
  }

  @Test
  public void testAddLamp() {
    PuzzleLibrary pl = new PuzzleLibraryImpl();
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    Model m = new ModelImpl(pl);
    m.addLamp(0, 0);
    assertTrue(m.isLamp(0, 0));
  }

  @Test
  public void testIsLitBase() {
    PuzzleLibrary pl = new PuzzleLibraryImpl();
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    Model m = new ModelImpl(pl);
    m.addLamp(0, 0);
    assertTrue(m.isLit(0, 1));
    assertFalse(m.isLit(0, 6));
    assertFalse(m.isLit(6, 0));
    try {
      System.out.println(m.isLit(2, 0));
    } catch (Exception e) {
      System.out.println("Caught");
    }
    assertTrue(m.isLit(0, 3));
  }

  @Test
  public void testIsLitEdge() {
    PuzzleLibrary pl = new PuzzleLibraryImpl();
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    Model m = new ModelImpl(pl);
  }

  @Test
  public void testActivePuzzle() {
    PuzzleLibrary pl = new PuzzleLibraryImpl();
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_05));
    Model m = new ModelImpl(pl);
    for (int i = 0; i < 5; i++) {
      m.setActivePuzzleIndex(i);
      Puzzle p = m.getActivePuzzle();
      for (int r = 0; r < pl.getPuzzle(i).getHeight(); r++) {
        for (int c = 0; c < pl.getPuzzle(i).getWidth(); c++) {
          assertEquals(p.getCellType(r, c), pl.getPuzzle(i).getCellType(r, c));
        }
      }
    }
  }
}
