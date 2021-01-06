import com.mandziak.model.User;

public class KnowledgeControl extends DB{
    
    public void control(Lecture answers) {
        Lecture correctAnswers = dbGetLectureByID();
        int mark = 0;
        if(answers.getA1().equals(correctAnswers.getA1()))
            mark += 34;
        if(answers.getA2().equals(correctAnswers.getA2()))
            mark += 33;
        if(answers.getA3().equals(correctAnswers.getA3()))
            mark += 33;
        
        User user = dbGetCurrentUser();
        String fn = user.getFirstName();
        String ln = user.getLastName();
        String title = correctAnswers.getTitle();
        
        Mark markm = new Mark(fn, ln, title, mark);
        if(dbGetMark(markm) == null) {
            dbAddMark(markm);
            alert("" + mark);
        } else {
            dbChangeMark(markm);
            alert("" + dbGetMark(markm).getMark());
        }
    }
    
    public void setMinMark() {
        User user = dbGetCurrentUser();
        Lecture lecture = dbGetLectureByID();
        String fn = user.getFirstName();
        String ln = user.getLastName();
        String title = lecture.getTitle();
        Mark markm = new Mark(fn, ln, title, 0);
        if(dbGetMark(markm) == null) {
            dbAddMark(markm);
        }
    }
    
}
