package model;
public class Person {
    private final String FIRST;
    private final String LAST;
    private String username;

    /*
     * @pre first != null && last != null;
     */
    public Person(String first, String last){
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
    /*
     * @pre username != null
     */
    public void setUser(String username){
        this.username = username;
    }

    public String getUserName(){
        return this.username;
    }
    
    @Override
    public String toString() {
    	return this.FIRST + " " + this.LAST;
    }
}