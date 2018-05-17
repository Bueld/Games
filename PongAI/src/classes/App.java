package classes;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

	private Scene scn;
	private StackPane stck;
	private Group playground;
	private Group options;

	private Rectangle bounds;
	private Rectangle top, bot;
	private Rectangle statsBg;

	private Rectangle frame;

	private Paddle p1;
	private Paddle p2;

	private ArrayList<Ball> ballsOfSteel;

	private ArrayList<String> keyPressing;

	private GridPane stats;
	private Label scoreA, scoreB, nameA, nameB;
	private String playerA = "Méla";
	private String playerB = "Baxi";

	private Button optionsButton;
	private Rectangle pausingBg;
	private Label pausing;

	private Rectangle darken;
	private Rectangle optionsBg;
	private GridPane optionsGrid;
	private Label optionsTitle;
	private TextField editPlayerA, editPlayerB;
	private Label remainingCA, remainingCB;
	private Button setPlayerA, setPlayerB;
	private Button exitOptionsButton;

	private Timeline time;

	private int ballNumber = 10;
	private double speeeeeed = 3 * Math.PI;
	private double zoom = 2;
	private int winsA, winsB;
	private final int borderWidth = 12;
	private final int padding = 8;
	private final int paddleHeight = 100;
	private final int nameLength = 20;

	private double ballSpeed = Math.PI;

	private boolean optionsMode = false;
	private boolean paused = false;

	public void init() {

		createPlayground();

		createKeyHandling();
	}

	private void createPlayground() {

		playground = new Group();
		options = new Group();

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

		createStats();

		ballsOfSteel = new ArrayList<Ball>();
		for (int i = 0; i < ballNumber; i++) {
			Color c = Color.hsb(Math.random() * 360, 1, 1);
			ballsOfSteel.add(new Ball(8, ballSpeed));
			ballsOfSteel.get(i).reset(false, bounds);
			ballsOfSteel.get(i).setFill(c);
			ballsOfSteel.get(i).setStroke(c.darker());
		}

		frame = new Rectangle();
		frame.setHeight(bounds.getHeight() - 2);
		frame.setWidth(bounds.getWidth() - 2);
		frame.setTranslateX(1);
		frame.setTranslateY(1);
		frame.setFill(Color.TRANSPARENT);
		frame.setStroke(border);
		frame.setStrokeWidth(padding + 1);
		frame.setStrokeType(StrokeType.OUTSIDE);

		p1 = new Paddle(true, bounds, paddleHeight);
		p2 = new Paddle(false, bounds, paddleHeight);

		createPausing();
		createOptions();

		playground.getChildren().addAll(bounds, top, bot, statsBg, stats);
		playground.getChildren().addAll(ballsOfSteel);
		playground.getChildren().addAll(frame, p1, p2, pausingBg, pausing, options);
	}

	private void enterOptions() {
		optionsMode = true;
		paused = true;
	}

	private void exitOptions() {
		optionsMode = false;
	}

	private void createStats() {
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
		stats.setPadding(new Insets(padding));
		stats.setHgap(16);

		Font f = Font.font("Courier New", FontWeight.BLACK, 20);

		scoreA = new Label("" + winsA);
		scoreA.setFont(f);
		scoreA.setTextFill(Color.MEDIUMSLATEBLUE.darker());

		scoreB = new Label("" + winsB);
		scoreB.setFont(f);
		scoreB.setTextFill(Color.MEDIUMSLATEBLUE.darker());

		optionsButton = new Button("Options");
		optionsButton.setFont(f);
		optionsButton.setOnAction(e -> {
			enterOptions();
		});

		nameA = new Label(playerA + ": ");
		nameA.setFont(f);
		nameA.setTextFill(Color.MEDIUMSLATEBLUE.darker());
		nameB = new Label(playerB + ": ");
		nameB.setFont(f);
		nameB.setTextFill(Color.MEDIUMSLATEBLUE.darker());

		stats.add(scoreA, 1, 0);
		stats.add(scoreB, 1, 1);
		stats.add(nameA, 0, 0);
		stats.add(nameB, 0, 1);
		stats.add(optionsButton, 1, 0);

		GridPane.setConstraints(scoreA, 1, 0, 1, 1, HPos.LEFT, VPos.CENTER);
		GridPane.setConstraints(scoreB, 1, 1, 1, 1, HPos.LEFT, VPos.CENTER);
		GridPane.setConstraints(optionsButton, 2, 0, 1, 2, HPos.CENTER, VPos.CENTER);
		GridPane.setHgrow(scoreA, Priority.NEVER);
		GridPane.setHgrow(scoreB, Priority.NEVER);
		GridPane.setHgrow(nameA, Priority.ALWAYS);
		GridPane.setHgrow(nameB, Priority.ALWAYS);
		GridPane.setHgrow(optionsButton, Priority.NEVER);
	}

	private void createPausing() {
		Font f = Font.font("Courier New", FontWeight.BLACK, 80);
		pausingBg = new Rectangle();
		pausingBg.setFill(Color.DARKSLATEGRAY);
		pausingBg.setOpacity(0.7);
		pausingBg.setStroke(Color.DARKSLATEGRAY);
		pausingBg.setStrokeWidth(32);
		pausingBg.setStrokeLineJoin(StrokeLineJoin.ROUND);

		pausing = new Label();
		pausing.setFont(f);
		pausing.setText("PAUSED");
		pausing.setTextFill(Color.WHITESMOKE.darker());
		pausing.setVisible(false);
	}

	private void createOptions() {

		darken = new Rectangle();
		darken.setWidth(bounds.getWidth());
		darken.setHeight(bounds.getHeight() + 16 + statsBg.getHeight());
		darken.setStroke(Color.BLACK);
		darken.setStrokeWidth(padding * 2);
		darken.setOpacity(0.5);

		optionsBg = new Rectangle();
		optionsBg.setWidth(bounds.getWidth() * .8);
		optionsBg.setHeight((bounds.getHeight() + 16 + stats.getHeight()) * .8);
		optionsBg.setTranslateX((bounds.getWidth() - optionsBg.getWidth()) / 2);
		optionsBg.setTranslateY((bounds.getHeight() - optionsBg.getHeight()) / 2);
		optionsBg.setFill(Color.LIGHTBLUE.brighter());
		optionsBg.setStroke(Color.LIGHTBLUE.darker());
		optionsBg.setStrokeWidth(padding);
		optionsBg.setStrokeType(StrokeType.INSIDE);

		optionsGrid = new GridPane();
		optionsGrid.setMinWidth(optionsBg.getWidth() - padding * 2);
		optionsGrid.setMaxWidth(optionsBg.getWidth() - padding * 2);
		optionsGrid.setMinHeight(optionsBg.getHeight() - padding * 2);
		optionsGrid.setMaxHeight(optionsBg.getHeight() - padding * 2);
		optionsGrid.setTranslateX(optionsBg.getTranslateX() + padding);
		optionsGrid.setTranslateY(optionsBg.getTranslateY() + padding);

		optionsGrid.setPadding(new Insets(padding));
		optionsGrid.setHgap(8);
		optionsGrid.setVgap(8);

		exitOptionsButton = createButton("x", Color.rgb(240, 0, 0), Color.rgb(255, 30, 30));
		exitOptionsButton.setOnAction(e -> {
			exitOptions();
		});

		optionsGrid.add(exitOptionsButton, 3, 0);

		Font f = Font.font("Courier New", FontWeight.BLACK, 25);
		Color c = Color.DARKCYAN.darker();

		optionsTitle = new Label();
		optionsTitle.setFont(f);
		optionsTitle.setText("Options");
		optionsTitle.setTextFill(c);

		optionsGrid.add(optionsTitle, 0, 0);

		f = Font.font("Courier New", FontWeight.BLACK, 20);

		editPlayerA = new TextField();
		editPlayerA.setPromptText("Player A");
		editPlayerA.setPrefSize(150, 30);
		editPlayerA.setFont(f);
		editPlayerA.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.length() > nameLength) {
					editPlayerA.setText(newValue.substring(0, nameLength));
					remainingCA.setText(0 + "");
					return;
				}
				remainingCA.setText(nameLength - newValue.length() + "");
			}
		});

		optionsGrid.add(editPlayerA, 0, 1);

		editPlayerB = new TextField();
		editPlayerB.setPromptText("Player B");
		editPlayerB.setPrefSize(150, 30);
		editPlayerB.setFont(f);
		editPlayerB.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.length() > nameLength) {
					editPlayerB.setText(newValue.substring(0, nameLength));
					remainingCB.setText(0 + "");
					return;
				}
				remainingCB.setText(nameLength - newValue.length() + "");
			}
		});

		optionsGrid.add(editPlayerB, 0, 2);

		remainingCA = new Label();
		remainingCA.setFont(f);
		remainingCA.setTextFill(c);

		optionsGrid.add(remainingCA, 1, 1);

		remainingCA.setText(nameLength + "");

		remainingCB = new Label();
		remainingCB.setFont(f);
		remainingCB.setTextFill(c);

		optionsGrid.add(remainingCB, 1, 2);

		remainingCB.setText(nameLength + "");

		setPlayerA = createButton("y", Color.rgb(0, 130, 0), Color.rgb(0, 180, 0));
		setPlayerA.setOnAction(e -> {
			playerA = editPlayerA.getText();
			nameA.setText(playerA + ": ");
		});

		optionsGrid.add(setPlayerA, 2, 1);

		setPlayerB = createButton("y", Color.rgb(0, 130, 0), Color.rgb(0, 180, 0));
		setPlayerB.setOnAction(e -> {
			playerB = editPlayerB.getText();
			nameB.setText(playerB + ": ");
		});

		optionsGrid.add(setPlayerB, 2, 2);

		GridPane.setConstraints(exitOptionsButton, 3, 0, 1, 1, HPos.RIGHT, VPos.CENTER);
		GridPane.setConstraints(optionsTitle, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER);
		GridPane.setHgrow(optionsTitle, Priority.ALWAYS);

		options.getChildren().addAll(darken, optionsBg, optionsGrid);
		options.setVisible(false);
	}

	private Button createButton(String name, Color r1, Color r2) {
		Button b = new Button();
		b.setBackground(null);

		Rectangle bg = new Rectangle();
		bg.setWidth(32);
		bg.setHeight(24);
		bg.setTranslateX(-16);
		bg.setTranslateY(-12);
		bg.setStrokeLineJoin(StrokeLineJoin.ROUND);
		bg.setStrokeWidth(8);
		bg.setOpacity(0.7);

		Image xO = new Image(getClass().getResourceAsStream("../img/" + name + ".png"));
		ImageView xOnly = new ImageView(xO);
		xOnly.setFitWidth(24);
		xOnly.setFitHeight(24);
		xOnly.setTranslateX(-12);
		xOnly.setTranslateY(-12);

		Light.Distant light = new Light.Distant();
		light.setAzimuth(225);
		light.setColor(Color.NAVAJOWHITE);

		Lighting lighting = new Lighting();
		lighting.setLight(light);
		lighting.setSurfaceScale(2);

		bg.setEffect(lighting);

		bg.setFill(r1);
		bg.setStroke(r1);

		b.setOnMouseEntered(e -> {
			bg.setFill(r2);
			bg.setStroke(r2);
		});
		b.setOnMouseExited(e -> {
			bg.setFill(r1);
			bg.setStroke(r1);
		});
		b.setOnMousePressed(e -> {
			light.setAzimuth(45);
		});
		b.setOnMouseReleased(e -> {
			light.setAzimuth(225);
		});

		Group xSymbol = new Group();
		xSymbol.getChildren().addAll(bg, xOnly);

		b.setGraphic(xSymbol);
		b.setPadding(new Insets(0));
		return b;
	}

	private void createKeyHandling() {

		keyPressing = new ArrayList<String>();

		time = new Timeline();
		time.setCycleCount(Timeline.INDEFINITE);
		time.getKeyFrames().add(new KeyFrame(Duration.millis(14), e -> {

			if (!paused) {

				moveAccordingToKeyAction();
				// ok
				for (int i = 0; i < ballsOfSteel.size(); i++) {
					int a = ballsOfSteel.get(i).moveBall(top, bot, p1, p2, bounds);
					if (a == 1) {
						winsA++;
						scoreA.setText("" + winsA);
						ballsOfSteel.get(i).reset(false, bounds);
					}
					if (a == -1) {
						winsB++;
						scoreB.setText("" + winsB);
						ballsOfSteel.get(i).reset(false, bounds);
					}
				}
			}
			updatePausing();
			updateOptions();
		}));
	}

	private void updatePausing() {
		if (paused) {
			pausing.setTranslateX((bounds.getWidth() - pausing.getWidth()) / 2);
			pausing.setTranslateY((bounds.getHeight() - pausing.getHeight()) / 2);
			pausingBg.setWidth(pausing.getWidth() + 16);
			pausingBg.setHeight(pausing.getHeight() - 32);
			pausingBg.setTranslateX((bounds.getWidth() - pausingBg.getWidth()) / 2);
			pausingBg.setTranslateY((bounds.getHeight() - pausingBg.getHeight()) / 2);
			pausingBg.setVisible(true);
			pausing.setVisible(true);
		} else {
			pausingBg.setVisible(false);
			pausing.setVisible(false);
		}
	}

	private void updateOptions() {
		if (optionsMode) {
			options.setVisible(true);
		} else {
			options.setVisible(false);
		}
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
		stck.getChildren().addAll(playground);
		scn = new Scene(stck, 666, 666, true);
		scn.setFill(Color.rgb(5, 5, 50));

		stg.setScene(scn);
		stg.setTitle("Pong");
		stg.getIcons().add(new Image(getClass().getResourceAsStream("../img/icon.png")));

		scn.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.F11) {
				stg.setFullScreen(!stg.isFullScreen());
			} else if (e.getCode() == KeyCode.P) {
				if (!optionsMode)
					paused = !paused;
			} else if (e.getCode() == KeyCode.ENTER) {
				for (int i = 0; i < ballsOfSteel.size(); i++) {
					ballsOfSteel.get(i).setMoving(true);
				}
			} else if (e.getCode() == KeyCode.BACK_SPACE) {
				for (int i = 0; i < ballsOfSteel.size(); i++) {
					ballsOfSteel.get(i).reset(false, bounds);
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
