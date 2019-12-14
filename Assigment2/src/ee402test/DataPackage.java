package ee402test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataPackage {
	   //Different from Client DataPackage
	   private int t_temp;
		private String  t_time;// value of reference
		
		public DataPackage() {}
		public DataPackage getDataPackage() {
			//ouvrir le fichier et donner le t_temp à l'adresse /sys/class/thermal/thermal_zone0/temp
			try {
				File file = new File("/sys/class/thermal/thermal_zone0/temp");
				Scanner scan = new Scanner(file);
				String data= scan.nextLine();
				System.out.println(data);
				scan.close();
				t_temp=Integer.valueOf(data);
			}
			catch (FileNotFoundException e){
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
			DateTimeService datatime=new DateTimeService();
			t_time= datatime.getDateAndTime();
			return this;
		}
		//GETTER
		public int get_temp() {return t_temp;}
		public String get_time() {return t_time;}
}
