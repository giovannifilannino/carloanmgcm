package presentation.controller.utility;

import presentation.Main;
import javafx.scene.image.Image;

public class ImageGetter {

	public static Image getLogo(){
		return new Image(Main.class.getResourceAsStream("controller/utility/logo.png"));
	}
	
}
