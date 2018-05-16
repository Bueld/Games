package classes;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

	private BorderPane brdrpn;
	private Scene scn;
		
	

	@Override
	public void start(Stage stg) {
		brdrpn = new BorderPane();
		brdrpn.setBackground(null);
		scn = new Scene(brdrpn, 666, 666, true);
		scn.setFill(Color.rgb(40, 6, 60));

		stg.setScene(scn);
		stg.setTitle("List");
		stg.getIcons().add(new Image(getClass().getResourceAsStream("../img/icon.png")));

		scn.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.F11) {
					stg.setFullScreen(!stg.isFullScreen());
				}
			}
		});

		stg.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
