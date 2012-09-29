package us.la.lft.traffic.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Scanner;

import android.util.Log;

public class CachedHttpGetRequest {
	private static String TAG = "CachedHttpGetRequest";

	public String get(String url, String cacheFile, long timeToLive) {
		File file = new File(cacheFile);
		InputStream inputStream;
		boolean write = false;

		// If the cache file has expired, delete it
		long now = new Date().getTime();
		long lastModified = file.lastModified();
		if (now - lastModified >= timeToLive) {
			Log.d(TAG, "TTL of cache file '" + cacheFile + "' for URL '" + url + "' expired: " + String.valueOf(now) + " - " + String.valueOf(lastModified) + " = " + String.valueOf(now - lastModified) + " >= " + String.valueOf(timeToLive));
			file.delete();
		}

		// If the cache file exists, use it as the data source
		try {
			inputStream = (InputStream) new FileInputStream(file);
			Log.d(TAG, "Found unexpired cache file '" + cacheFile + "'");

		// If the cache file doesn't exist, use the URL as the data source
		} catch (FileNotFoundException e) {
			Log.d(TAG, "Could not find cache file '" + file + "', fetching URL '" + url + "'");
			inputStream = HttpGetRequestFactory.getHttpGetRequest().get(url);
			write = true;
		}

		// Fetch the data from the source
		String content = new Scanner(inputStream).useDelimiter("\\A").next();

		// If the data needs to be cached, write it to the cache file
		if (write) {
			this.set(cacheFile, content);
		}

		return content;
	}

	public void set(String cacheFile, String content) {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(cacheFile);
			fileWriter.write(content, 0, content.length());
			fileWriter.close();
		} catch (IOException e) {
			Log.d(TAG, "Can't write to cache file '" + cacheFile + "': " + e.getMessage());
		}
	}
}
