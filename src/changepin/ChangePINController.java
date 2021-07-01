
package changepin;

import deshboard.DeshBoardController;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import login.LoginFXMLController;

public class ChangePINController implements Initializable {

    @FXML
    private Label balance;
    @FXML
    private Label account_No;
    @FXML
    private Button changePinBtn;
    @FXML
    private PasswordField oldPin_tf;
    @FXML
    private PasswordField newPin_tf;
    @FXML
    private PasswordField retypeNewPin_tf;
    
    
    DeshBoardController desh=new DeshBoardController();
    
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

     public void changePin(MouseEvent event){
            Connection con=null;
            PreparedStatement ps=null;
            ResultSet rs=null;
            
            
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","jatt786");
                String sql=" SELECT* FROM userdata WHERE Account_NO=? and PIN=?";
                ps=con.prepareStatement(sql);
                
                ps.setString(1,LoginFXMLController.acc);
                ps.setString(2, oldPin_tf.getText());
                
                rs=ps.executeQuery();
                
                if(rs.next()){
                    if((newPin_tf.getText()).equals(retypeNewPin_tf.getText())){
                       
                        String sql1=" UPDATE userdata SET PIN='"+newPin_tf.getText()+"' WHERE Account_NO='"+LoginFXMLController.acc+"'";
                        ps=con.prepareStatement(sql1);
                        ps.execute();

                        Alert a=new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("PIN Change");
                        a.setHeaderText(" PIN  Change Succesfully" );
                        a.setContentText("YOUR ACCOUNT PIN Has Been Change Succesfully . Now You Can LogIn With New PIN");
                        a.showAndWait();    

                        oldPin_tf.setText("");
                        newPin_tf.setText("");
                        retypeNewPin_tf.setText("");
                        
                        desh.logout(event);
                       
                   }
                    else{
                        
                        Alert a=new Alert(Alert.AlertType.ERROR);
                        a.setTitle("NEW NOT MATCH");
                        a.setHeaderText("PLEASE ENTER SAME NEW AND RETYPE PIN " );
                        a.setContentText("YOUR ACCOUNT PIN HAS BEEN NOT CHANGE . Becouse YOU Have EnterNot Same PIN");
                        a.showAndWait();
                    }
                }
                else{
                   
                    Alert a=new Alert(Alert.AlertType.ERROR);
                    a.setTitle("PIN NOT CHANGE");
                    a.setHeaderText("PLEASE ENTER RIGHT OLD PIN " );
                    a.setContentText("YOUR ACCOUNT PIN HAS BEEN NOT CHANGE . Becouse YOU Have Enter Wrong Old PIN");
                    a.showAndWait();
                
                }
                
            }catch(Exception e){
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setHeaderText("PIN NOT CHANGE Succesfully ");
                a.setContentText("YOUR ACCOUNT PIN IS NOT MATCH.  BECOUSE HAS SOME EXCEPTION :" +e.getMessage());
                a.showAndWait();
              
            }
   }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       setData();
    }    

    
    
}
