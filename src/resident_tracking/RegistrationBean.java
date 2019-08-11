package resident_tracking;

public class RegistrationBean {


    private String userid, lastname, firstname, lifenumber, securityquestion,securityanswer, password1, password2,
    				message1,message2,message3;
    
   private boolean sqlstatus;
    
    private int rows_updated=0;
    
    public String getUserID()
    {
    	return (userid);
    }
    
    public void setUserID(String ui){
    	this.userid=ui;
    }
   
    public String getLastName()
    {
    	return (lastname);
    }
    
    public void setLastName(String ln){
    	this.lastname=ln;
    }
    
    public String getFirstName()
    {
    	return (firstname);
    }
    
    public void setFirstName(String fn){
    	this.firstname=fn;
    }
    
    public String getLifeNumber()
    {
    	return (lifenumber);
    }
    
    public void setLifeNumber(String li){
    	this.lifenumber=li;
    }
    
    public String getSecurityQuestion()
    {
    	return (securityquestion);
    }
    
    public void setSecurityQuestion(String sq){
    	this.securityquestion=sq;
    }
    
    public String getSecurityAnswer()
    {
    	return (securityanswer);
    }
    
    public void setSecurityAnswer(String sa){
    	this.securityanswer=sa;
    }
    
    public String getPassword1()
    {
    	return (password1);
    }
    
    public void setPassword1(String p1){
    	this.password1=p1;
    }
    
    public String getPassword2()
    {
    	return (password2);
    }
    
    public void setPassword2(String p2){
    	this.password2=p2;
    }
    
    public String getMessage1()
    {
    	return (message1);
    }
    
    public void setMessage1(String m1){
    	this.message1=m1;
    }
    
    public String getMessage2()
    {
    	return (message2);
    }
    
    public void setMessage2(String m2){
    	this.message2=m2;
    }
    
    public String getMessage3()
    {
    	return (message3);
    }
    
    public void setMessage3(String m3){
    	this.message3=m3;
    }
    
    public boolean getSqlStatus()
    {
    	return (sqlstatus);
    }
    
    public void setSqlStatus(boolean ss){
    	this.sqlstatus=ss;
    }
}