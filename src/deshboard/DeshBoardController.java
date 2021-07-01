
package deshboard;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.BankApplication;

import login.LoginFXMLController;
import static login.LoginFXMLController.stage;
import transactionhistory.TansactionHistoryController;


public class DeshBoardController implements Initializable {

    @FXML
    private FontAwesomeIconView home;
    @FXML
    private FontAwesomeIconView cancel;
    @FXML
    private FontAwesomeIconView minmize;
   
    @FXML
    private Label name;
    @FXML
    private Circle profilePic;
    @FXML
    private Button accountInfo;
 
    @FXML
    private Pane dashBoardHomePane;
    @FXML
    private Label homeName;
    @FXML
    private Label body;
    
    @FXML
    private Button deposit;
    @FXML
    private Button withdraw;
    @FXML
    private Pane dashBoardWhitePane;
    @FXML
    private FontAwesomeIconView logout;
    @FXML
    private Button changePin;
    @FXML
    private Button transferMoney;
    @FXML
    private Button transactionHistory;
    
    public static Stage stage=null;
    
    public static String dbName;
    
    private double xOffSet=0.0;
    private double yOffSet=0.0;
    
    TansactionHistoryController th=new TansactionHistoryController();

    @FXML
    private void hendalMouseEvents(MouseEvent event) throws IOException {
        
        // Back To Home Screen Method
        
        if(event.getSource()==(home)){
      
            dashBoardHomePane.toFront();
        }
        
        // logout method
        
        if(event.getSource()==(logout)){
             
               logout(event);
        }
        
        // close the programe method
        
        if(event.getSource()==cancel){  
            
           System.exit(0);

        }

        // minimized the application method

        if(event.getSource()==minmize){  
            
            Stage stage=(Stage) minmize.getScene().getWindow();
            stage.setIconified(true);

        }

        // show account information on the deshboard method

        if(event.getSource()==accountInfo){  

            Parent fxml=FXMLLoader.load(getClass().getResource("/accountinfo/AccountInformationFXML.fxml"));
            dashBoardWhitePane.getChildren().removeAll();
            dashBoardWhitePane.getChildren().addAll(fxml);
            dashBoardWhitePane.toFront();

        }

        // show withdraw bar on deshboard method

        if(event.getSource()==withdraw){  

            Parent fxml=FXMLLoader.load(getClass().getResource("/withdraw/WithdrawAmountFXML.fxml"));
            dashBoardWhitePane.getChildren().removeAll();
            dashBoardWhitePane.getChildren().addAll(fxml);
            dashBoardWhitePane.toFront();

        }

        // show deposit bar on deshboaed method

        if(event.getSource()==deposit){ 

            Parent fxml=FXMLLoader.load(getClass().getResource("/deposit/DepositAmountFXML.fxml"));
            dashBoardWhitePane.getChildren().removeAll();
            dashBoardWhitePane.getChildren().addAll(fxml);
            dashBoardWhitePane.toFront();
        }
        
        // show change PIM bar on deshboard method
        
        if(event.getSource()==changePin){ 

            Parent fxml=FXMLLoader.load(getClass().getResource("/changepin/ChangePINFXML.fxml"));
            dashBoardWhitePane.getChildren().removeAll();
            dashBoardWhitePane.getChildren().addAll(fxml);
            dashBoardWhitePane.toFront();
        }
        
        // show Amout Transfer bar on deshboard method
        
        if(event.getSource()==transferMoney){ 

            Parent fxml=FXMLLoader.load(getClass().getResource("/amount_transfer/AmountTransferFXML.fxml"));
            dashBoardWhitePane.getChildren().removeAll();
            dashBoardWhitePane.getChildren().addAll(fxml);
            dashBoardWhitePane.toFront();
        }
        
        // show  Transfer History bar on deshboard method
        
        if(event.getSource()==transactionHistory){ 

            Parent fxml=FXMLLoader.load(getClass().getResource("/transactionhistory/TansactionHistoryFXML.fxml"));
            dashBoardWhitePane.getChildren().removeAll();
            dashBoardWhitePane.getChildren().addAll(fxml);
            dashBoardWhitePane.toFront();
        }
          
    
    
    }
    
    public void logout(MouseEvent event) throws IOException{
        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent root=FXMLLoader.load(getClass().getResource("/login/LoginFXML.fxml"));
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
    
    public void deleteAllDepositHistory(MouseEvent event){
        th.deleteDepositHistory(event);
    }
    public void deleteAllWithdrawHistory(MouseEvent event){
        th.deleteWithdrawHistory(event);
    }
    
    
    
    public void setData1(){
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;


        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","jatt786");
            String sql=" SELECT* FROM userdata WHERE Account_NO=?";
            ps=con.prepareStatement(sql);

            ps.setString(1,LoginFXMLController.acc);

            rs=ps.executeQuery();
            if(rs.next()){
                name.setText(rs.getString("Name"));
                homeName.setText(rs.getString("Name"));
                dbName=name.getText();


                InputStream is=rs.getBinaryStream("Profile_pic");
                OutputStream os=new FileOutputStream(new File("pic.jpg"));
                byte[] content=new byte[1024];
                int size=0;
                while((size=is.read(content)) != -1){
                    os.write(content, 0, size);
                }
                os.close();
                is.close();
                profilePic.setStroke(Color.WHITE);
                Image img=new Image("File:pic.jpg",false);
                profilePic.setFill(new ImagePattern(img));


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
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       body.setText("This is beatifull bank and trusted for client \n So please choose thi for better future becouse \n We have your care so come and get best oppertunaty");
       setData1();
      
    }   
   

    @FXML
    private void drag(MouseEvent event) {
         stage.setX(event.getScreenX() -xOffSet);
         stage.setY(event.getScreenY() -yOffSet);
    }

    @FXML
    private void press(MouseEvent event) {
        xOffSet=event.getSceneX();
        yOffSet=event.getSceneY();
    }
    
}
