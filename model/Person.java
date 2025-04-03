package model;
public class Person {
    private final String FIRST;
    private final String LAST;
    private String username;

    public Person(String first, String last){
        assert first != null && last != null;
        this.FIRST = first;
        this.LAST = last;
        this.username = "";
    }

    public String getFirstName(){
        return FIRST;
    }
    public String getLastName(){
        return this.LAST;
    }

    public void setUser(String username){
        assert username != null;
        this.username = username;
    }

    public String getUserName(){
        return this.username;
    }
}
