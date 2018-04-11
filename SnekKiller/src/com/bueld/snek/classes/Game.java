package com.bueld.snek.classes;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class Game extends Application {

	private static final Rectangle style = new Rectangle(0, 0, 50, 50);
	private static final int gridSize = 10;
	private static final String name = "Snek Killer";

	private StackPane gridsPane;

	private BorderPane borderPane;
	private Scene scene;

	private Grid g;

	private Button normMouse;
	private Button addMouse;
	private Button delMouse;

	private Button clearAll;

	private GridPane mouseChangers;

	public void init() {
		setupGrids();
		setupButtons();
	}

	private void setupGrids() {

		style.setFill(Color.DARKGREY);
		style.setStroke(Color.GREY);
		style.setStrokeType(StrokeType.INSIDE);

		gridsPane = new StackPane();
		gridsPane.setBackground(null);
		gridsPane.setPadding(new Insets(12));

		g = new Grid(gridSize, 0, style);
		gridsPane.getChildren().addAll(g);
	}

	private void setupButtons() {
		normMouse = new Button("Normal");
		normMouse.setOnAction(e -> {
			g.setMouseId(0);
		});
		addMouse = new Button("Adding");
		addMouse.setOnAction(e -> {
			g.setMouseId(1);
		});
		delMouse = new Button("Deleting");
		delMouse.setOnAction(e -> {
			g.setMouseId(2);
		});

		clearAll = new Button("Clear All");
		clearAll.setOnAction(e -> {
			g.clearAll();
		});

		mouseChangers = new GridPane();

		mouseChangers.setPadding(new Insets(20));
		mouseChangers.setHgap(15);
		mouseChangers.setVgap(15);

		mouseChangers.add(normMouse, 0, 0);
		mouseChangers.add(addMouse, 0, 1);
		mouseChangers.add(delMouse, 0, 2);
		mouseChangers.add(clearAll, 0, 3);

	}

	@Override
	public void start(Stage stage) {
		borderPane = new BorderPane();
		borderPane.setBackground(null);
		borderPane.setCenter(gridsPane);
		borderPane.setLeft(mouseChangers);

		scene = new Scene(borderPane, 666, 666, true, SceneAntialiasing.BALANCED);
		scene.setFill(Color.rgb(30, 6, 40));

		stage.setScene(scene);
		stage.setTitle(name);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("../img/icon.png")));

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.F11) {
					stage.setFullScreen(!stage.isFullScreen());
				}

			}

		});

		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Rectangle getRectStyle() {
		return style;
	}

	public static int getGridsize() {
		return gridSize;
	}

}
