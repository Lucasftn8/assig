package ee402test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataPackage {
	   //Same as Client DataPackage
	   private float t_temp;
		private String  t_time;// value of reference
		
		public DataPackage(float _temp,String _time) {
			this.t_temp=_temp;
			this.t_time=_time;
			//receive here the thread server value?
		}
		public DataPackage getDataPackage() {
			//ouvrir le fichier et donner le t_temp à l'adresse /sys/class/thermal/thermal_zone0/temp
			try {
				File file = new File("/sys/class/thermal/thermal_zone0/temp");
				Scanner scan = new Scanner(file);
				float data = scan.nextFloat();
				System.out.println(data);
				scan.close();
				t_temp=data/100;
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
		public float get_temp() {return t_temp;}
		public String get_time() {return t_time;}
}
