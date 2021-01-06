import com.mandziak.model.User;

public class StudentRegistration extends DB {
    
    public int registerStudent(User user) {
        if(dbGetUser(user) == null) {
            dbAddUser(user);
            return 0;
        }
        return 1;
    }
}
