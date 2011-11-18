package us.la.lft.traffic;

public class CameraValueObject {
	protected int latitude;
	protected int longitude;
	protected String description;
	protected String image;
	
	public CameraValueObject(int latitude, int longitude, String description, String image) {
		setLatitude(latitude);
		setLongitude(longitude);
		setDescription(description);
		setImage(image);
	}
	
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String toString() {
		return description;
	}
}
