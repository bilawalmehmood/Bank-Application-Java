
package deposit;

import com.jfoenix.controls.JFXTextField;
import deshboard.DeshBoardController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import login.LoginFXMLController;


public class DepositAmountController implements Initializable {
    
    @FXML
    private Label balance;
    @FXML
    private Label account_No;
    @FXML
    private JFXTextField amount_tf;
    @FXML
    private PasswordField pin_tf;
    @FXML
    private Button depositBtn;
    
   ;
    
    Calendar cal=Calendar.getInstance();
    int year=cal.get(Calendar.YEAR);
    int month=cal.get(Calendar.MONTH);
    int day=cal.get(Calendar.DAY_OF_MONTH);
    int hour=cal.get(Calendar.HOUR);
    int minutes=cal.get(Calendar.MINUTE);
    int seconds=cal.get(Calendar.SECOND);
    int daynight=cal.get(Calendar.AM_PM);
    
    DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
    Date d=new Date();
    String date=dateFormat.format(d);
    
    LocalTime localTime=LocalTime.now();
    DateTimeFormatter dt=DateTimeFormatter.ofPattern("hh:mm:ss a");
    String time=localTime.format(dt);
    
    
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
    

    
    public void depositAmount(MouseEvent event){
        
            Connection con=null;
            PreparedStatement ps=null;
            ResultSet rs=null;
            
            
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","jatt786");
                String sql=" SELECT* FROM userdata WHERE Account_NO=? and PIN=?";
                ps=con.prepareStatement(sql);
                
                ps.setString(1,LoginFXMLController.acc);
                ps.setString(2, pin_tf.getText());
                
                rs=ps.executeQuery();
                if(rs.next()){
                   int da=Integer.parseInt(amount_tf.getText());
                   int ta=Integer.parseInt(balance.getText());
                   if(da<0){
                        Alert a=new Alert(Alert.AlertType.ERROR);
                        a.setTitle("ERROR");
                        a.setHeaderText("ERROR IN Deposit ");
                        a.setContentText(" YOUR ENTER AMOUNT IS NOT VALID TO DEPOSIT FOR MONEY\n SO TRY AGAIN WTH VALID AMOUNT");
                        a.showAndWait();
                   }
                   else{
                       int total=ta+da;
                      
                       String sql1=" UPDATE userdata SET Balance='"+total+"' WHERE Account_NO='"+LoginFXMLController.acc+"'";
                       ps=con.prepareStatement(sql1);
                       ps.execute();
                       
                       String sql2="INSERT INTO deposit(Name,Account_NO,Deposit_Amount,New_Amount,Date,Time) VALUES(?,?,?,?,?,?)";
                       ps=con.prepareStatement(sql2);

                       ps.setString(1,DeshBoardController.dbName );
                       ps.setString(2,LoginFXMLController.acc );
                       ps.setString(3,String.valueOf(da) );
                       ps.setString(4,String.valueOf(total));
                       ps.setString(5,date );
                       ps.setString(6,time );
                       
                       int i=ps.executeUpdate();
                       if(i>0){
                            Alert a=new Alert(Alert.AlertType.INFORMATION);
                            a.setTitle("Amount Deposit");
                            a.setHeaderText("IN YOUR ACCOUNT Amount Deposit Succesfully" );
                            a.setContentText("Amount :"+da+" has Been Succesfully Deposit\n Your Current Balance Is :"+total);
                            a.showAndWait();
                            
                            amount_tf.setText("");
                            pin_tf.setText("");
                            balance.setText(String.valueOf(total));
                       }
                       
                   }
                    
                }
                else{
                   
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setTitle("Amount Not Deposit");
                a.setHeaderText("PLEASE ENTER RIGHT PIN " );
                a.setContentText("YOUR ACCOUNT HAS BEEN NOT Been Deposit . Becouse YOU Have Enter Wrong  PIN");
                a.showAndWait();
                
                }
                
            }catch(Exception e){
                     Alert a=new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setHeaderText("AMOUNT NOT Deposit Succesfully ");
                a.setContentText("YOUR AMOUNT IS NOT DEPOSIT  BECOUSE HAS SOME EXCEPTION :" +e.getMessage());
                a.showAndWait();
              
            }
   }
    
     

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setData();
    }    
    
}
