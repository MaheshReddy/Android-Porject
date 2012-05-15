package com.es.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.util.ServiceException;

public class GoogleCal  {

	private final static String url = "https://www.google.com/calendar/feeds/default/private/full";
	private final static String usrName = "travelwatchmaster@gmail.com";
	private final static String passwd = "interOP@123";

	public ArrayList<String> onCreate() {
		// TODO Auto-generated method stub
		URL feedUrl;
		ArrayList<String> rtr_str = null;
		try {
			feedUrl = new URL(url);

			CalendarQuery myQuery = new CalendarQuery(feedUrl);
			// myQuery.setMinimumStartTime(DateTime.now());
			CalendarService myService = new CalendarService(
					"exampleCo-exampleApp-1");
			myService.setUserCredentials(usrName, passwd);

			// Send the request and receive the response:
			CalendarEventFeed resultFeed = myService.query(myQuery,
					CalendarEventFeed.class);
			List<CalendarEventEntry> ent = resultFeed.getEntries();
			Iterator<CalendarEventEntry> itr = ent.iterator();
			rtr_str = new ArrayList<String>();
			while (itr.hasNext()) {
				CalendarEventEntry entry = itr.next();
				System.out.println(entry.getTitle().getPlainText());
				rtr_str.add(entry.getTitle().getPlainText());
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
		return  rtr_str;
	}
}
