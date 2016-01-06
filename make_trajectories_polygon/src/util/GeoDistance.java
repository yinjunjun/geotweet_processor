package util;

public class GeoDistance{

  public static double Distance(double lat1, double lon1, double lat2, double lon2)
  {
	double r = 6371.0; // approx. radius of earth in km
	double lat1Radians = (lat1 * Math.PI) / 180.0;
	double lon1Radians = (lon1 * Math.PI) / 180.0;
	double lat2Radians = (lat2 * Math.PI) / 180.0;
	double lon2Radians = (lon2 * Math.PI) / 180.0;

	double deltaLat = lat2Radians - lat1Radians;
	double deltaLng = lon2Radians - lon1Radians;

	double a = Math.sin(deltaLat/2.0) * Math.sin(deltaLat/2.0) + Math.cos(lat1Radians) * Math.cos(lat2Radians) * Math.sin(deltaLng/2.0) * Math.sin(deltaLng/2.0);
	
	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt( 1 - a));
	
	double d = r * c;	

	
	//double d = r * Math.acos((Math.cos(lat1Radians) *
	//		Math.cos(lat2Radians) *
	//		Math.cos(lon2Radians - lon1Radians) +
	//		(Math.sin(lat1Radians) * Math.sin(lat2Radians))));
	return d;
   }
}
