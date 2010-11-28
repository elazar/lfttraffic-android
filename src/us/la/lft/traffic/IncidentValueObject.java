package us.la.lft.traffic;

public class IncidentValueObject {
	protected int latitude;
	protected int longitude;
	protected String address;
	protected String description;
	protected String reportTime;
	
	public IncidentValueObject(int latitude, int longitude, String address, String description, String reportTime) {
		setLatitude(latitude);
		setLongitude(longitude);
		setAddress(address);
		setDescription(description);
		setReportTime(reportTime);
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReportTime() {
		return reportTime;
	}
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	public String toString() {
		return address + "\n" + description + " - " + reportTime;
	}
}
