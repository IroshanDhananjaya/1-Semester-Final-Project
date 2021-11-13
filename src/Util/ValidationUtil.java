package Util;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static Object validate(LinkedHashMap<javafx.scene.control.TextField,Pattern> map, Button btn) {
        btn.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()){
                    textFieldKey.setStyle("-fx-border-color: red");
                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-border-color: green");
        }
        btn.setDisable(false);
        return true;
    }
}
