public class StudentProgressControl extends DB {
    
    public int getMarkValue(Mark mark) {
        mark = dbGetMark(mark);
        if(mark != null)
            return mark.getMark();
        return -1;
    }
    
    public int getFinalMark() {
        
        return 0;
    }
    
}
