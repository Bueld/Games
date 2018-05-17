package classes;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

	private Scene scn;
	private StackPane stck;
	private Group playground;

	private Rectangle bounds;
	private Rectangle top, bot;
	private Rectangle statsBg;

	private Rectangle frame;

	private Paddle p1;
	private Paddle p2;

	private ArrayList<Ball> ballsOfSteel;

	private ArrayList<String> keyPressing;

	private GridPane stats;
	private Label scoreA, scoreB;

	private Button switchNameEditor;

	private Timeline time;

	private int ballNumber = 10000;
	private double speeeeeed = 3 * Math.PI;
	private double zoom = 2;
	private int winsA, winsB;
	private final int borderWidth = 12;
	private final int padding = 8;
	private final int paddleHeight = 200;

	private double ballSpeed = Math.PI;

	private boolean nameEditor = false;
	private boolean paused = false;

	public void init() {

		createPlayground();

		createKeyHandling();
	}

	private void createPlayground() {

		playground = new Group();

		Color border = Color.rgb(150, 40, 190);

		bounds = new Rectangle();
		bounds.setWidth(600);
		bounds.setHeight(500);
		bounds.setFill(Color.rgb(10, 3, 15));

		top = new Rectangle();
		top.setWidth(bounds.getWidth());
		top.setHeight(borderWidth);
		top.setFill(border);
		top.setStroke(border);

		bot = new Rectangle();
		bot.setWidth(bounds.getWidth());
		bot.setHeight(borderWidth);
		bot.setFill(border);
		bot.setTranslateY(bounds.getHeight() - bot.getHeight());
		bot.setStroke(border);

		statsBg = new Rectangle();
		statsBg.setWidth(bounds.getWidth());
		statsBg.setHeight(64);
		statsBg.setFill(Color.LIGHTBLUE.brighter());
		statsBg.setStroke(Color.LIGHTBLUE.darker());
		statsBg.setStrokeWidth(padding);
		statsBg.setStrokeType(StrokeType.OUTSIDE);
		statsBg.setTranslateY(bounds.getHeight() + 16);

		stats = new GridPane();
		stats.setTranslateY(statsBg.getTranslateY());
		stats.setMinHeight(statsBg.getHeight());
		stats.setMaxHeight(statsBg.getHeight());
		stats.setMinWidth(statsBg.getWidth());
		stats.setMaxWidth(statsBg.getWidth());

		playground.getChildren().addAll(bounds, top, bot, statsBg);

		p1 = new Paddle(true, bounds, paddleHeight);
		p2 = new Paddle(false, bounds, paddleHeight);

		ballsOfSteel = new ArrayList<Ball>();
		for (int i = 0; i < ballNumber; i++) {
			Color c = Color.hsb(Math.random() * 360, 1, 1);
			ballsOfSteel.add(new Ball(1, ballSpeed));
			ballsOfSteel.get(i).reset(false, bounds);
			ballsOfSteel.get(i).setFill(c);
			ballsOfSteel.get(i).setStroke(c.darker());
		}

		playground.getChildren().addAll(ballsOfSteel);

		frame = new Rectangle();
		frame.setHeight(bounds.getHeight() - 2);
		frame.setWidth(bounds.getWidth() - 2);
		frame.setTranslateX(1);
		frame.setTranslateY(1);
		frame.setFill(Color.TRANSPARENT);
		frame.setStroke(border);
		frame.setStrokeWidth(padding + 1);
		frame.setStrokeType(StrokeType.OUTSIDE);

		playground.getChildren().add(frame);

		playground.getChildren().addAll(p1, p2);
	}

	private void createKeyHandling() {

		keyPressing = new ArrayList<String>();

		time = new Timeline();
		time.setCycleCount(Timeline.INDEFINITE);
		time.getKeyFrames().add(new KeyFrame(Duration.millis(14), e -> {

			moveAccordingToKeyAction();

			if (!paused) {
				for (int i = 0; i < ballsOfSteel.size(); i++) {
					int a = ballsOfSteel.get(i).moveBall(top, bot, p1, p2, bounds);
					if (a == 1) {
						winsA++;
						ballsOfSteel.get(i).reset(false, bounds);
					}
					if (a == -1) {
						winsB++;
						ballsOfSteel.get(i).reset(false, bounds);
					}
				}
			}

		}));
	}

	private void moveAccordingToKeyAction() {

		if (keyPressing.contains("A") && p1.getTranslateY() > top.getTranslateY() + top.getHeight()) {
			if (p1.getTranslateY() - speeeeeed < top.getTranslateY() + top.getHeight()) {
				p1.setTranslateY(top.getTranslateY() + top.getHeight());
			} else {
				p1.setTranslateY(p1.getTranslateY() - speeeeeed);
			}
		}
		if (keyPressing.contains("D") && p1.getTranslateY() + p1.getHeight() < bot.getTranslateY()) {
			if (p1.getTranslateY() + speeeeeed + p1.getHeight() > bot.getTranslateY()) {
				p1.setTranslateY(bot.getTranslateY() - p1.getHeight());
			} else {
				p1.setTranslateY(p1.getTranslateY() + speeeeeed);
			}
		}
		if (keyPressing.contains("J") && p2.getTranslateY() > top.getTranslateY() + top.getHeight()) {
			if (p2.getTranslateY() - speeeeeed < top.getTranslateY() + top.getHeight()) {
				p2.setTranslateY(top.getTranslateY() + top.getHeight());
			} else {
				p2.setTranslateY(p2.getTranslateY() - speeeeeed);
			}
		}
		if (keyPressing.contains("L") && p2.getTranslateY() + p2.getHeight() < bot.getTranslateY()) {
			if (p2.getTranslateY() + speeeeeed + p2.getHeight() > bot.getTranslateY()) {
				p2.setTranslateY(bot.getTranslateY() - p2.getHeight());
			} else {
				p2.setTranslateY(p2.getTranslateY() + speeeeeed);
			}
		}

	}

	private void addKeyPressing(String code) {

		if (!keyPressing.contains(code)) {
			keyPressing.add(code);
		}
	}

	private void adjustZoom(double height, double width) {
		double scnRelation = height / width;
		double boundsRelation = (bounds.getHeight() + 16 + statsBg.getHeight()) / bounds.getWidth();

		if (scnRelation >= boundsRelation) {
			zoom = width / (bounds.getWidth() + padding * 2);
		} else {
			zoom = height / (bounds.getHeight() + padding * 2 + 16 + statsBg.getHeight());
		}
		playground.setScaleX(zoom);
		playground.setScaleY(zoom);
	}

	@Override
	public void start(Stage stg) {
		stck = new StackPane();
		stck.setBackground(null);
		stck.getChildren().add(playground);
		scn = new Scene(stck, 666, 666, true);
		scn.setFill(Color.rgb(5, 5, 50));

		stg.setScene(scn);
		stg.setTitle("Pong");
		stg.getIcons().add(new Image(getClass().getResourceAsStream("../img/icon.png")));

		scn.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.F11) {
				stg.setFullScreen(!stg.isFullScreen());
			} else if (e.getCode() == KeyCode.SPACE) {
				paused = !paused;
			} else if (e.getCode() == KeyCode.ENTER) {
				for (int i = 0; i < ballsOfSteel.size(); i++) {
					ballsOfSteel.get(i).setMoving(true);
				}
			} else if (e.getCode() == KeyCode.BACK_SPACE) {
				for (int i = 0; i < ballsOfSteel.size(); i++) {
					ballsOfSteel.get(i).reset(false, bounds);
					;
				}
			} else {
				addKeyPressing(e.getCode() + "");
			}
		});

		scn.setOnKeyReleased(e -> {
			if (keyPressing.contains(e.getCode() + "")) {
				keyPressing.remove(e.getCode() + "");
			}
		});

		scn.heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				adjustZoom(newValue.doubleValue(), scn.getWidth());
			}
		});

		scn.widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				adjustZoom(scn.getHeight(), newValue.doubleValue());
			}
		});

		stg.show();
		adjustZoom(scn.getHeight(), scn.getWidth());
		time.play();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
