package classes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

	public Paddle(boolean directionRight) {
		this.setWidth(12);
		this.setHeight(100);
		this.setFill(Color.ALICEBLUE);

		if (directionRight) {
			this.setTranslateX(0);
			this.setTranslateY(200);
		} else {
			this.setTranslateX(588);
			this.setTranslateY(200);
		}
	}

}
