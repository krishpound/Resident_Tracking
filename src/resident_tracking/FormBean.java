package resident_tracking;

public class FormBean
{
    private String startDate, endDate, resident, filter, sortOrder, department, user_role, status,
    			   userID, lastName, firstName, degree, program, site, action, message1, message2, message3,
    			   applicationUser,title,sqlaction,reportstyle,chartid,charttype,chartylabel,
    			   chartproducer,charttitle,dictationID,accredited,academic_year;
    
    private int chartheight;
    
    private boolean sqlstatus,reported_duty;
    
    private int rows_updated=0;
    
    public String getStartDate()
    {
    	return (startDate);
    }
    
    public void setStartDate(String sd){
    	this.startDate=sd.trim();
    }
    
    public String getEndDate()
    {
        return this.endDate;
    }
    
    public void setEndDate(String ed)
    {
        this.endDate = ed.trim();
    }

    public boolean getSqlStatus()
    {
        return this.sqlstatus;
    }

    public void setSqlStatus(boolean ss)
    {
        this.sqlstatus = ss;
    }
    
    public boolean getReportedDuty()
    {
        return this.reported_duty;
    }

    public void setReportedDuty(boolean rd)
    {
        this.reported_duty = rd;
    }
    public String getResident()
    {
        return fixNull(this.resident);
    }
    
    public void setResident(String r)
    {
        this.resident = r.trim();
    }
      
    public String getApplicationUser()
    {
        return this.applicationUser;
    }

    public void setApplicationUser(String au)
    {
        this.applicationUser = au.trim();
    }
  
    public String getSqlAction()
    {
        return this.sqlaction;
    }

    public void setSqlAction(String sql)
    {
        this.sqlaction = sql;
    }
    
    public String getDepartment() 
    {
        return fixNull(this.department);
    }

    public void setDepartment(String d)
    {
        this.department = d.trim();
    }
    
    public String getTitle() 
    {
        return this.title;
    }

    public void setTitle(String t)
    {
        this.title = t;
    }
    
    public String getUserRole()
    {
        return fixNull(this.user_role);
    }

    public void setUserRole(String ur)
    {
        this.user_role = ur.trim();
    }
   
    public String getStatus()
    {
        return fixNull(this.status);
    }

    public void setStatus(String st)
    {
        this.status = st.trim();
    }
    
    public String getUserID()
    {
        return fixNull(this.userID);
    }

    public void setUserID(String id)
    {
        this.userID = id.trim();
    }
    
    public String getReportStyle()
    {
        return this.reportstyle;
    }

    public void setReportStyle(String rs)
    {
        this.reportstyle = rs;
    }
    
    public String getLastName()
    {
        return fixNull(this.lastName);
    }

    public void setLastName(String ln)
    {
        this.lastName = ln.trim();
    }
    
    public String getFirstName()
    {
        return fixNull(this.firstName);
    }

    public void setFirstName(String fn)
    {
        this.firstName = fn.trim();
    }
    
    public String getDegree()
    {
        return fixNull(this.degree);
    }

    public void setDegree(String dg)
    {
        this.degree = dg.trim();
    }
    
    public String getMessage1()
    {
        return this.message1;
    }

    public void setMessage1(String m1)
    {
        this.message1 = m1;
    }
    
    public String getMessage2()
    {
        return this.message2;
    }

    public void setMessage2(String m2)
    {
        this.message2 = m2;
    }
    
    public String getMessage3()
    {
        return this.message3;
    }

    public void setMessage3(String m3)
    {
        this.message3 = m3;
    }
    
    public String getProgram()
    {
        return fixNull(this.program);
    }

    public void setProgram(String pg)
    {
        this.program = pg;
    }
    
    public String getAccredited()
    {
        return fixNull(this.accredited);
    }
    public void setAccredited(String ac)
    {
        this.accredited = ac;
    }
    public String getSite()
    {
        return fixNull(this.site);
    }
    public void setSite(String st)
    {
        this.site = st.trim();
    }
    
    public String getAction()
    {
        return fixNull(this.action);
    }
    public void setAction(String a)
    {
        this.action = a.trim();
    }
    public String getFilter()
    {
        return fixNull(this.filter);
    }

    public void setFilter(String f)
    {
        this.filter = f.trim();
    }
    
    public String getSortOrder()
    {
        return fixNull(this.sortOrder);
    }

    public void setSortOrder(String s)
    {
        this.sortOrder = s.trim();
    }
    
    public void setDictationID(String d)
    {
    	this.dictationID = d.trim();
    }
    
    public String getDictationID()
    {
    	return fixNull(this.dictationID);
    }
    
    public void setAcademicYear(String ay)
    {
    	this.academic_year = ay.trim();
    }   
    public String getAcademicYear()
    {
    	return fixNull(this.academic_year);
    }
    public String getChartID()
    {
        return this.chartid;
    }

    public void setChartID(String ci)
    {
        this.chartid = ci;
    }
    
    public String getChartType()
    {
        return this.charttype;
    }

    public void setChartType(String ct)
    {
        this.charttype = ct;
    }
    
    public String getChartYLable()
    {
        return this.chartylabel;
    }

    public void setChartYLabel(String cy)
    {
        this.chartylabel = cy;
    }
    
    public String getChartProducer()
    {
        return this.chartproducer;
    }

    public void setChartProducer(String cp)
    {
        this.chartproducer = cp;
    }
  
    public String getChartTitle()
    {
        return this.charttitle;
    }

    public void setChartTitle(String ct)
    {
        this.charttitle = ct;
    }
    
    public int getChartHeight()
    {
        return this.chartheight;
    }

    public void setChartHeight(int ch)
    {
        this.chartheight = ch;
    }
    
    public int getRowsUpdated()
    {
        return this.rows_updated;
    }

    public void setRowsUpdated(int ru)
    {
        this.rows_updated = ru;
    }
    
    private String fixNull(String in)
    {
        return (in == null) ? "" : in;
    }
    
}
