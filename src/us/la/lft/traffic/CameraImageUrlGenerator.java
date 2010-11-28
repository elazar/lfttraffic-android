package us.la.lft.traffic;

public class CameraImageUrlGenerator {
	public static String getUrl(String image) {
		return "http://gis.lafayettela.gov/VServerImages/" + image + ".jpg";
	}
}
