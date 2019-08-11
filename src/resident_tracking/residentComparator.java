package resident_tracking;

import java.util.*;

public class residentComparator implements Comparator<ResidentDuty>{

	public int compare(ResidentDuty one, ResidentDuty two){
		
		return one.getResident().compareTo(two.getResident());
			
	}
		
}
