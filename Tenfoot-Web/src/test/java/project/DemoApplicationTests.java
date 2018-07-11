package project;

import com.project.utils.DateTool;

import java.util.Date;

public class DemoApplicationTests {


	public static void main(String[] args) {
//		String a = getWeekDate();
//		System.out.println(a);
//
//		String str = "1,2,3,4,5,7";
//		System.out.println(str.contains(a));

		Double d = 0.01d;
		System.out.println(Math.round(d*100));
	}

	public static String getWeekDate(){
		try {
			return String.valueOf(DateTool.getWeekDayNum(DateTool.getNDayOfDate(new Date(),7)));
		}catch (Exception e){
			e.printStackTrace();
			return "";
		}
	}


}
