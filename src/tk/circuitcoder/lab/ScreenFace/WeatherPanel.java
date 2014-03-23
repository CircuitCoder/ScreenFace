package tk.circuitcoder.lab.ScreenFace;

public class WeatherPanel extends Panel {
	
	public static class Weather {
		public static enum Status {
			CLEAR,CLOUDY,OVERCAST,RAINY,SNOWY,SLEET,SMOG,HAZE
		}
		
		private Status status;
		private int level;
		
		private int AQI;
		private int tem;
		private int hum;	
		private int winspd;	//Wind speed
		private double windir;	//Wind direction
	}
	private Weather getWeather() {
		return null;
	}
}
