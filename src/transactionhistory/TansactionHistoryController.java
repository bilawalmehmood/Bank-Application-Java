
package transactionhistory;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import login.LoginFXMLController;


public class TansactionHistoryController implements Initializable {
    
    Connection con=null;
    PreparedStatement ps=null;
    ResultSet rs=null;

    @FXML
    private TableView<History> withdraw_table;
    @FXML
    private TableColumn<History, String> name;
    @FXML
    private TableColumn<History,String> account_no;
    @FXML
    private TableColumn<History, String> withdraw_amt;
    @FXML
    private TableColumn<History, String> withdraw_remain_amt;
    @FXML
    private TableColumn<History, String> withdraw_date;
    @FXML
    private TableColumn<History, String> withdraw_time;
    
    @FXML
    private TableView<History> deposit_table;
    @FXML
    private TableColumn<History, String> deposit_name;
    @FXML
    private TableColumn<History, String> deposit_account_no;
    @FXML
    private TableColumn<History, String> deposit_amt;
    @FXML
    private TableColumn<History, String> deposit_new_amt;
    @FXML
    private TableColumn<History, String> deposit_date;
    @FXML
    private TableColumn<History, String> deposit_time;
    
    @FXML
    private TableView<History> transfer_table;
    @FXML
    private TableColumn<History, String> transfer_name;
    @FXML
    private TableColumn<History, String> transfer_account_no;
    @FXML
    private TableColumn<History, String> transfer_amt;
    @FXML
    private TableColumn<History, String> transfer_send_to;
    @FXML
    private TableColumn<History, String> transfer_date;
    @FXML
    private TableColumn<History, String> transfer_time;
    
    @FXML
    private TableView<History> reciever_table;
    @FXML
    private TableColumn<History, String> reciever_name;
    @FXML
    private TableColumn<History, String> reciever_account_no;
    @FXML
    private TableColumn<History, String> reciever_amt;
    @FXML
    private TableColumn<History, String> reciever_recieve_from;
    @FXML
    private TableColumn<History, String> reciever_date;
    @FXML
    private TableColumn<History, String> reciever_time;

    public void withdraw(){
        
        name.setCellValueFactory(new PropertyValueFactory<History , String>("name"));
        account_no.setCellValueFactory(new PropertyValueFactory<History , String>("account"));
        withdraw_amt.setCellValueFactory(new PropertyValueFactory<History , String>("amount"));
        withdraw_remain_amt.setCellValueFactory(new PropertyValueFactory<History , String>("genric"));
        withdraw_date.setCellValueFactory(new PropertyValueFactory<History , String>("date"));
        withdraw_time.setCellValueFactory(new PropertyValueFactory<History , String>("time"));
        
        ObservableList<History> list1=FXCollections.observableArrayList();
        
            
            
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","jatt786");
            
            String sql=" SELECT* FROM withdraw WHERE Account_NO=?";
            ps=con.prepareStatement(sql);

            ps.setString(1,LoginFXMLController.acc );

            rs=ps.executeQuery();
            
            while(rs.next()){
               
               list1.add(new History(rs.getString("Name"),rs.getString("Account_NO"),rs.getString("Withdraw_Amount"),rs.getString("Remaining_Amount"),rs.getString("Date"),rs.getString("Time")));

            } 
        }catch(Exception e){
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("ERROR In Withdraw Histry");
            a.setContentText("YOUR Data not show Withdraw History . BECOUSE HAS SOME EXCEPTION :" +e.getMessage());
            a.showAndWait();

        }
        
        withdraw_table.setItems(list1);
        
    }
    
    public void deposit(){
        
        deposit_name.setCellValueFactory(new PropertyValueFactory<History , String>("name"));
        deposit_account_no.setCellValueFactory(new PropertyValueFactory<History , String>("account"));
        deposit_amt.setCellValueFactory(new PropertyValueFactory<History , String>("amount"));
        deposit_new_amt.setCellValueFactory(new PropertyValueFactory<History , String>("genric"));
        deposit_date.setCellValueFactory(new PropertyValueFactory<History , String>("date"));
        deposit_time.setCellValueFactory(new PropertyValueFactory<History , String>("time"));
        
        ObservableList<History> list1=FXCollections.observableArrayList();
        
            
            
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","jatt786");
            
            String sql=" SELECT* FROM deposit WHERE Account_NO=?";
            ps=con.prepareStatement(sql);

            ps.setString(1,LoginFXMLController.acc );

            rs=ps.executeQuery();
            
            while(rs.next()){
               
               list1.add(new History(rs.getString("Name"),rs.getString("Account_NO"),rs.getString("Deposit_Amount"),rs.getString("New_Amount"),rs.getString("Date"),rs.getString("Time")));

            } 
        }catch(Exception e){
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("ERROR In Deposit Histry");
            a.setContentText("YOUR Data not show Deposit History . BECOUSE HAS SOME EXCEPTION :" +e.getMessage());
            a.showAndWait();

        }
        
        deposit_table.setItems(list1);
        
    }
    
     public void transfer(){
        
        transfer_name.setCellValueFactory(new PropertyValueFactory<History , String>("name"));
        transfer_account_no.setCellValueFactory(new PropertyValueFactory<History , String>("account"));
        transfer_amt.setCellValueFactory(new PropertyValueFactory<History , String>("amount"));
        transfer_send_to.setCellValueFactory(new PropertyValueFactory<History , String>("genric"));
        transfer_date.setCellValueFactory(new PropertyValueFactory<History , String>("date"));
        transfer_time.setCellValueFactory(new PropertyValueFactory<History , String>("time"));
        
        ObservableList<History> list1=FXCollections.observableArrayList();
        
            
            
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","jatt786");
            
            String sql=" SELECT* FROM transferamount WHERE Sender_Account_NO=?";
            ps=con.prepareStatement(sql);

            ps.setString(1,LoginFXMLController.acc );

            rs=ps.executeQuery();
            
            while(rs.next()){
               
               list1.add(new History(rs.getString("Sender_Name"),rs.getString("Sender_Account_NO"),rs.getString("Transfer_Amount"),rs.getString("Reciever_Account_NO"),rs.getString("Date"),rs.getString("Time")));

            } 
        }catch(Exception e){
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("ERROR In Deposit Histry");
            a.setContentText("YOUR Data not show Deposit History . BECOUSE HAS SOME EXCEPTION :" +e.getMessage());
            a.showAndWait();

        }
        
        transfer_table.setItems(list1);
        
    }
     
     public void reciever(){
        
        reciever_name.setCellValueFactory(new PropertyValueFactory<History , String>("name"));
        reciever_account_no.setCellValueFactory(new PropertyValueFactory<History , String>("account"));
        reciever_amt.setCellValueFactory(new PropertyValueFactory<History , String>("amount"));
        reciever_recieve_from.setCellValueFactory(new PropertyValueFactory<History , String>("genric"));
        reciever_date.setCellValueFactory(new PropertyValueFactory<History , String>("date"));
        reciever_time.setCellValueFactory(new PropertyValueFactory<History , String>("time"));
        
        ObservableList<History> list1=FXCollections.observableArrayList();
        
            
            
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","jatt786");
            
            String sql=" SELECT* FROM transferamount WHERE Reciever_Account_NO=?";
            ps=con.prepareStatement(sql);

            ps.setString(1,LoginFXMLController.acc );

            rs=ps.executeQuery();
            
            while(rs.next()){
               
               list1.add(new History(rs.getString("Reciever_Name"),rs.getString("Reciever_Account_NO"),rs.getString("Transfer_Amount"),rs.getString("Sender_Account_NO"),rs.getString("Date"),rs.getString("Time")));

            } 
        }catch(Exception e){
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("ERROR In Deposit Histry");
            a.setContentText("YOUR Data not show Deposit History . BECOUSE HAS SOME EXCEPTION :" +e.getMessage());
            a.showAndWait();

        }
        
        reciever_table.setItems(list1);
        
    }
     
     @FXML
    public void deleteDepositHistory(MouseEvent event){
        
        Connection con=null;
        PreparedStatement ps=null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","jatt786");
            
            String sql=" DELETE FROM deposit WHERE Account_NO=?";
            ps=con.prepareStatement(sql);
            
            ps.setString(1, LoginFXMLController.acc );
            
            int p=ps.executeUpdate();
            
            if(p>0){
            Alert a=new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("HISTORY DELETE");
            a.setContentText("HISTORY has been deleted succesfully ");
            a.showAndWait();
            
            }
            
        }catch(Exception e){
            
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Exceptin :");
            a.setContentText("Exception :"+e.getMessage());
            a.showAndWait();   
        }  
    }
    
    @FXML
    public void deleteWithdrawHistory(MouseEvent event){
        
        Connection con=null;
        PreparedStatement ps=null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","jatt786");
            
            String sql=" DELETE FROM withdraw WHERE Account_NO=?";
            ps=con.prepareStatement(sql);
            
            ps.setString(1, LoginFXMLController.acc );
            
            int p=ps.executeUpdate();
            
            if(p>0){
            Alert a=new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("HISTORY DELETE");
            a.setContentText("HISTORY has been deleted succesfully ");
            a.showAndWait();
            
            }
            
        }catch(Exception e){
            
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Exceptin :");
            a.setContentText("Exception :"+e.getMessage());
            a.showAndWait();   
        }  
    }
   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         withdraw();
         deposit();
         transfer();
         reciever();
    }    
    
}
