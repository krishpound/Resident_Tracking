package resident_tracking;

public class loginBean
{
    private String v_uid;
    private String v_pw;
    private String v_userEmail;
    
    public String getuserEmail()
    {
    	return fixNull(this.v_userEmail);
    }
    
    public void setuserEmail(String ue){
    	this.v_userEmail=ue;
    }
    
    public String getUID()
    {
        return fixNull(this.v_uid);
    }

    public void setUID(String userid)
    {
        this.v_uid = userid;
    }

    public String getPW()
    {
        return fixNull(this.v_pw);
    }

    public void setPW(String password)
    {
        this.v_pw = password;
    }
          
    private String fixNull(String in)
    {
        return (in == null) ? "" : in;
    }

    public String getMessage()
    {
        return "\nWelcome to Resident Tracking, " + getUID() + ".\n" + "password given was: "+getPW()+".";
              
    }
}
