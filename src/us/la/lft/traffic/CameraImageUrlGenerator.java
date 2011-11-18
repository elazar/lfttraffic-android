package us.la.lft.traffic;

public class CameraImageUrlGenerator {
	// Original source for image values: http://www.lafayettela.gov/trafficcameras/traffic_cameras.aspx
	public static String getUrl(String image) {
		return "http://www.lafayettela.gov/tcams/getTCamera.aspx?ptzid=" + image;
	}
}
