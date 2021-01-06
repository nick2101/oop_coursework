import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mandziak.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class KursController implements Initializable {
    
    @FXML
    private Label firstNameLabel;
    
    @FXML
    private TextField firstNameField;
    
    @FXML
    private Label firstNameIncorrectLabel;
    
    @FXML
    private Label lastNameLabel;
    
    @FXML
    private TextField lastNameField;
    
    @FXML
    private Label lastNameIncorrectLabel;
    
    @FXML
    private Label passwordLabel;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label passwordIncorrectLabel;
    
    @FXML
    private Label groupLabel;
    
    @FXML
    private TextField groupField;
    
    @FXML
    private Label groupIncorrectLabel;
    
    @FXML
    private Button signInButton;
    
    @FXML
    private Button signUpButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        firstNameField.setOnAction(event -> {
            if(groupLabel.isVisible() && groupField.isVisible() && groupIncorrectLabel.isVisible())
                signUp();
            else
                signIn();
        });
        
        lastNameField.setOnAction(event -> {
            if(groupLabel.isVisible() && groupField.isVisible() && groupIncorrectLabel.isVisible())
                signUp();
            else
                signIn();
        });
        
        passwordField.setOnAction(event -> {
            if(groupLabel.isVisible() && groupField.isVisible() && groupIncorrectLabel.isVisible())
                signUp();
            else
                signIn();
        });
        
        groupField.setOnAction(event -> {
            if(groupLabel.isVisible() && groupField.isVisible() && groupIncorrectLabel.isVisible())
                signUp();
            else
                signIn();
        });
        
        signInButton.setOnAction(event -> {
            if(groupLabel.isVisible() && groupField.isVisible() && groupIncorrectLabel.isVisible()) {
                groupLabel.setVisible(false);
                groupField.setVisible(false);
                groupIncorrectLabel.setVisible(false);
            }
            signIn();
        });
        
        signUpButton.setOnAction(event -> {
            signUp();
        });
    }
    
    private int signIn() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String password = passwordField.getText().trim();
        if(fieldsAreFilled() == true) {
            DB dbHandler = new DB();
            User user = new User(firstName, lastName, password);
            user = dbHandler.dbGetUser(user);
            if(user != null) {
                if(password.equals(user.getPassword())) {
                    User.setCurrentUser(user.getId());
                    signInButton.getScene().getWindow().hide();
                    openScene("page.fxml");
                } else {
                    passwordIncorrectLabel.setText("Пароль не верный");
                    return 3;
                }
            } else {
                lastNameIncorrectLabel.setText("Пользователь не найден");
                return 2;
            }
        } else {
            return 1;
        }
        return 0;
    }
    
    private int signUp() {
        if(groupLabel.isVisible() && groupField.isVisible() && groupIncorrectLabel.isVisible()) {
            if(fieldsAreFilled() == true) {
                StudentRegistration registration = new StudentRegistration();
                
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String password = passwordField.getText().trim();
                String group = groupField.getText().trim();
                int mark;
                if(group.equals("admin"))
                    mark = -2;
                else
                    mark = -1;
                User user = new User(firstName, lastName, password, group);
                int result = registration.registerStudent(user);
                switch (result) {
                    case 0:
                        groupIncorrectLabel.setText("Пользователь зарегистрирован");
                        return 0;
                    case 1:
                        lastNameIncorrectLabel.setText("Пользователь уже зарегистрирован");
                        return 2;
                }
            } else {
                return 1;
            }
        } else {
            groupLabel.setVisible(true);
            groupField.setVisible(true);
            groupIncorrectLabel.setVisible(true);
            return 3;
        }
        return 4;
    }
    
    private boolean fieldsAreFilled() {
        boolean result = true;
        if(firstNameField.getText().trim().equals("")) {
            firstNameIncorrectLabel.setText("Введите имя");
            result = false;
        } else {
            firstNameIncorrectLabel.setText("");
        }
        if(lastNameField.getText().trim().equals("")) {
            lastNameIncorrectLabel.setText("Введите фамилию");
            result = false;
        } else {
            lastNameIncorrectLabel.setText("");
        }
        if(passwordField.getText().trim().equals("")) {
            passwordIncorrectLabel.setText("Введите пароль");
            result = false;
        } else {
            passwordIncorrectLabel.setText("");
        }
        if(groupField.isVisible() && groupField.getText().trim().equals("")) {
            groupIncorrectLabel.setText("Введите группу");
            result = false;
        } else {
            groupIncorrectLabel.setText("");
        }
        return result;
    }
    
    private void openScene(String window) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(window));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(KursController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
