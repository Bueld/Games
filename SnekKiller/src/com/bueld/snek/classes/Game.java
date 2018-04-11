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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class Game extends Application {

	private static final Rectangle style = new Rectangle(0, 0, 40, 40);
	private static final int gridSize = 10;
	private static final String name = "Snek Killer";

	private GridPane gridsPane;

	private BorderPane borderPane;
	private Scene scene;

	private Grid g1;
	private Grid g2;
	private Grid g3;
	private Grid g4;

	private Button normMouse;
	private Button addMouse;
	private Button delMouse;

	private Button attack;

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

		gridsPane = new GridPane();
		gridsPane.setBackground(null);
		gridsPane.setPadding(new Insets(12));

		g1 = new Grid(gridSize, 0, style);
		g2 = new Grid(gridSize, 1, style);
		g3 = new Grid(gridSize, 2, style);
		g4 = new Grid(gridSize, 3, style);

		gridsPane.setHgap(style.getWidth() * 2);
		gridsPane.setVgap(style.getHeight());
		gridsPane.setPadding(new Insets(style.getHeight() / 2, style.getWidth(), style.getHeight(), style.getWidth()));

		gridsPane.add(g1, 0, 0);
		gridsPane.add(g2, 0, 1);
		gridsPane.add(g3, 1, 0);
		gridsPane.add(g4, 1, 1);

	}

	private void setupButtons() {
		normMouse = new Button("Normal");
		normMouse.setOnAction(e -> {
			g1.setMouseId(0);
			g2.setMouseId(0);
			g3.setMouseId(0);
			g4.setMouseId(0);
		});
		addMouse = new Button("Adding");
		addMouse.setOnAction(e -> {
			g1.setMouseId(1);
			g2.setMouseId(1);
			g3.setMouseId(1);
			g4.setMouseId(1);
		});
		delMouse = new Button("Deleting");
		delMouse.setOnAction(e -> {
			g1.setMouseId(2);
			g2.setMouseId(2);
			g3.setMouseId(2);
			g4.setMouseId(2);
		});

		clearAll = new Button("Clear All");
		clearAll.setOnAction(e -> {
			g1.clearAll();
			g2.clearAll();
			g3.clearAll();
			g4.clearAll();
		});

		attack = new Button("Attack");
		attack.setOnAction(e -> {
			g1.setMouseId(3);
			g2.setMouseId(3);
			g3.setMouseId(3);
			g4.setMouseId(3);
		});

		mouseChangers = new GridPane();

		mouseChangers.setPadding(new Insets(20));
		mouseChangers.setHgap(15);
		mouseChangers.setVgap(15);

		mouseChangers.add(normMouse, 0, 0);
		mouseChangers.add(addMouse, 0, 1);
		mouseChangers.add(delMouse, 0, 2);
		mouseChangers.add(clearAll, 0, 3);
		mouseChangers.add(attack, 0, 4);

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
