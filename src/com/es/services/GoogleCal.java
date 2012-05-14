package com.es.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.Feed;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.util.ServiceException;

import android.app.Activity;
import android.os.Bundle;

public class GoogleCal extends Activity {

	private final static String url = "https://www.google.com/calendar/feeds/default/private/full";
	private final static String usrName = "travelwatchmaster@gmail.com";
	private final static String passwd = "interOP@123";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		URL feedUrl;
		try {
			feedUrl = new URL(url);
		

		CalendarQuery myQuery = new CalendarQuery(feedUrl);
		//myQuery.setMinimumStartTime(DateTime.now());
		CalendarService myService = new CalendarService("exampleCo-exampleApp-1");
		myService.setUserCredentials(usrName, passwd);

		// Send the request and receive the response:
		CalendarEventFeed resultFeed = myService.query(myQuery, CalendarEventFeed.class);
		List<CalendarEventEntry> ent = resultFeed.getEntries();
		Iterator<CalendarEventEntry> itr = ent.iterator();
		while(itr.hasNext()){
			CalendarEventEntry entry = (CalendarEventEntry)itr.next();
			System.out.println(entry.getTitle().getPlainText());
		}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}
