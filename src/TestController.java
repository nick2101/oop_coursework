import java.net.URL;
import java.util.ResourceBundle;

import com.mandziak.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TestController implements Initializable {
    
    @FXML
    private Label titleLabel;
    
    @FXML
    private Label q1Label;
    
    @FXML
    private TextField q1Field;
    
    @FXML
    private Label a1Label;
    
    @FXML
    private TextField a1Field;
    
    @FXML
    private Label q2Label;
    
    @FXML
    private TextField q2Field;
    
    @FXML
    private Label a2Label;
    
    @FXML
    private TextField a2Field;
    
    @FXML
    private Label q3Label;
    
    @FXML
    private TextField q3Field;
    
    @FXML
    private Label a3Label;
    
    @FXML
    private TextField a3Field;
    
    @FXML
    private Button endButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StudentRegistration dbHandler = new StudentRegistration();
        User user = dbHandler.dbGetCurrentUser();
        
        endButton.setOnAction(event -> {
            if(endButton.getText().equals("Сохранить")) {
                saveTest();
            } else{
                endTest();
            }
            endButton.getScene().getWindow().hide();
        });
        
        loadScreen(user);
        
    }
    
    private void endTest() {
        String answer1 = a1Field.getText();
        String answer2 = a2Field.getText();
        String answer3 = a3Field.getText();
        KnowledgeControl knowledgeControl = new KnowledgeControl();
        Lecture answers = new Lecture(answer1, answer2, answer3);
        knowledgeControl.control(answers);
    }
    
    private int saveTest() {
        InputMaterial inputMaterial = new InputMaterial();
        String q1Text = q1Field.getText();
        String a1Text = a1Field.getText();
        String q2Text = q2Field.getText();
        String a2Text = a2Field.getText();
        String q3Text = q3Field.getText();
        String a3Text = a3Field.getText();
        if(a2Text.equals("") && !a3Text.equals("")) {
            alert("Введите вопрос №2 или удалите вопрос №3");
            return 1;
        }
        Lecture lecture = new Lecture(q1Text, a1Text, q2Text, a2Text, q3Text, a3Text);
        inputMaterial.changeTest(lecture);
        return 0;
    }
    private int i = 0;
    private void loadScreen(User user) {
        DB dbHandler = new DB();
        Lecture lecture = dbHandler.dbGetLectureByID();
        titleLabel.setText(lecture.getTitle());
        q1Field.setText(lecture.getQ1());
        q2Field.setText(lecture.getQ2());
        q3Field.setText(lecture.getQ3());
        if(user.getGroup().equals("admin")) {
            a1Field.setText(lecture.getA1());
            a2Field.setText(lecture.getA2());
            a3Field.setText(lecture.getA3());
            q1Field.setEditable(true);
            a1Field.setEditable(true);
            q2Field.setEditable(true);
            a2Field.setEditable(true);
            q3Field.setEditable(true);
            a3Field.setEditable(true);
            endButton.setText("Сохранить");
        } else {
            q1Field.setEditable(false);
            q2Field.setEditable(false);
            q3Field.setEditable(false);
            if(lecture.getA2().equals("")) {
                q2Label.setVisible(false);
                q2Field.setVisible(false);
                a2Label.setVisible(false);
                a2Field.setVisible(false);
            }
            if(lecture.getA3().equals("")) {
                q3Label.setVisible(false);
                q3Field.setVisible(false);
                a3Label.setVisible(false);
                a3Field.setVisible(false);
            }
            endButton.setText("Закончить тетстирование");
        }
    }
    
    private void alert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
