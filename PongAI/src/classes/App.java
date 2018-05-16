package classes;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

	private Scene scn;
	private StackPane stck;
	private Group playground;

	private Rectangle bounds;
	private Rectangle top;
	private Rectangle bot;

	private Paddle p1;
	private Paddle p2;

	private Ball b;

	private ArrayList<String> keyPressing;

	private Timeline time;

	private double speeeeeed = 3 * Math.PI;

	private double ballSpeed = Math.PI;
	private boolean ballMoving = false;
	private double bxd;
	private double byd;

	public void init() {

		createPlayground();

		createKeyHandling();
	}

	private void createPlayground() {

		playground = new Group();

		bounds = new Rectangle();
		bounds.setWidth(600);
		bounds.setHeight(500);
		bounds.setFill(Color.rgb(10, 3, 15));

		Color border = Color.rgb(150, 40, 190);

		top = new Rectangle();
		top.setWidth(600);
		top.setHeight(10);
		top.setFill(border);

		bot = new Rectangle();
		bot.setWidth(600);
		bot.setHeight(10);
		bot.setFill(border);
		bot.setTranslateY(490);

		playground.getChildren().addAll(bounds, top, bot);

		p1 = new Paddle(true);
		p2 = new Paddle(false);
		b = new Ball(6);

		playground.getChildren().addAll(p1, p2, b);
	}

	private void createKeyHandling() {

		keyPressing = new ArrayList<String>();

		time = new Timeline();
		time.setCycleCount(Timeline.INDEFINITE);
		time.getKeyFrames().add(new KeyFrame(Duration.millis(14), e -> {

			moveAccordingToKeyAction();

			moveBall();

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

	private void moveBall() {
		if (ballMoving) {
			b.setTranslateX(b.getTranslateX() + bxd);
			b.setTranslateY(b.getTranslateY() + byd);

			if (b.getTranslateY() - byd - b.getRadiusY() < top.getTranslateY() + top.getHeight()) {
				b.setTranslateY(top.getTranslateY() + top.getHeight() + b.getRadiusY());
				byd = Math.abs(byd);
			}

			if (b.getTranslateY() + byd + b.getRadiusY() > bot.getTranslateY()) {
				b.setTranslateY(bot.getTranslateY() - b.getRadiusY());
				byd = -Math.abs(byd);
			}

			if (b.getTranslateX() - bxd - b.getRadiusX() < p1.getTranslateX() + p1.getWidth()
					&& b.getTranslateY() + byd - b.getRadiusY() > p1.getTranslateY()
					&& b.getTranslateY() + byd + b.getRadiusY() < p1.getTranslateY() + p1.getHeight()) {
				bxd = Math.abs(bxd);
			}

			if (b.getTranslateX() + bxd + b.getRadiusX() > p2.getTranslateX()
					&& b.getTranslateY() + byd + b.getRadiusY() > p2.getTranslateY()
					&& b.getTranslateY() + byd + b.getRadiusY() < p2.getTranslateY() + p2.getHeight()) {
				bxd = -Math.abs(bxd);
			}
		}
	}

	private void startBall() {

		b.setTranslateX(300 - b.getRadiusX() / 2);
		b.setTranslateY(250 - b.getRadiusY() / 2);

		double start = Math.random() * 360;

		bxd = Math.cos(start) * ballSpeed;
		byd = Math.sin(start) * ballSpeed;

		ballMoving = true;
	}

	private void addKeyPressing(String code) {

		if (!keyPressing.contains(code)) {
			keyPressing.add(code);
		}
	}

	@Override
	public void start(Stage stg) {
		stck = new StackPane();
		stck.setBackground(null);
		stck.getChildren().add(playground);
		scn = new Scene(stck, 666, 666, true);
		scn.setFill(Color.rgb(40, 6, 60));

		stg.setScene(scn);
		stg.setTitle("Pong");
		stg.getIcons().add(new Image(getClass().getResourceAsStream("../img/icon.png")));

		scn.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.F11) {
				stg.setFullScreen(!stg.isFullScreen());
			} else if (e.getCode() == KeyCode.SPACE) {
				ballMoving = !ballMoving;
			} else if (e.getCode() == KeyCode.ENTER) {
				startBall();
			} else {
				addKeyPressing(e.getCode() + "");
			}
		});

		scn.setOnKeyReleased(e -> {
			if (keyPressing.contains(e.getCode() + "")) {
				keyPressing.remove(e.getCode() + "");
			}
		});

		stg.show();
		time.play();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
