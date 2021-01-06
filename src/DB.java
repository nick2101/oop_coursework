import com.mandziak.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
    private final String USER_TABLE = "users";
    private final String USER_ID = "user_id";
    private final String USER_FIRSTNAME = "first_name";
    private final String USER_LASTNAME = "last_name";
    private final String USER_PASSWORD = "password";
    private final String USER_GROUP = "group_name";
    private final String USER_MARK = "mark";

    private final String MATERIAL_TABLE = "material";
    private final String MATERIAL_ID = "idmat";
    private final String MATERIAL_ENABLE = "enable";
    private final String MATERIAL_TITLE = "title";
    private final String MATERIAL_TEXT = "text";
    private final String MATERIAL_Q1 = "q1";
    private final String MATERIAL_A1 = "a1";
    private final String MATERIAL_Q2 = "q2";
    private final String MATERIAL_A2 = "a2";
    private final String MATERIAL_Q3 = "q3";
    private final String MATERIAL_A3 = "a3";

    private final String MARK_TABLE = "marks";
    private final String MARK_ID = "idm";
    private final String MARK_FN = "studentfn";
    private final String MARK_LN = "studentln";
    private final String MARK_TEST = "test";
    private final String MARK_MARK = "mark";

    private Connection getDbConnection()
    {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/course_oop?characterEncoding=UTF-8&useSSL=false", "root", "12345678");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    public User dbGetUser(User user) {
        try {
            String select = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_FIRSTNAME + "=? AND " + USER_LASTNAME + "=?";
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String password = resultSet.getString(4);
                String group = resultSet.getString(5);
                user = new User(id, firstName, lastName, password, group);
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public User dbGetCurrentUser() {
        try {
            String select = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_ID + "=" + User.getCurrentUser();
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String password = resultSet.getString(4);
                String group = resultSet.getString(5);
                User user = new User(id, firstName, lastName, password, group);
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    protected void dbAddUser(User user)
    {
        try {
            String insert = "INSERT INTO " + USER_TABLE + " (" + USER_FIRSTNAME + "," + USER_LASTNAME + "," + USER_PASSWORD + "," + USER_GROUP + "," + USER_MARK + ") VALUES (?,?,?,?,?)";
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getFirstName());
            prSt.setString(2, user.getLastName());
            prSt.setString(3, user.getPassword());
            prSt.setString(4, user.getGroup());
            prSt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected Lecture dbGetLectureByID() {
        try {
            String select = "SELECT * FROM " + MATERIAL_TABLE + " WHERE " + MATERIAL_ID + "=" + Lecture.getCurrentLecture();
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                int enable = resultSet.getInt(2);
                String title = resultSet.getString(3);
                String text = resultSet.getString(4);
                String q1 = resultSet.getString(5);
                String a1 = resultSet.getString(6);
                String q2 = resultSet.getString(7);
                String a2 = resultSet.getString(8);
                String q3 = resultSet.getString(9);
                String a3 = resultSet.getString(10);
                Lecture lecture = new Lecture(id, enable, title, text, q1, a1, q2, a2, q3, a3);
                return lecture;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    protected Lecture dbGetLectureByTitle(Lecture lecture) {
        try {
            String select = "SELECT * FROM " + MATERIAL_TABLE + " WHERE " + MATERIAL_TITLE + "=?";
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, lecture.getTitle());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                int enable = resultSet.getInt(2);
                String title = resultSet.getString(3);
                String text = resultSet.getString(4);
                String q1 = resultSet.getString(5);
                String a1 = resultSet.getString(6);
                String q2 = resultSet.getString(7);
                String a2 = resultSet.getString(8);
                String q3 = resultSet.getString(9);
                String a3 = resultSet.getString(10);
                lecture = new Lecture(id, enable, title, text, q1, a1, q2, a2, q3, a3);
                return lecture;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    protected ObservableList dbGetAllLectureTitles(boolean bool) {
        ObservableList <String> lectures = FXCollections.observableArrayList();
        try {
            String select = "SELECT * FROM " + MATERIAL_TABLE;
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                if(bool == true) {
                    lectures.add(resultSet.getString(3));
                } else {
                    if(resultSet.getInt(2) == 1)
                        lectures.add(resultSet.getString(3));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lectures;
    }

    protected int dbGetAllLecturesCount() {
        int count = 0;
        try {
            String select = "SELECT * FROM " + MATERIAL_TABLE;
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                count++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    protected void dbAddEmptyLecture() {
        try {
            int lectionsCount = dbGetAllLecturesCount() + 1;
            String title = "Лекция " + lectionsCount;
            Lecture lecture = new Lecture(title);
            while(dbGetLectureByTitle(lecture) != null) {
                lectionsCount+=1;
                title = "Лекция " + lectionsCount;
                lecture = new Lecture(title);
            }
            String insert = "INSERT INTO " + MATERIAL_TABLE + " (" + MATERIAL_ENABLE + "," + MATERIAL_TITLE + ") VALUES (0,?)";
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, title);
            prSt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void dbSaveLecture(Lecture lecture) {
        try {
            String update = "UPDATE " + MATERIAL_TABLE + " SET " + MATERIAL_ENABLE + "=0, " + MATERIAL_TITLE + "=?, " + MATERIAL_TEXT + "=? WHERE (" + MATERIAL_ID + " = ?)";
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setString(1, lecture.getTitle());
            prSt.setString(2, lecture.getText());
            prSt.setInt(3, Lecture.getCurrentLecture());
            prSt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void dbDeleteLectureByID() {
        try {
            String delete = "DELETE FROM " + MATERIAL_TABLE + " WHERE (" + MATERIAL_ID + "=" + Lecture.getCurrentLecture() + ")";
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void dbSaveTestByID(Lecture lecture) {
        try {
            String update = "UPDATE " + MATERIAL_TABLE + " SET " + MATERIAL_ENABLE + "=?, " + MATERIAL_Q1 + "=?, " + MATERIAL_A1 + "=?, " + MATERIAL_Q2 + "=?, " + MATERIAL_A2 + "=?, " + MATERIAL_Q3 + "=?, " + MATERIAL_A3 + "=? WHERE (" + MATERIAL_ID + "= ?)";
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setInt(1, 1);
            prSt.setString(2, lecture.getQ1());
            prSt.setString(3, lecture.getA1());
            prSt.setString(4, lecture.getQ2());
            prSt.setString(5, lecture.getA2());
            prSt.setString(6, lecture.getQ3());
            prSt.setString(7, lecture.getA3());
            prSt.setInt(8, Lecture.getCurrentLecture());
            prSt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected Mark dbGetMark(Mark markm) {
        try {
            String select = "SELECT * FROM " + MARK_TABLE + " WHERE " + MARK_FN + "=? AND " + MARK_LN + "=? AND " + MARK_TEST + "=?";
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, markm.getFN());
            preparedStatement.setString(2, markm.getLN());
            preparedStatement.setString(3, markm.getTest());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                String fn = resultSet.getString(2);
                String ln = resultSet.getString(3);
                String title = resultSet.getString(4);
                int mark = resultSet.getInt(5);
                markm = new Mark(id, fn, ln, title, mark);
                return markm;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    protected void dbAddMark(Mark markm) {
        try {
            String insert = "INSERT INTO " + MARK_TABLE + " (" + MARK_FN + ", " + MARK_LN + ", " + MARK_TEST + ", " + MARK_MARK + ") VALUES (?,?,?,?)";
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, markm.getFN());
            prSt.setString(2, markm.getLN());
            prSt.setString(3, markm.getTest());
            prSt.setInt(4, markm.getMark());
            prSt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void dbChangeMark(Mark mark) {
        try {
            Mark hMark = dbGetMark(mark);
            String update = "UPDATE " + MARK_TABLE + " SET " + MARK_MARK + " = ? WHERE (" + MARK_ID + "= ?)";
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setInt(1, mark.getMark());
            prSt.setInt(2, hMark.getID());
            prSt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void dbChangeMarkTitle(Lecture lecture) {
        try {
            String update = "UPDATE " + MARK_TABLE + " SET " + MARK_TEST + " = ? WHERE (" + MARK_TEST + "= ?)";
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setString(1, lecture.getTitle());
            prSt.setString(2, dbGetLectureByID().getTitle());
            prSt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void alert(String text) {
        System.out.println(text);
    }

}
