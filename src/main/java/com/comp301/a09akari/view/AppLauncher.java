package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    // TODO: Create your Model, View, and Controller instances and launch your GUI
    PuzzleLibrary pl = new PuzzleLibraryImpl();
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    pl.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_05));
    Model model = new ModelImpl(pl);
    ClassicMvcController controller = new ControllerImpl(model);

    View view = new View(controller, model);

    int sceneWidth = 350;
    int sceneHeight = 450;
    Scene scene = new Scene(view.render(), sceneWidth, sceneHeight);
    stage.setScene(scene);

    model.addObserver(
        (Model m) -> {
          scene.setRoot(view.render());
        });

    stage.setTitle("Akari");
    stage.show();
  }
}
