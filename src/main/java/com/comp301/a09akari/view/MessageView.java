package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MessageView implements FXComponent {

  private final ClassicMvcController controller;
  private final Model model;

  public MessageView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    VBox layout = new VBox();

    StackPane solved = new StackPane();
    solved.setPrefHeight(30);
    solved.setPrefWidth(350);
    solved.setMinHeight(30);
    solved.setMinWidth(350);
    solved.setMaxHeight(30);
    solved.setMaxWidth(350);

    StackPane puzNum = new StackPane();
    Label lPN =
        new Label(
            "Puzzle " + (model.getActivePuzzleIndex() + 1) + " of " + model.getPuzzleLibrarySize());
    lPN.setFont(new Font(15));
    puzNum.getChildren().add(lPN);
    puzNum.setPrefHeight(30);
    puzNum.setPrefWidth(350);
    puzNum.setMinHeight(30);
    puzNum.setMinWidth(350);
    puzNum.setMaxHeight(30);
    puzNum.setMaxWidth(350);

    if (model.isSolved()) {
      Label l = new Label("Solved");
      l.setFont(new Font(20));
      // l.setTextFill(Color.LAWNGREEN);
      solved.getChildren().add(l);
      // solved.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY,
      // Insets.EMPTY)));

    } else {
      Label l = new Label("Not Solved");
      l.setFont(new Font(20));
      solved.getChildren().add(l);
    }

    layout.getChildren().add(solved);
    layout.getChildren().add(puzNum);

    return layout;
  }
}
