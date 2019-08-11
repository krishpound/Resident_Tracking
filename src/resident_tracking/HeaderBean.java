package resident_tracking;

import java.util.ArrayList;

public class HeaderBean
{
    private String welcome_name,username,title,login_time,startdate,enddate,resident,filter,browser,
    			   programlist,residentlist,academicyear;
    
    private int    academic_year_key;
    
    private ArrayList<String> summaryFilterList = new ArrayList<String>();
    private ArrayList<String> summarySortList = new ArrayList<String>();
    private ArrayList<String> detailFilterList = new ArrayList<String>();
    private ArrayList<String> detailSortList = new ArrayList<String>();
    private ArrayList<String> menuStyleList = new ArrayList<String>();
    private boolean authorized, administrator;

    public HeaderBean(){
    	
    	summaryFilterList.add("Residents with Reported Time");
    	summaryFilterList.add("All Residents");
    	summaryFilterList.add("Residents with Exceptions");
    	summaryFilterList.add("Residents with TDS exceptions");
    	summaryFilterList.add("Residents with OR exceptions");
    	summarySortList.add("Total Exceptions Desc");
    	summarySortList.add("Total Exceptions Asc");
    	summarySortList.add("Total Exception Days Asc");
    	summarySortList.add("Total Exception Days Desc");
    	summarySortList.add("TDS Exceptions Desc");
		summarySortList.add("TDS Exceptions Asc");
		summarySortList.add("TDS Exception Days Asc");
		summarySortList.add("TDS Exception Days Desc");
		summarySortList.add("OR Exceptions Desc");
		summarySortList.add("OR Exceptions Asc");
		summarySortList.add("OR Exception Days Asc");
		summarySortList.add("OR Exception Days Desc");
		summarySortList.add("Resident Name");
		detailFilterList.add("Approved Duty Hours and Exceptions");
		detailFilterList.add("Approved Duty Hours only");
		detailFilterList.add("Approved Duty Hours and all OR and TDS events");
		detailFilterList.add("TDS - all events");
		detailFilterList.add("TDS - exceptions");
		detailFilterList.add("OR - all events");
		detailFilterList.add("OR - all exceptions");
		detailFilterList.add("Exceptions only");
		detailSortList.add("Date Ascending");
		detailSortList.add("Date Descending");
		menuStyleList.add("Tabular");
		menuStyleList.add("Graphical");
		
    }
    
    public String getWelcomeName()
    {
    	return fixNull(welcome_name);
    }
    
    public void setWelcomeName(String wn){
    	this.welcome_name=wn;
    }
    
    public ArrayList<String> getSummaryFilterList(){
    	return this.summaryFilterList;
    }
    public ArrayList<String> getSummarySortList(){
    	return this.summarySortList;
    }
    public ArrayList<String> getDetailFilterList(){
    	return this.detailFilterList;
    }
    public ArrayList<String> getDetailSortList(){
    	return this.detailSortList;
    }
    public ArrayList<String> getMenuStyleList(){
    	return this.menuStyleList;
    }
    public boolean getAuthorized()
    {
    	return (authorized);
    }
    
    public void setAuthorized(boolean au){
    	this.authorized=au;
    }
    
    public String getResidentList()
    {
    	return (residentlist);
    }
    
    public void setResidentList(String rl){
    	this.residentlist=rl;
    }
    
    public String getProgramList()
    {
    	return (programlist);
    }
    
    public void setProgramList(String pl){
    	this.programlist=pl;
    }
    
    public String getBrowser()
    {
    	return (browser);
    }
    
    public void setBrowser(String br)
    {
    	this.browser=br;
    }
 
    public boolean getAdministrator()
    {
    	return (administrator);
    }
    
    public void setAdministrator(boolean ad){
    	
    	this.administrator=ad;
    }
    
    public String getStartDate()
    {
    	return (startdate);
    }
    
    public void setStartDate(String sd){
    	this.startdate=sd;
    }
    
    public String getEndDate()
    {
    	return (enddate);
    }
    
    public void setEndDate(String ed){
    	this.enddate=ed;
    }
    
    public String getResident()
    {
    	return (resident);
    }
    
    public void setResident(String re){
    	this.resident=re;
    }
    
    public String getUserName()
    {
    	return fixNull(username);
    }
    
    public void setUserName(String un){
    	this.username=un;
    }
    public String getTitle()
    {
    	return fixNull(title);
    }
    
    public void setTitle(String t){
    	this.title = t;
    }
    
    public String getAcademicYear()
    {
    	return fixNull(academicyear);
    }
    
    public void setAcademicYear(String ay){
    	this.academicyear = ay;
    }
    public int getAcademicYearKey()
    {
    	return (academic_year_key);
    }
    public String getAcademicYearKeyString(){
    	
    	return (Integer.toString(academic_year_key));
    	
    }
    
    public void setAcademicYearKey(int ayk){
    	this.academic_year_key = ayk;
    }
    public String getLoginTime()
    {
    	return fixNull(login_time);
    }
    
    public void setLoginTime(String lt){
    	this.login_time=lt;
    }
    
    public String getFilter()
    {
    	return fixNull(filter);
    }
    
    public void setFilter(String f){
    	this.filter=f;
    }
    private String fixNull(String in)
    {
        return (in == null) ? "" : in;
    }
    
}

