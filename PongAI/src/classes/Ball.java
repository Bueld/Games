package classes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Ball extends Ellipse {

	public Ball(double radius) {
		this.setFill(Color.ALICEBLUE);
		this.setRadiusX(radius);
		this.setRadiusY(radius);
		this.setStroke(Color.ALICEBLUE.darker());
		this.setStrokeWidth(1);
		this.setTranslateX(300 - radius / 2);
		this.setTranslateY(250 - radius / 2);
	}
}
