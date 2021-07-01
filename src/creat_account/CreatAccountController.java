
package creat_account;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import deshboard.DeshBoardController;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import login.BankApplication;


public class CreatAccountController implements Initializable {

    @FXML
    private AnchorPane signUpPane;
    @FXML
    private TextField name;
    @FXML
    private TextField idCard;
    @FXML
    private TextField mobileNO;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private ComboBox<String> religion;
    @FXML
    private ComboBox<String> metirialStatus;
    @FXML
    private TextField adress;
    @FXML
    private TextField city;
    @FXML
    private DatePicker dob;
    @FXML
    private TextField accountNO;
    @FXML
    private TextField balance;
    @FXML
    private ComboBox<String> accountType;
    @FXML
    private TextField pin;
    @FXML
    private ComboBox<String> securityQ;
    @FXML
    private TextField answer;
    @FXML
    private FileChooser fileChooser=new FileChooser();
    private File file;
    private FileInputStream fis;
    @FXML
    private ImageView profilePic;
    @FXML
    private Button browser;
    @FXML
    private Button createAccount;
    @FXML
    private FontAwesomeIconView back;
    
    DeshBoardController desh=new DeshBoardController();
    
    ObservableList<String> list1=FXCollections.observableArrayList("male","Female","Other");
    ObservableList<String> list2=FXCollections.observableArrayList("Islam","Chrischun","Hindu","Other");
    ObservableList<String> list3=FXCollections.observableArrayList("Married","Un_Merried");
    ObservableList<String> list4=FXCollections.observableArrayList("Current","Other","Saving");
    ObservableList<String> list5=FXCollections.observableArrayList("What is your pet name ?","What is your childhood town");

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gender.setItems(list1);
        religion.setItems(list2);
        metirialStatus.setItems(list3);
        accountType.setItems(list4);
        securityQ.setItems(list5);
        accountNO.setText(String.valueOf(genrateRendomAccount()));
        accountNO.setEditable(false);
    } 
    
    public boolean validateName(){
        Pattern p=Pattern.compile("[a-zA-Z ]+");
        Matcher m=p.matcher(name.getText());
        if(m.find() && m.group().equals(name.getText())){
            return true;
        }
        else{
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Wrong Name");
            a.setHeaderText("Your Name Is Wrong  ");
            a.setContentText("PLEASE ENTER Right Charactor For Name ");
            a.showAndWait();
            return false;
        }     
    }
    
    public boolean validateMobileNumber(){
        Pattern p=Pattern.compile("[0-9]+");
        Matcher m=p.matcher(mobileNO.getText());
        if(m.find() && m.group().equals(mobileNO.getText())){
            return true;
        }
        else{
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Wrong Number");
            a.setHeaderText("Your Mobile Number Is Wrong  ");
            a.setContentText("PLEASE ENTER Right Charactor For Mobile Number ");
            a.showAndWait();
            return false;
        }
    }
    
    public boolean validateIdCardNumber(){
        Pattern p=Pattern.compile("[0-9]+");
        Matcher m=p.matcher(idCard.getText());
        if(m.find() && m.group().equals(idCard.getText())){
            return true;
        }
        else{
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Wrong Id Card Number");
            a.setHeaderText("Your Id Card Number Is Wrong  ");
            a.setContentText("PLEASE ENTER Right Charactor For Id Card Number ");
            a.showAndWait();
            return false;
        }
    }
    
    public boolean validateBalance(){
        Pattern p=Pattern.compile("[0-9]+");
        Matcher m=p.matcher(balance.getText());
        if(m.find() && m.group().equals(balance.getText())){
            return true;
        }
        else{
            Alert a=new Alert(Alert.AlertType.ERROR);
            a.setTitle("Wrong Balance");
            a.setHeaderText("Your balance Wrong  ");
            a.setContentText("PLEASE ENTER Right Charactor For Balance ");
            a.showAndWait();
            return false;
        }
    }
    
    public void clearField(){
        name.clear();
        idCard.clear();
        mobileNO.clear();
        gender.getSelectionModel().clearSelection();
        metirialStatus.getSelectionModel().clearSelection();
        religion.getSelectionModel().clearSelection();
        dob.getEditor().clear();
        city.clear();
        adress.clear();
        accountType.getSelectionModel().clearSelection();
        pin.clear();
        securityQ.getSelectionModel().clearSelection();
        balance.clear();
        answer.clear();
        accountNO.setText(String.valueOf(genrateRendomAccount()));
        
    }
    
    public int genrateRendomAccount(){
        Random ran=new Random();
        int num=ran.nextInt(898998)+(100000);
        return num;
    }

    @FXML
    private void hendalMouseEvents(MouseEvent event) throws IOException {
        if(event.getSource().equals(createAccount)){
            Connection con=null;
            PreparedStatement ps=null;
            
            if(validateName() && validateMobileNumber() && validateIdCardNumber() && validateBalance()){
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","jatt786");

                    String sql="INSERT INTO userdata(Name,Id_Card,Mobile_NO,Gender,Metrial_Status,Religion,DOB,City,Adress,Account_NO,Account_Type,PIN,Security_Question,Balance,Profile_pic,Answer) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    ps=con.prepareStatement(sql);

                    ps.setString(1,name.getText() );
                    ps.setString(2,idCard.getText() );
                    ps.setString(3,mobileNO.getText() );
                    ps.setString(4,gender.getValue() );
                    ps.setString(5,metirialStatus.getValue() );
                    ps.setString(6,religion.getValue() );
                    ps.setString(7,((TextField)dob.getEditor()).getText() );
                    ps.setString(8,city.getText() );
                    ps.setString(9,adress.getText() );
                    ps.setString(10,accountNO.getText() );
                    ps.setString(11,accountType.getValue() );
                    ps.setString(12,pin.getText() );
                    ps.setString(13,securityQ.getValue() );
                    ps.setString(14,balance.getText() );
                    fis=new FileInputStream(file);
                    ps.setBinaryStream(15,(InputStream)fis,(int)file.length());
                    ps.setString(16,answer.getText() );

                    int i=ps.executeUpdate();
                    if(i>0){
                        Alert a=new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Account Created");
                        a.setHeaderText("Account CREATED Succesfully ");
                        a.setContentText("YOUR ACCOUNT HAS BEEN CREATED Succesfully .YOU CAN NOW LOGIN WITH ACCOUNT NO AND PIN");
                        a.showAndWait();
                        
                        clearField();
                        desh.logout(event);
                    }
                    else{
                        Alert a=new Alert(Alert.AlertType.ERROR);
                        a.setTitle("ERROR Account Created");
                        a.setHeaderText("Account Not Craeted  ");
                        a.setContentText("YOUR ACCOUNT HAS BEEN NOT CREATED .PLEASE ENTER ALL DATA ");
                        a.showAndWait();
                    }

                }catch(Exception e){
                    Alert a=new Alert(Alert.AlertType.ERROR);
                    a.setTitle("ERROR");
                    a.setHeaderText("YOUR ACCOUNT NOT BE CREATED" );
                    a.setContentText("YOUR ACCOUNT IS NOT CREATED BECOUSE HAS SOME EXCEPTION :" +e.getMessage());
                    a.showAndWait();
                }
            }
            
        }
        if(event.getSource().equals(browser)){
            fileChooser.getExtensionFilters().add(new ExtensionFilter("Images Files","*.png","*.jpg"));
            file=fileChooser.showOpenDialog(null);
            if(file!=null){
                Image img=new Image(file.toURI().toString(),150,150,true,true);
                profilePic.setImage(img);
                profilePic.setPreserveRatio(true);
            }
        }
        
        if(event.getSource()==(back)){
            BankApplication.stage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/login/LoginFXML.fxml")));
        }
    }

   
}
