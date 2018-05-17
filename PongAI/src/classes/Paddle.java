package classes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Paddle extends Rectangle {

	public Paddle(boolean directionRight, Rectangle bounds, int height) {
		this.setWidth(12);
		this.setHeight(height);
		this.setFill(Color.ALICEBLUE);
		this.setStroke(Color.ALICEBLUE.darker());
		this.setStrokeType(StrokeType.INSIDE);
		this.setStrokeWidth(1.5);

		if (directionRight) {
			this.setTranslateX(0);
			this.setTranslateY((bounds.getHeight()-this.getHeight())/2);
		} else {
			this.setTranslateX(bounds.getWidth()-this.getWidth());
			this.setTranslateY((bounds.getHeight()-this.getHeight())/2);
		}
	}

}
