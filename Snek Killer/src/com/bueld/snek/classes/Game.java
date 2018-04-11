package com.bueld.snek.classes;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Game extends Application {

	private static final Rectangle style = new Rectangle(0, 0, 40, 40);
	private static final int gridSize = 10;
	private static final String name = "Snek Killer";
	
	private StackPane gridsPane;

	private BorderPane borderPane;
	private Scene scene;

	public void init() {
		setupGrids();
	}

	private void setupGrids() {
		
		gridsPane = new StackPane();
		gridsPane.setBackground(null);
		
		Grid g = new Grid(gridSize,0,style);
		gridsPane.getChildren().addAll(g);
	}

	@Override
	public void start(Stage stage) {
		borderPane = new BorderPane();
		borderPane.setBackground(null);
		borderPane.setCenter(gridsPane);

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
