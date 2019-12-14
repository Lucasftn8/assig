package gen1;

public class DataPackage {
	   //Same as Client DataPackage
	   private int t_temp;
		private String  t_time;// value of reference
		
		public DataPackage(String _temp,String _time) {
			this.t_temp=Integer.valueOf(_temp)/100;//to get in degres instead of milidegres
			this.t_time=_time;
			//receive here the thread server value?
		}
		//GETTER
		public int get_temp() {return t_temp;}
		public String get_time() {return t_time;}
}
