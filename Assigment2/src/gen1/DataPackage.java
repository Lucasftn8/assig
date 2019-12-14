package gen1;

public class DataPackage {
	   //Same as Client DataPackage
	   private int t_temp;
		private String  t_time;// value of reference
		
		public DataPackage(String _data_str) {
			String[] str_stmp=_data_str.split("|");
			this.t_temp=Integer.valueOf(str_stmp[0])/100;//to get in degres instead of milidegres
			this.t_time=str_stmp[1];
			//receive here the thread server value?
		}
		//GETTER
		public int get_temp() {return t_temp;}
		public String get_time() {return t_time;}
}
