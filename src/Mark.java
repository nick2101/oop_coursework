public class Mark {
    private int id;
    private String fn;
    private String ln;
    private String test;
    private int mark;
    private static String currentTest;
    
    Mark() {}
    
    Mark(String fn, String ln, String test) {
        this.fn = fn;
        this.ln = ln;
        this.test = test;
    }
    
    Mark(String fn, String ln, String test, int mark) {
        this.fn = fn;
        this.ln = ln;
        this.test = test;
        this.mark = mark;
    }
    
    Mark(int id, String fn, String ln, String test, int mark) {
        this.id = id;
        this.fn = fn;
        this.ln = ln;
        this.test = test;
        this.mark = mark;
    }
    
    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }
    public String getFN() {
        return fn;
    }
    public String getLN() {
        return ln;
    }
    public String getTest() {
        return test;
    }
    public int getMark() {
        return mark;
    }
    public void setMark(int mark) {
        this.mark = mark;
    }
    public static String getCurrentTest() {
        return currentTest;
    }
    public static void setCurrentTest(String current) {
        currentTest = current;
    }
}
