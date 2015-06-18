package MyUtility;

import java.util.Comparator;


public class MyMatchAvgComparator implements Comparator<MyMatch> {

	public MyMatchAvgComparator() {
		// TODO Auto-generated constructor stub
	}
	public int compare(MyMatch lhs, MyMatch rhs) 
	{
			return Double.compare(Double.parseDouble(lhs.getMyMatch_AVG()),Double.parseDouble(rhs.getMyMatch_AVG()));
	}
	
}
