package us.la.lft.traffic;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class IncidentFetcher {
	protected DefaultHttpClient httpClient;
	protected HttpGet httpRequest;
	protected IncidentList incidentList = null;
	protected long lastFetched = 0;
	protected static IncidentFetcher incidentFetcher;

	protected IncidentFetcher() {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
		HttpConnectionParams.setSoTimeout(httpParams, 3000);
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(httpParams, schemeRegistry);
		this.httpClient = new DefaultHttpClient(connectionManager, httpParams);
		this.httpRequest = new HttpGet("http://laftrafficscraper.heroku.com");
	}

	public static IncidentFetcher getInstance() {
		if (incidentFetcher == null) {
			incidentFetcher = new IncidentFetcher();
		}
		return incidentFetcher;
	}

	public IncidentList getIncidentList() {
		if (new Date().getTime() - this.lastFetched < 45) {
			return this.incidentList;
		}

		this.incidentList = new IncidentList();

		HttpResponse httpResponse;
		try {
			httpResponse = this.httpClient.execute(this.httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				InputStream inputStream = httpResponse.getEntity().getContent();
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				Document document = documentBuilder.parse(inputStream);
				NodeList accidents = document.getElementsByTagName("accident");
				int accidentCount = accidents.getLength();
				Node accident;
				int accidentIndex;
				String address, description, reportTime;
				Date reportDate;
				int reportTimePlusSignIndex;
				int latitude, longitude;

				NodeList childNodes;
				int childNodeIndex, childNodeCount;
				Node childNode;
				String childNodeName, childNodeValue;
				DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				DateFormat dateFormatter = new SimpleDateFormat("h:mm a");
				for (accidentIndex = 0; accidentIndex < accidentCount; accidentIndex++) {
					accident = accidents.item(accidentIndex);
					childNodes = accident.getChildNodes();
					childNodeCount = childNodes.getLength();
					address = description = reportTime = "";
					latitude = longitude = 0;
					for (childNodeIndex = 0; childNodeIndex < childNodeCount; childNodeIndex++) {
						childNode = childNodes.item(childNodeIndex);
						if (childNode.getNodeType() != Node.ELEMENT_NODE) {
							continue;
						}
						childNodeName = childNode.getNodeName();
						childNodeValue = childNode.getChildNodes().item(0).getNodeValue();
						if (childNodeName.equals("location")) {
							address = childNodeValue + address;
						} else if (childNodeName.equals("dueto")) {
							description = childNodeValue;
						} else if (childNodeName.equals("time")) {
							reportTimePlusSignIndex = childNodeValue.indexOf("+");
							if (reportTimePlusSignIndex != -1) {
								reportTime = childNodeValue.substring(0, reportTimePlusSignIndex);
							} else {
								reportTime = childNodeValue;
							}
							try {
								reportDate = dateParser.parse(reportTime);
								reportTime = dateFormatter.format(reportDate);
							} catch (ParseException e) { }
						} else if (childNodeName.equals("city")) {
							address += ", " + childNodeValue;
						} else if (childNodeName.equals("latitude")) {
							latitude = (int) (Float.parseFloat(childNodeValue) * 1000000);
						} else if (childNodeName.equals("longitude")) {
							longitude = (int) (Float.parseFloat(childNodeValue) * 1000000);
						}
					}
					this.incidentList.add(latitude, longitude, address, description, reportTime);
				}

				this.lastFetched = new Date().getTime();
			}
		} catch (IOException e) {
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		}
		return this.incidentList;
	}
}
