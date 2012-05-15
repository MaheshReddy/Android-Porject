 
package com.es.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Example application that uses OAuth method to acquire access to your account.<br>
 * This application illustrates how to use OAuth method with Twitter4J.<br>
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public  class Twitterservicetest {
    /**
     * Usage: java twitter4j.examples.tweets.UpdateStatus [text]
     *
     * @param args message
     */

	@SuppressWarnings("unused")
	public static void main(String a[]) {
		 try {
	            Twitter twitter = new TwitterFactory().getInstance();
	            twitter.setOAuthConsumer("eTUZPafnoroI7xRVNXBVbw",
        		"WeB9n3qcBc1KRq5RJ5KpM3CO1zFGEyWQBJ1tBVbkE");
	            //AccessToken accToken = new AccessToken("498332544-IHHvbZPHsYjrTcMI30R9XLsXEhWHYfjDVfzEZLsmek", 
	            	//								"Ezd5nzMcYFrPVgS05fqQNG2WNO10DWSH5GO5nVUGI");
	            //twitter.setOAuthAccessToken(accToken);
	            
	            try {
	                // get request token.
	                // this will throw IllegalStateException if access token is already available
	                RequestToken requestToken = twitter.getOAuthRequestToken();
	                //RequestToken requestToken = new RequestToken("IHHvbZPHsYjrTcMI30R9XLsXEhWHYfjDVfzEZLsmek", "Ezd5nzMcYFrPVgS05fqQNG2WNO10DWSH5GO5nVUGI");
	                System.out.println("Got request token.");
	                System.out.println("Request token: " + requestToken.getToken());
	                System.out.println("Request token secret: " + requestToken.getTokenSecret());
	                AccessToken accessToken = null;

	                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	                while (null == accessToken) {
	                    System.out.println("Open the following URL and grant access to your account:");
	                    System.out.println(requestToken.getAuthorizationURL());
	                    System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
	                    String pin = new String("6883870");//= br.readLine();
	                    try {
	                        if (pin.length() > 0) {
	                            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
	                        } else {
	                            accessToken = twitter.getOAuthAccessToken(requestToken);
	                        }
	                    } catch (TwitterException te) {
	                        if (401 == te.getStatusCode()) {
	                            System.out.println("Unable to get the access token.");
	                        } else {
	                            te.printStackTrace();
	                        }
	                    }
	                }
	                System.out.println("Got access token.");
	                System.out.println("Access token: " + accessToken.getToken());
	                System.out.println("Access token secret: " + accessToken.getTokenSecret());
	            } catch (IllegalStateException ie) {
	                // access token is already available, or consumer key/secret is not set.
	                if (!twitter.getAuthorization().isEnabled()) {
	                    System.out.println("OAuth consumer key/secret is not set.");
	                    System.exit(-1);
	                }
	            }
	            Status status = twitter.updateStatus("Test from Activiti 5.9 failed. Retrying in  "+ new Date().getSeconds() + " seconds");
	            System.out.println("Successfully updated the status to [" + status.getText() + "].");
	            System.exit(0);
	        } catch (TwitterException te) {
	            te.printStackTrace();
	            System.out.println("Failed to get timeline: " + te.getMessage());
	            System.exit(-1);
	        }

	}
}
