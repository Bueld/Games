package classes;

import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Ball extends Ellipse {

	private double bxd, byd;
	private boolean moving = false;
	private double ballSpeed, restartCooldown;

	public Ball(double radius, double speed) {
		this.setRadiusX(radius);
		this.setRadiusY(radius);
		this.setStrokeWidth(1);
		this.setStrokeType(StrokeType.INSIDE);
		this.setTranslateX(300 - radius / 2);
		this.setTranslateY(250 - radius / 2);

		ballSpeed = speed;
	}

	public int moveBall(Rectangle top, Rectangle bot, Rectangle p1, Rectangle p2, Rectangle bounds) {

		if (moving) {
			if (this.getTranslateY() + byd - this.getRadiusY() < top.getTranslateY() + top.getHeight()) {
				this.setTranslateY(top.getTranslateY() + top.getHeight() + this.getRadiusY());
				byd = Math.abs(byd);
			}
			if (this.getTranslateY() + byd + this.getRadiusY() > bot.getTranslateY()) {
				this.setTranslateY(bot.getTranslateY() - this.getRadiusY());
				byd = -Math.abs(byd);
			}
			if (this.getTranslateX() + bxd - this.getRadiusX() < p1.getTranslateX() + p1.getWidth()
					&& this.getTranslateY() + byd - this.getRadiusY() > p1.getTranslateY()
					&& this.getTranslateY() + byd + this.getRadiusY() < p1.getTranslateY() + p1.getHeight()) {
				bxd = Math.abs(bxd);
			}
			if (this.getTranslateX() + bxd + this.getRadiusX() > p2.getTranslateX()
					&& this.getTranslateY() + byd + this.getRadiusY() > p2.getTranslateY()
					&& this.getTranslateY() + byd + this.getRadiusY() < p2.getTranslateY() + p2.getHeight()) {
				bxd = -Math.abs(bxd);
			}
			this.setTranslateX(this.getTranslateX() + bxd);
			this.setTranslateY(this.getTranslateY() + byd);
		}
		if (restartCooldown >= 0) {
			restartCooldown--;
		}
		if (restartCooldown == 0) {
			moving = true;
		}
		return checkGoal(bounds);
	}

	private int checkGoal(Rectangle bounds) {
		if (this.getTranslateX() < 0) {
			reset(false, bounds);
			return 1;
		}
		if (this.getTranslateX() > bounds.getWidth()) {
			reset(false, bounds);
			return -1;
		}
		return 0;
	}

	public void reset(boolean move, Rectangle bounds) {

		this.setTranslateX(bounds.getWidth() / 2);
		this.setTranslateY(bounds.getHeight() / 2);

		double start = Math.random() * 360;

		bxd = Math.cos(start) * ballSpeed;
		byd = Math.sin(start) * ballSpeed;

		moving = move;
		restartCooldown = 100;
	}

	public void setMoving(boolean move) {
		moving = move;
	}

}
