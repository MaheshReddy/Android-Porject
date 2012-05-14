package com.es.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.es.basic.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class AmazonWSApp1Activity {
	private static String TAG = "AmazonWSApp1Activity";

	/*
	 * Your AWS Access Key ID, as taken from the AWS Your Account page.
	 */
	private static final String AWS_ACCESS_KEY_ID = "AKIAIOGRC46O43OMUO6Q";

	/*
	 * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
	 * Your Account page.
	 */
	private static final String AWS_SECRET_KEY = "qm1Ahom7mv1LSRVsH4XhT9klirOMWTu/ImWKqSQT";

	private static final String AWS_ASSOC_TAG = "3085-7248-9938";

	/*
	 * Use one of the following end-points, according to the region you are
	 * interested in:
	 * 
	 * US: ecs.amazonaws.com CA: ecs.amazonaws.ca UK: ecs.amazonaws.co.uk DE:
	 * ecs.amazonaws.de FR: ecs.amazonaws.fr JP: ecs.amazonaws.jp
	 */
	private static final String ENDPOINT = "ecs.amazonaws.com";

	/*
	 * The Item ID to lookup. The value below was selected for the US locale.
	 * You can choose a different value if this value does not work in the
	 * locale of your choice.
	 */
	// private static final String ITEM_ID = "0545162076";

    /** Called when the activity is first created. */
    public ArrayList<String> onCreate() {

		RequestHelper helper = null;
		try {
			helper = RequestHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID,
					AWS_SECRET_KEY);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		ArrayList<String> rtr=new ArrayList<String>();
		
		String str = "HarryPotter";
		Log.d(TAG, "Fetching for " + str);
		Map<String, String> params = new HashMap<String, String>();
		params.put("Service", "AWSECommerceService");
		params.put("Version", "2011-08-31");
		params.put("Operation", "ItemSearch");
		// params.put("ItemId", ITEM_ID);
		params.put("SearchIndex", "All");

		// params.put("ResponseGroup", "Small");
		params.put("AssociateTag", AWS_ASSOC_TAG);
		params.put("Keywords", str);
		String requestUrl = helper.sign(params);
		Log.d(TAG, "Signed Request is \"" + requestUrl + "\"");

		// fetchItemId(requestUrl);
		Document doc = fetchResponse(requestUrl);
		List<String> prodIds = fetchItemId(doc);
		List<String> prodTitles = fetchTitle(doc);
		if (prodIds.size() != prodTitles.size()) {
			Log.e(TAG, "#Items = " + prodIds.size() + ", #Titles = "
					+ prodTitles.size());
		} else {
			for (int i = 0; i < 4; ++i){
				Log.d(TAG, "Item Id: " + prodIds.get(i) + ", Title: "
						+ prodTitles.get(i));
				rtr.add("Item Id: " + prodIds.get(i) + ", Title: "
						+ prodTitles.get(i));
			}
		}
		return rtr;

    }

	private static Document fetchResponse(String requestUrl) {
		Document doc = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(requestUrl);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return doc;
	}

	/*
	 * Utility function to fetch the response from the service and extract the
	 * title from the XML.
	 */
	private static List<String> fetchTitle(Document doc) {
		String title = null;
		List<String> prodTitles = new ArrayList<String>();
		try {
			// DocumentBuilderFactory dbf =
			// DocumentBuilderFactory.newInstance();
			// DocumentBuilder db = dbf.newDocumentBuilder();
			// Document doc = db.parse(requestUrl);
			// Node titleNode = doc.getElementsByTagName("Title").item(0);
			// title = titleNode.getTextContent();
			NodeList titleNodeList = doc.getElementsByTagName("Title");
			for (int i = 0; i < titleNodeList.getLength(); i++) {
				title = titleNodeList.item(i).getTextContent();
				prodTitles.add(title);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return prodTitles;
	}

	private static List<String> fetchItemId(Document doc) {
		String title = null;
		List<String> prodIds = new ArrayList<String>();
		try {
			// DocumentBuilderFactory dbf =
			// DocumentBuilderFactory.newInstance();
			// DocumentBuilder db = dbf.newDocumentBuilder();
			// Document doc = db.parse(requestUrl);
			NodeList titleNodeList = doc.getElementsByTagName("ASIN");
			for (int i = 0; i < titleNodeList.getLength(); i++) {
				title = titleNodeList.item(i).getTextContent();
				prodIds.add(title);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return prodIds;
	}
}