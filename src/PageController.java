import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mandziak.model.User;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PageController implements Initializable {
    
    @FXML
    private Label studentLabel;
    
    @FXML
    private Label firstNameLabel;
    
    @FXML
    private Label lastNameLabel;
    
    @FXML
    private Label groupLabel;
    
    @FXML
    private Button signOutButton;
    
    @FXML
    private ListView<String> lecturesList;
    
    @FXML
    private Button startTestButton;
    
    @FXML
    private Button saveLectureButton;
    
    @FXML
    private Button addLectureButton;
    
    @FXML
    private Button delLectureButton;
    
    @FXML
    private Button saveTestButton;
    
    @FXML
    private ListView<String> markTitlesList;
    
    @FXML
    private TextField markFNField;
    
    @FXML
    private TextField markLNField;
    
    @FXML
    private Label markLabel;
    
    @FXML
    private Button markButton;
    
    @FXML
    private TextArea titleArea;
    
    @FXML
    private TextArea textArea;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        saveLectureButton.setOnAction(event -> {
            InputMaterial inputMaterial = new InputMaterial();
            String title = titleArea.getText();
            String text = textArea.getText();
            Lecture lecture = new Lecture(title, text);
            int result = inputMaterial.changeLecture(lecture);
            if(result == 0)
                updateLectures(!groupLabel.isVisible());
        });
        
        addLectureButton.setOnAction(event -> {
            InputMaterial inputMaterial = new InputMaterial();
            inputMaterial.addLecture();
            updateLectures(!groupLabel.isVisible());
        });
        
        delLectureButton.setOnAction(event -> {
            InputMaterial inputMaterial = new InputMaterial();
            inputMaterial.deleteLecture();
            updateLectures(!groupLabel.isVisible());
            titleArea.setText("");
            textArea.setText("");
        });
        
        saveTestButton.setOnAction(event -> {
            openScene("test.fxml");
        });
        
        startTestButton.setOnAction(event -> {
            StudentRegistration dbHandler = new StudentRegistration();
            User user = dbHandler.dbGetCurrentUser();
            
            DB dbHandler1 = new DB();
            Lecture lecture = dbHandler1.dbGetLectureByID();
            String fn = user.getFirstName();
            String ln = user.getLastName();
            String title = lecture.getTitle();
            Mark markm = new Mark(fn, ln, title);
            KnowledgeControl dbHandler3 = new KnowledgeControl();
            markm = dbHandler3.dbGetMark(markm);
            if(markm != null) {
                alert("За этот тест уже стоит оценка: " + markm.getMark());
            } else {
                markm = new Mark(fn, ln, title, 0);
                dbHandler3.dbAddMark(markm);
                openScene("test.fxml");
            }
        });
        
        markButton.setOnAction(event -> {
            String fn;
            String ln;
            if(markFNField.isVisible() && markLNField.isVisible()) {
                fn = markFNField.getText();
                ln = markLNField.getText();
            } else {
                StudentRegistration dbHandler = new StudentRegistration();
                User user = dbHandler.dbGetCurrentUser();
                fn = user.getFirstName();
                ln = user.getLastName();
            }
            String test = Mark.getCurrentTest();
            if(fn.equals("") || ln.equals(""))
                alert("Введите имя и фамилию студента");
            Mark mark = new Mark(fn, ln, test);
            StudentProgressControl progressControl = new StudentProgressControl();
            if(progressControl.getMarkValue(mark) == -1)
                markLabel.setText("---");
            else
                markLabel.setText("" + progressControl.getMarkValue(mark));
        });
        
        lecturesList.getSelectionModel().selectedItemProperty().addListener( (ObservableValue<? extends String>observable, String oldValue, String newValue) -> {
            Lecture lecture = new Lecture();
            lecture.setTitle(newValue);
            DB dbHandler = new DB();
            lecture = dbHandler.dbGetLectureByTitle(lecture);
            if(lecture != null) {
                Lecture.setCurrentLecture(lecture.getID());
                titleArea.setText(lecture.getTitle());
                textArea.setText(lecture.getText());
            }
        });
        
        markTitlesList.getSelectionModel().selectedItemProperty().addListener( (ObservableValue<? extends String>observable, String oldValue, String newValue) -> {
            Mark.setCurrentTest(newValue);
        });
        
        loadScreen();
        
    }
    
    private void loadScreen() {
        StudentRegistration dbHandler = new StudentRegistration();
        User user = dbHandler.dbGetCurrentUser();
        firstNameLabel.setText(user.getFirstName());
        lastNameLabel.setText(user.getLastName());
        groupLabel.setText(user.getGroup());
        if(user.getGroup().equals("admin")) {
            studentLabel.setText("Преподаватель");
            groupLabel.setVisible(false);
            saveLectureButton.setVisible(true);
            addLectureButton.setVisible(true);
            delLectureButton.setVisible(true);
            saveTestButton.setVisible(true);
            startTestButton.setVisible(false);
            titleArea.setEditable(true);
            textArea.setEditable(true);
            markFNField.setVisible(true);
            markLNField.setVisible(true);
            updateLectures(true);
        } else {
            studentLabel.setText("Студент");
            groupLabel.setVisible(true);
            saveLectureButton.setVisible(false);
            addLectureButton.setVisible(false);
            delLectureButton.setVisible(false);
            saveTestButton.setVisible(false);
            startTestButton.setVisible(true);
            titleArea.setEditable(false);
            textArea.setEditable(false);
            markFNField.setVisible(false);
            markLNField.setVisible(false);
            updateLectures(false);
        }
        
    }
    
    private void updateLectures(boolean bool) {
        DB dbHandler = new DB();
        ObservableList<String> lectureTitles = dbHandler.dbGetAllLectureTitles(bool);
        lecturesList.setItems(lectureTitles);
        markTitlesList.setItems(lectureTitles);
    }
    
    private void alert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
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