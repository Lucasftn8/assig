package ee402test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataPackage {
	   // Client DataPackage
        private String t_temp,t_time;// value of reference
		
		public DataPackage() {}
		public DataPackage getDataPackage() {
			//ouvrir le fichier et donner le t_temp à l'adresse /sys/class/thermal/thermal_zone0/temp
			try {
				File file = new File("/sys/class/thermal/thermal_zone0/temp");
				Scanner scan = new Scanner(file);
				t_temp= scan.nextLine();
				System.out.println(t_temp);
				scan.close();
			}
			catch (FileNotFoundException e){
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
			DateTimeService datatime=new DateTimeService();
			t_time= datatime.getDateAndTime();
			return this;
		}
		public String get_data_str() {return (t_temp+"|"+t_time);}
}
