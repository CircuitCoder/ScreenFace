package tk.circuitcoder.lab.ScreenFace;

import java.io.IOException;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
import net.sf.json.util.JSONBuilder;
import net.sf.json.util.JSONStringer;
import net.sf.json.util.JSONUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("unused")
public class WeatherPanel extends Panel {
	private static final long serialVersionUID = 1L;
	
	private HttpClient hc;
	private HttpGet aqiReq;
	
	private AQI caqi;
	private Weather cw;
	
	private Thread rt;
	
	public static class Weather {
		public static enum Condition {
			CLEAR,CLOUDY,OVERCAST,RAINY,SNOWY,SLEET,SMOG,HAZE
		}
		
		private Condition cond;
		private int level;
		
		private int tem;
		private int hum;	
		private int winspd;	//Wind speed
		private double windir;	//Wind direction
	}
	
	public static class AQI {
		private int AQI;
		private String pp;
		private String q;
		private String timepoint;
		
		@Override
		public String toString() {
			return "AQI: "+String.valueOf(AQI)
					+"\nPrimary Pollutant: "+pp
					+"\nQuality: "+q
					+"\nTime Point: "+timepoint;
		}
	}
	
	public WeatherPanel(String token,String sc) {
		if(token==null) throw new IllegalArgumentException();
		if(sc==null) sc="1007A";
		
		hc=HttpClients.createDefault();
		aqiReq=new HttpGet("http://www.pm25.in/api/querys/aqis_by_station.json?token="+token+"&station_code="+sc);
		
		rt=new Thread(new Runnable() {
			@Override
			public void run() {
				if(cw==null) cw=getWeather();
				else synchronized (cw) {
					cw=getWeather();
				}
				if(caqi==null) caqi=getAQI();
				else synchronized (caqi) {
					caqi=getAQI();
				}
			}
		});
		refreash();
	}
	
	public void refreash() {
		if(rt.isAlive()) return;
		rt.start();
	}
	
	private Weather getWeather() {
		return null;
	}
	
	private AQI getAQI() {
		AQI result=new AQI();
		try {
			HttpResponse res=hc.execute(aqiReq);
			String resStr=EntityUtils.toString(res.getEntity());
			System.out.println(resStr);
			JSONArray tmpArr=JSONArray.fromObject(resStr);
			JSONObject resObj=tmpArr.getJSONObject(0);
			
			result.AQI=resObj.getInt("aqi");
			result.pp=resObj.getString("primary_pollutant");
			result.q=resObj.getString("quality");
			result.timepoint=resObj.getString("time_point");
			
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
}
