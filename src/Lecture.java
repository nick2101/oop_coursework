public class Lecture {
    private int id;
    private int enable;
    private String title;
    private String text;
    private String a1;
    private String q1;
    private String a2;
    private String q2;
    private String a3;
    private String q3;
    private static int current;
    
    public Lecture() {}
    
    public Lecture(String title) {
        this.title = title;
    }
    
    public Lecture(String title, String text) {
        this.title = title;
        this.text = text;
    }
    
    public Lecture(String a1, String a2, String a3) {
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
    }
    
    public Lecture(String q1, String a1, String q2, String a2, String q3, String a3) {
        this.q1 = q1;
        this.a1 = a1;
        this.q2 = q2;
        this.a2 = a2;
        this.q3 = q3;
        this.a3 = a3;
    }
    
    public Lecture(int enable, String title, String text, String q1, String a1, String q2, String a2, String q3, String a3) {
        this.enable = enable;
        this.text = text;
        this.title = title;
        this.q1 = q1;
        this.a1 = a1;
        this.q2 = q2;
        this.a2 = a2;
        this.q3 = q3;
        this.a3 = a3;
    }
    
    public Lecture(int id, int enable, String title, String text, String q1, String a1, String q2, String a2, String q3, String a3) {
        this.id = id;
        this.title = title;
        this.enable = enable;
        this.text = text;
        this.q1 = q1;
        this.a1 = a1;
        this.q2 = q2;
        this.a2 = a2;
        this.q3 = q3;
        this.a3 = a3;
    }
    
    public int getID() {
        return id;
    }
    
    public void setID(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public int getEnable() {
        return enable;
    }
    
    public void setEnablle(int enable) {
        this.enable = enable;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getQ1() {
        return q1;
    }
    
    public void setQ1(String q1) {
        this.q1 = q1;
    }
    
    public String getA1() {
        return a1;
    }
    
    public void setA1(String a1) {
        this.a1 = a1;
    }
    
    public String getQ2() {
        return q2;
    }
    
    public void setQ2(String q2) {
        this.q2 = q2;
    }
    
    public String getA2() {
        return a2;
    }
    
    public void setA2(String a2) {
        this.a2 = a2;
    }
    
    public String getQ3() {
        return q3;
    }
    
    public void setQ3(String q3) {
        this.q3 = q3;
    }
    
    public String getA3() {
        return a3;
    }
    
    public void setA3(String a3) {
        this.a3 = a3;
    }
    
    public static int getCurrentLecture(){
        return current;
    }
    public static void setCurrentLecture(int currentUser){
        current = currentUser;
    }
}
