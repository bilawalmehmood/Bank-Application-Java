
package login;

import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static login.BankApplication.stage;


public class LoginFXMLController implements Initializable {
    
   
    @FXML
    private Pane infoPane;
    @FXML
    private ImageView cancel;
    @FXML
    private Pane signInPane;
    @FXML
    private PasswordField tfPin;
    @FXML
    private Button signInBtn;
    @FXML
    private Button signUpBtn;
    @FXML
    private Label forgotLbl;
    @FXML
    private JFXTextField tfAccountNo;
    @FXML
    private Label back;
    @FXML
    private Pane forgotPasswordPane;
    @FXML
    private Label welcomeLbl;
    @FXML
    private ScrollPane signUpPane;
    @FXML
    private FontAwesomeIconView back2;
    @FXML
    private Button createAccountBtn;
    // forgot declirations
    @FXML
    private JFXTextField forgotAccountNo;
    @FXML
    private JFXTextField forgotAnswer;
    @FXML
    private Button recoverBtn;
    @FXML
    private ComboBox<String> forgotSecurityQ;
    ObservableList<String> list=FXCollections.observableArrayList("What is your pet name ?","What is your childhood");
    
     public static Stage stage=null;
     private double xOffSet=0.0;
     private double yOffSet=0.0;
     
     public static String acc;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      forgotSecurityQ.setItems(list);
    }    

    @FXML
    private void hendleMouseEvent(MouseEvent event) {
       if(event.getSource()==back ){  
            signInPane.toFront();
       }
       if(event.getSource()==cancel){  
            System.exit(0);
       }
       if(event.getSource().equals(forgotLbl)){
            forgotPasswordPane.toFront();
         }
       
       
    }

    @FXML
    private void hendalAcctionEvent(ActionEvent event) throws IOException {
         
        // SIGN UP ACCOUNT
        
         if(event.getSource().equals(signUpBtn)){
            Parent fxml=FXMLLoader.load(getClass().getResource("/creat_account/CreatAccountFXML.fxml"));
            signInPane.getChildren().removeAll();
            signInPane.getChildren().addAll(fxml);
        }
         
         // SIGN IN ACCOUNT
         
         if(event.getSource().equals(signInBtn)){
            Connection con=null;
            PreparedStatement ps=null;
            ResultSet rs=null;
            
            
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","jatt786");
                String sql=" SELECT* FROM userdata WHERE Account_NO=? and PIN=?";
                ps=con.prepareStatement(sql);
                
                ps.setString(1,tfAccountNo.getText() );
                ps.setString(2,tfPin.getText() );
                acc=tfAccountNo.getText();
                
                
                
                rs=ps.executeQuery();
                if(rs.next()){
                    ((Node)event.getSource()).getScene().getWindow().hide();
                    Parent root=FXMLLoader.load(getClass().getResource("/deshboard/DeshBoardFXML.fxml"));
                    Scene scene=new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/css/Design.css").toExternalForm());
                    Stage stage =new Stage(); 
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(scene);
                    stage.show();
                    this.stage=stage;
                    root.setOnMousePressed((MouseEvent event1) -> {
                        xOffSet=event1.getSceneX();
                        yOffSet=event1.getSceneY();
           
       });
       
                    root.setOnMouseDragged((MouseEvent event1) -> {
                        stage.setX(event1.getScreenX() -xOffSet);
                        stage.setY(event1.getScreenY() -yOffSet);
           
       });
                   
                }
                else{
                   
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setTitle("Account Not Login");
                a.setHeaderText("YOUR ACCOUNT NOT Login " );
                a.setContentText("YOUR ACCOUNT HAS BEEN NOT Been Login Succesfully .YOU Have Enter Wrong ACCOUNT NO AND PIN");
                a.showAndWait();
                
                }
                
            }catch(Exception e){
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setHeaderText("Account CREATED Succesfully ");
                a.setContentText("YOUR ACCOUNT IS NOT Login  BECOUSE HAS SOME EXCEPTION :" +e.getMessage());
                a.showAndWait();
                
            }
    
             
         }
         
         // RECOVER ACCOUNT PIN
         if(event.getSource().equals(recoverBtn)){
             boolean result2=chackRecover();
             if(result2){
                 System.out.println("recover Succesfully");
             }
             else{
                 System.out.println("recover NOT Succesfully");
             }
         }
         
         
         
    }
    
     private boolean chackRecover(){
            Connection con=null;
            PreparedStatement ps=null;
            ResultSet rs=null;
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","jatt786");
                String sql=" SELECT* FROM userdata WHERE Account_NO=? and Security_Question=? and Answer=?";
                ps=con.prepareStatement(sql);
                
                ps.setString(1,forgotAccountNo.getText() );
                ps.setString(2,forgotSecurityQ.getValue() );
                ps.setString(3,forgotAnswer.getText() );
                
                
                rs=ps.executeQuery();
                if(rs.next()){
                    Alert a=new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Account PIN Recovery");
                a.setHeaderText("Blow is your PIN ");
                a.setContentText("YOUR ACCOUNT :"+rs.getString("Account_NO")+" \n PIN   :"+ rs.getString("PIN"));
                a.showAndWait();
                return true;
                }
                else{
                   
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setHeaderText("Wrong Data " );
                a.setContentText("Please Try Again");
                a.showAndWait();
                return false;
                }
                
            }catch(Exception e){
                     Alert a=new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setHeaderText("ERROR in Account Recovery ");
                a.setContentText("YOUR ACCOUNT HAS BEEN NOT Recover .YOU Have Enter Wrong Data.  BECOUSE HAS SOME EXCEPTION :" +e.getMessage());
                a.showAndWait();
                return false;
            }
     }

    
    
}

    
