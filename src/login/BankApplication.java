
package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BankApplication extends Application {
    
    public static Stage stage=null;
    
    private double xOffSet=0.0;
    private double yOffSet=0.0;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginFXML.fxml"));
        
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.TRANSPARENT);
        
          root.setOnMousePressed((MouseEvent event) -> {
          xOffSet=event.getSceneX();
          yOffSet=event.getSceneY();
           
       });
       
        root.setOnMouseDragged((MouseEvent event) -> {
          stage.setX(event.getScreenX() -xOffSet);
          stage.setY(event.getScreenY() -yOffSet);
           
       });
        this.stage=stage;
        stage.setScene(scene);
        stage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
