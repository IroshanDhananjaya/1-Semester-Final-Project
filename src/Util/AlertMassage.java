package Util;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class AlertMassage {
    public void successMassage(String text){
        Image image = new Image(("/Assets/succecs.png"));
        Notifications notifications=Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text(text);
        notifications.title("Confirmation Massage");
        notifications.hideAfter(Duration.seconds(3));
        notifications.position(Pos.TOP_CENTER);
        notifications.show();
    }
    public void errorMassage(String text){
        Image image = new Image(("/Assets/error.png"));
        Notifications notifications=Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text(text);
        notifications.title("Error Massage");
        notifications.hideAfter(Duration.seconds(3));
        notifications.position(Pos.TOP_CENTER);
        notifications.show();
    }
}
