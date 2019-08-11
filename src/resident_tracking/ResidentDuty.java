package resident_tracking;

public class ResidentDuty {

	private String 	resident,program;
	private int		ttl_tds_ex,ttl_tds_days,ttl_or_ex,ttl_or_days,TTL_ex,TTL_ex_days;
	private boolean	reported_time;
	
	public ResidentDuty(){
		
		this.resident=null;
		this.program=null;
		this.ttl_tds_ex=0;
		this.ttl_tds_days=0;
		this.ttl_or_ex=0;
		this.ttl_or_days=0;
		this.TTL_ex=0;
		this.ttl_or_days=0;
		this.reported_time=false;
		
	}
	
	public String getResident()
    {
    	return (this.resident);
    }
    
    public void setResident(String r){
    	this.resident=r.trim();
    }
    
    public String getProgram()
    {
    	return (this.program);
    }
    
    public void setProgram(String r){
    	this.program=r.trim();
    }
   
    public int getTtlTdsEx()
    {
        return this.ttl_tds_ex;
    }
	
    public void setTtlTdsEx(int tdsex)
    {
        this.ttl_tds_ex=tdsex;
    }
	
    public int getTtlTdsDays()
    {
        return this.ttl_tds_days;
    }
	
    public void setTtlTdsDays(int tdsdy)
    {
        this.ttl_tds_days=tdsdy;
    }
    
    public int getTtlOrEx()
    {
        return this.ttl_or_ex;
    }
	
    public void setTtlOrEx(int orex)
    {
        this.ttl_or_ex=orex;
    }
	
    public int getTtlOrDays()
    {
        return this.ttl_or_days;
    }
	
    public void setTtlOrDays(int ordy)
    {
        this.ttl_or_days=ordy;
    }
    
    public int getTtlEx()
    {
        return this.TTL_ex;
    }
	
    public void setTtlEx(int ttlex)
    {
        this.TTL_ex=ttlex;
    }
   
    public int getTtlExDays()
    {
        return this.TTL_ex_days;
    }
	
    public void setTtlExDays(int ttldy)
    {
        this.TTL_ex_days=ttldy;
    }
       
    public boolean getReportedTime()
    {
        return this.reported_time;
    }
	
    public void setReportedTime(boolean rt)
    {
        this.reported_time=rt;
    }
    
    public String getTDSDisplay(){
    	
    	String display = String.valueOf(this.ttl_tds_ex)+" / "+String.valueOf(this.ttl_tds_days);
    	return display;
    	
    }
    
    public String getORDisplay(){
    	
    	String display = String.valueOf(this.ttl_or_ex)+" / "+String.valueOf(this.ttl_or_days);
    	return display;
    	
    }

    public String getTOTALDisplay(){
    	 	
    	String display = String.valueOf(this.TTL_ex)+" / "+String.valueOf(this.TTL_ex_days);
    	return display;
    	
    }
    
 //   public int compareTo (ResidentDuty rd){
 //   	int i = 1;
 //   	return i;
 //   }
    
    
    
    
    
    
}
