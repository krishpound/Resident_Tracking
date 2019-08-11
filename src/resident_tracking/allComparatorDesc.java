package resident_tracking;

import java.util.*;

public class allComparatorDesc implements Comparator{

	public int compare(Object one, Object two){
		
		int exceptions_1 = ((ResidentDuty)one).getTtlEx();
		int exceptions_2 = ((ResidentDuty)two).getTtlEx();
		
		if (exceptions_1 > exceptions_2)
			return -1;
		
		else if (exceptions_2 > exceptions_1)
			return 1;
		
		else
			return 0;
		
		
	}
		
}
