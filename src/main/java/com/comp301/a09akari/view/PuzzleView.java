package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PuzzleView implements FXComponent {

  private final ClassicMvcController controller;
  private final Model model;

  public PuzzleView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    GridPane grid = new GridPane();
    grid.setPrefSize(350, 350);
    grid.setMaxSize(350, 350);
    grid.setMinSize(350, 350);

    int rectangleSize =
        Math.min(
            Math.min(
                350 / model.getActivePuzzle().getWidth(),
                350 / model.getActivePuzzle().getHeight()),
            50);

    // Render board
    for (int r = 0; r < model.getActivePuzzle().getHeight(); r++) {
      for (int c = 0; c < model.getActivePuzzle().getWidth(); c++) {
        StackPane pane = new StackPane();
        pane.setPrefSize(rectangleSize, rectangleSize);
        pane.setMaxSize(rectangleSize, rectangleSize);
        pane.setMinSize(rectangleSize, rectangleSize);

        switch (model.getActivePuzzle().getCellType(r, c)) {
          case CORRIDOR:
            if (model.isLit(r, c)) {
              pane.setBackground(
                  new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
              if (model.isLamp(r, c)) {
                if (model.isLampIllegal(r, c)) {
                  pane.setBackground(
                      new Background(
                          new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                ImageView iv = new ImageView(new Image("light-bulb.png"));
                iv.setFitHeight(rectangleSize * .6);
                iv.setFitWidth(rectangleSize * .6);
                pane.getChildren().add(iv);
              }
            } else {
              pane.setBackground(
                  new Background(
                      new BackgroundFill(Color.MINTCREAM, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            int curR = r;
            int curC = c;
            pane.setOnMouseClicked(event -> controller.clickCell(curR, curC));
            break;
          case CLUE:
            pane.setBackground(
                new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

            Label num = new Label(((Integer) model.getActivePuzzle().getClue(r, c)).toString());
            if (model.isClueSatisfied(r, c)) {
              num.setTextFill(Color.LAWNGREEN);
            } else {
              num.setTextFill(Color.WHITE);
            }
            num.setFont(new Font(25));

            pane.getChildren().add(num);
            break;
          case WALL:
            pane.setBackground(
                new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            break;
        }
        grid.add(pane, c, r);
      }
    }
    grid.setGridLinesVisible(true);
    return grid;
  }
}
