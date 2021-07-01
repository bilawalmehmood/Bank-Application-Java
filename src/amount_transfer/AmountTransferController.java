
package amount_transfer;

import com.jfoenix.controls.JFXTextField;
import deshboard.DeshBoardController;
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


public class AmountTransferController implements Initializable {

    
     @FXML
    private Label balance;
    @FXML
    private Label account_No;
    @FXML
    private JFXTextField amount_tf;
    @FXML
    private PasswordField pin_tf;
    @FXML
    private JFXTextField recieverAccountNo_tf;
    @FXML
    private Button transferAmountBtn;
    @FXML
    private Button searchBtn;

    
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
    
    public void searchData(MouseEvent event){
        
            Connection con=null;
            PreparedStatement ps=null;
            ResultSet rs=null;
            
            
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","jatt786");
                String sql=" SELECT* FROM userdata WHERE Account_NO=?";
                ps=con.prepareStatement(sql);
                
                ps.setString(1,recieverAccountNo_tf.getText());
                
                rs=ps.executeQuery();
                if(rs.next()){
                    
                    Alert a=new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Data Search");
                    a.setHeaderText("This Account Hollder Data Is Blow" );
                    a.setContentText("Name :"+rs.getString("Name")+"\n Account NO :"+recieverAccountNo_tf.getText()+"\n Phone NO :"+rs.getString("Mobile_NO")+"\n Balance :"+rs.getString("Balance"));
                    a.showAndWait();
                
                   
                    
                }
                else{
                   
                    Alert a=new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Data NOT Show");
                    a.setHeaderText("YOUR Data NOT Be Show" );
                    a.setContentText("YOU Have Enter Wrong ACCOUNT NO OR PIN");
                    a.showAndWait();
                
                }
                
            }catch(Exception e){
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setHeaderText("ERROR In Data Search");
                a.setContentText("YOUR Data NOT Search. BECOUSE HAS SOME EXCEPTION :" +e.getMessage());
                a.showAndWait();
              
            }
   }
    

    
    @FXML
    public void transferAmount(MouseEvent event){
        
            
            Connection con=null;
            PreparedStatement ps=null;
            ResultSet rs=null;
            
            
            try{
                
                Class.forName("com.mysql.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","jatt786");
               
                // This using Quiry for Sender Acount Holder To Transfer Amount And Update Remaining Amount In DBS
                
                String sqll=" SELECT* FROM userdata WHERE Account_NO=? and PIN=?";
                ps=con.prepareStatement(sqll);
                
                
                ps.setString(1,LoginFXMLController.acc);
                ps.setString(2, pin_tf.getText());
                
                rs=ps.executeQuery();
               
                
                if(rs.next()){
                    int transfer_amt=Integer.parseInt(amount_tf.getText());
                    int sender_ta=Integer.parseInt(balance.getText());
                    if(transfer_amt>sender_ta){
                        Alert a=new Alert(Alert.AlertType.ERROR);
                        a.setTitle("ERROR");
                        a.setHeaderText("ERROR IN TRANSFER AMOUNT ");
                        a.setContentText("IN YOUR ACCOUNT IS NOT ENOUGH AMOUNT FOR TRANSFER SO TRY AGAIN");
                        a.showAndWait();
                    }
                   else if(transfer_amt<0){
                        Alert a=new Alert(Alert.AlertType.ERROR);
                        a.setTitle("ERROR");
                        a.setHeaderText("ERROR IN TRANSFER AMOUNT ");
                        a.setContentText(" YOUR ENTER AMOUNT IS NOT VALID TO TRANSFER AMOUNT \n SO TRY AGAIN WTH VALID AMOUNT");
                        a.showAndWait();
                   }
                   else{
                        int sender_total=sender_ta-transfer_amt;

                        String sql2=" UPDATE userdata SET Balance='"+sender_total+"' WHERE Account_NO='"+LoginFXMLController.acc+"'";
                        ps=con.prepareStatement(sql2);
                        ps.execute();
                        
                         // This using Quiry for Receiver Account Holder To Recive Amount And Update New Amount In DBS
                         
                        try{
                            String sql3=" SELECT* FROM userdata WHERE Account_NO=? ";
                            ps=con.prepareStatement(sql3);


                            ps.setString(1,recieverAccountNo_tf.getText());
                           

                            rs=ps.executeQuery();


                            if(rs.next()){
                                
                                int reciever_ta=Integer.parseInt(rs.getString("Balance"));
                                int reciever_total=reciever_ta+transfer_amt;

                                String sql4=" UPDATE userdata SET Balance='"+reciever_total+"' WHERE Account_NO='"+recieverAccountNo_tf.getText()+"'";
                                ps=con.prepareStatement(sql4);
                                ps.execute();

                                String sql5="INSERT INTO transferamount(Sender_Name,Sender_Account_NO,Reciever_Name,Reciever_Account_NO,Transfer_Amount,Date,Time) VALUES(?,?,?,?,?,?,?)";
                                ps=con.prepareStatement(sql5);

                                ps.setString(1,DeshBoardController.dbName );
                                ps.setString(2,LoginFXMLController.acc );
                                ps.setString(3,rs.getString("Name"));
                                ps.setString(4,recieverAccountNo_tf.getText() );
                                ps.setString(5,String.valueOf(transfer_amt) );
                                ps.setString(6,date );
                                ps.setString(7,time );

                                int i=ps.executeUpdate();
                                if(i>0){
                                     Alert a=new Alert(Alert.AlertType.INFORMATION);
                                     a.setTitle("Amount Transfered");
                                     a.setHeaderText(" Amount  Transfer Succesfully " );
                                     a.setContentText("Amount :"+transfer_amt+" has Been Transfer Succesfully \n in Account of Reciveer "+recieverAccountNo_tf.getText()+" \n your Account New Balance Is :"+sender_total+" \n Reciever Account New Balance Is :"+reciever_total);
                                     a.showAndWait();

                                     amount_tf.setText("");
                                     pin_tf.setText("");
                                     balance.setText(String.valueOf(sender_total));
                                }
                                            
                            
                            }
                        
                        }catch(Exception e){
                            Alert a=new Alert(Alert.AlertType.ERROR);
                            a.setTitle("ERROR");
                            a.setHeaderText("AMOUNT NOT Recive Succesfully ");
                            a.setContentText("YOUR AMOUNT IS NOT RECIEVE   BECOUSE HAS SOME EXCEPTION :" +e.getMessage());
                            a.showAndWait();

                        } 
                     
                   }
                    
                }
                else{
                   
                    Alert a=new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Amount Not Transfer");
                    a.setHeaderText("PLEASE ENTER YOUR RIGHT PIN " );
                    a.setContentText("YOUR ACCOUNT HAS BEEN NOT Been Transfer Succesfully .YOU Have Enter Your Wrong PIN");
                    a.showAndWait();
                
                }
                
            }catch(Exception e){
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setTitle("ERROR");
                a.setHeaderText("AMOUNT NOT TRANSFER Succesfully ");
                a.setContentText("YOUR AMOUNT IS NOT TRANSFER   BECOUSE HAS SOME EXCEPTION :" +e.getMessage());
                a.showAndWait();

                } 
   }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       setData();
    }    
    
}
