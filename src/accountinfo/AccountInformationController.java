
package accountinfo;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import login.LoginFXMLController;


public class AccountInformationController implements Initializable {

   
    @FXML
    private Text accountNo;
    @FXML
    private Text religion;
    @FXML
    private Text gender;
    @FXML
    private Text accountType;
    @FXML
    private Label balance;
    @FXML
    private AnchorPane accountInfoPane;
    @FXML
    private Label account_No;
    
    public void setData(){
            Connection con=null;
            PreparedStatement ps=null;
            ResultSet rs=null;
            
            
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","jatt786");
                String sql=" SELECT* FROM userdata WHERE Account_NO=?";
                ps=con.prepareStatement(sql);
                
                ps.setString(1,LoginFXMLController.acc );
                
                rs=ps.executeQuery();
                if(rs.next()){
                    balance.setText(rs.getString("Balance"));
                    account_No.setText(rs.getString("Account_NO"));
                    
                   
                    
                }
                else{
                   
                    Alert a=new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Data NOT Uploads");
                    a.setHeaderText("YOUR Data NOT Uploads on top  " );
                    a.setContentText("YOUR Data NOT Upload Succesfully .YOU Have Enter Wrong ACCOUNT NO OR PIN");
                    a.showAndWait();
                
                }
                
            }catch(Exception e){
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setHeaderText("ERROR In Data Uploads");
                a.setContentText("YOUR Data NOT Upload . BECOUSE HAS SOME EXCEPTION :" +e.getMessage());
                a.showAndWait();
              
            }
   }
    
    public void setInfo(){
            Connection con=null;
            PreparedStatement ps=null;
            ResultSet rs=null;
            
            
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","jatt786");
                String sql=" SELECT* FROM userdata WHERE Account_NO=?";
                ps=con.prepareStatement(sql);
                
                ps.setString(1,LoginFXMLController.acc );
                
                rs=ps.executeQuery();
                if(rs.next()){
           
                    accountNo.setText(rs.getString("Account_NO"));
                    religion.setText(rs.getString("Religion"));
                    gender.setText(rs.getString("Gender"));
                    accountType.setText(rs.getString("Account_Type"));
                   
                    
                }
                else{
                   
                    Alert a=new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Account ERROR");
                    a.setHeaderText("YOUR ACCOUNT SHOW DATA" );
                    a.setContentText("YOUR ACCOUNT HAS BEEN NOT Been NOT SHOW DATA Succesfully .YOU Have Enter Wrong ACCOUNT ");
                    a.showAndWait();
                
                }
                
            }catch(Exception e){
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setHeaderText("ERROR IN DATA");
                a.setContentText("YOUR ACCOUNT IS NOT SHOW DATA  BECOUSE HAS SOME EXCEPTION :" +e.getMessage());
                a.showAndWait();
              
            }
   }
   
   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setData();
        setInfo();
    }    
    
}
