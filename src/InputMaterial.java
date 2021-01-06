public class InputMaterial extends DB{
    
    public void addLecture() {
        dbAddEmptyLecture();
    }
    
    public int changeLecture(Lecture newLecture) {
        Lecture currentLecture = dbGetLectureByTitle(newLecture);
        if(currentLecture != null) {
            if(currentLecture.getID() == Lecture.getCurrentLecture()) {
                dbChangeMarkTitle(newLecture);
                dbSaveLecture(newLecture);
                return 0;
            } else {
                alert("Название лекции уже используется");
                return 1;
            }
        } else {
            dbChangeMarkTitle(newLecture);
            dbSaveLecture(newLecture);
            return 0;
        }
    }
    
    public void deleteLecture() {
        dbDeleteLectureByID();
    }
    
    public void changeTest(Lecture lecture) {
        dbSaveTestByID(lecture);
    }
    
}