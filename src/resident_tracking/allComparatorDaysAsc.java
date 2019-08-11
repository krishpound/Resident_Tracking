package resident_tracking;

import java.util.*;

public class allComparatorDaysAsc implements Comparator<ResidentDuty>{

	public int compare(ResidentDuty one, ResidentDuty two){
		
		int days_1 = one.getTtlExDays();
		int days_2 = two.getTtlExDays();
		
		if (days_1 > days_2)
			return 1;
		
		else if (days_2 > days_1)
			return -1;
		
		else
			return 0;
		
	}
}
