package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class View implements FXComponent {

  private final ClassicMvcController controller;
  private final Model model;

  public View(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    VBox layout = new VBox();

    ControlView controlView = new ControlView(controller, model);
    layout.getChildren().add(controlView.render());

    PuzzleView puzzleView = new PuzzleView(controller, model);
    layout.getChildren().add(puzzleView.render());

    MessageView messageView = new MessageView(controller, model);
    layout.getChildren().add(messageView.render());

    return layout;
  }
}
