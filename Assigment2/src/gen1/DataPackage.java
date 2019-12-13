package gen1;

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
			
			return this;
		}
		//GETTER
		public float get_temp() {return t_temp;}
		public String get_time() {return t_time;}
}
