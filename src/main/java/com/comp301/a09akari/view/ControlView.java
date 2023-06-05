package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlView implements FXComponent {

  private final ClassicMvcController controller;
  private final Model model;

  public ControlView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();

    Button nextButton = new Button("Next");
    nextButton.setOnAction(
        (ActionEvent event) -> {
          controller.clickNextPuzzle();
        });

    Button prevButton = new Button("Prev");
    prevButton.setOnAction(
        (ActionEvent event) -> {
          controller.clickPrevPuzzle();
        });

    Button randButton = new Button("Random");
    randButton.setOnAction(
        (ActionEvent event) -> {
          controller.clickRandPuzzle();
        });

    Button resetButton = new Button("Reset");
    resetButton.setOnAction(
        (ActionEvent event) -> {
          controller.clickResetPuzzle();
        });

    nextButton.setPrefHeight(30);
    nextButton.setPrefWidth(87.5);
    nextButton.setMinHeight(30);
    nextButton.setMinWidth(87.5);
    nextButton.setMaxHeight(30);
    nextButton.setMaxWidth(87.5);

    prevButton.setPrefHeight(30);
    prevButton.setPrefWidth(87.5);
    prevButton.setMinHeight(30);
    prevButton.setMinWidth(87.5);
    prevButton.setMaxHeight(30);
    prevButton.setMaxWidth(87.5);

    randButton.setPrefHeight(30);
    randButton.setPrefWidth(87.5);
    randButton.setMinHeight(30);
    randButton.setMinWidth(87.5);
    randButton.setMaxHeight(30);
    randButton.setMaxWidth(87.5);

    resetButton.setPrefHeight(30);
    resetButton.setPrefWidth(87.5);
    resetButton.setMinHeight(30);
    resetButton.setMinWidth(87.5);
    resetButton.setMaxHeight(30);
    resetButton.setMaxWidth(87.5);

    layout.getChildren().add(nextButton);
    layout.getChildren().add(prevButton);
    layout.getChildren().add(randButton);
    layout.getChildren().add(resetButton);

    return layout;
  }
}
