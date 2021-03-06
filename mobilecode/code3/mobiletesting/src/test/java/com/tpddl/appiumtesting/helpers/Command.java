package com.tpddl.appiumtesting.helpers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.tpddl.appiumtesting.utils.Constants;
import com.tpddl.appiumtesting.utils.PropertyReader;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;

public class Command implements Constants{
	static AndroidDriver<AndroidElement> driver = null;
	
	public static List getList(String type, String expression) {
		List list=driver.findElements(By.className(expression));
		return list;
	}
	
	public static void scroll(String value) {
		 driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+value+"\"));");
	}
	
	
	public static void XpathContentWise() {
		driver.findElementByXPath("//*[@content-desc='3']").click();
		driver.findElementByXPath("//*[@content-desc='45']").click();
	}
	
	public static void ratingBar(String expression) {
		//io.appium.android.apis:id/ratingbar2
		//AndroidElement ratingBar = driver.findElement(By.id("io.appium.android.apis:id/ratingbar2"));
	List<AndroidElement> list = 	driver.findElements(By.className("android.widget.RatingBar"));
	
	System.out.println("Rating Bar is "+list.size());
	AndroidElement fiveStarRatingbar = list.get(1);
		//Get start point of fiveStarRatingbar.
		  int startX = fiveStarRatingbar.getLocation().getX();
		  System.out.println(startX);
		  //Get end point of fiveStarRatingbar.
		  int endX = fiveStarRatingbar.getSize().getWidth();
		  System.out.println(endX);
		  //Get vertical location of fiveStarRatingbar.
		  int yAxis = fiveStarRatingbar.getLocation().getY();
		  
		  //Set fiveStarRatingbar tap position to set Rating = 4 star.
		  //You can use endX * 0.2 for 1 star, endX * 0.4 for 2 star, endX * 0.6 for 3 star, endX * 0.8 for 4 star, endX * 1 for 5 star.
		  int tapAt = (int) (endX * 0.9);  
		  //Set fiveStarRatingbar to Rating = 4.5 star using TouchAction class.
		  TouchAction act=new TouchAction(driver);  
		  
		  act.press(PointOption.point(tapAt, yAxis)).release().perform();
		  
	}
	
	public static AndroidDriver<AndroidElement> getDriver() throws MalformedURLException{
		String serverURL = PropertyReader.getValue(SERVER_URL_KEY);
		String apkLocation = PropertyReader.getValue(APK_PATH_KEY);
		//String serverURL = "http://127.0.0.1:4723/wd/hub";
		//String apkLocation = "/Users/amit/Documents/tpddl-appium/apk/calc.apk";
		// c:\\apk\\calc.apk
		DesiredCapabilities dc  = new DesiredCapabilities();
		dc.setCapability(MobileCapabilityType.DEVICE_NAME, ANDROID_TEST_FW);
		dc.setCapability(MobileCapabilityType.APP, apkLocation);
		 driver = new AndroidDriver<>(new URL(serverURL),dc);
		 int time = PropertyReader.getIntValue(IMPLICIT_WAIT_TIME);
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		return driver;
	}
	
	private static AndroidElement getElement(String type, String expression) {
		AndroidElement element =  null;
		if(type.equalsIgnoreCase(ID)) {
			element = driver.findElement(By.id(expression));
		}
		else
		if(type.equalsIgnoreCase(CLASS)) {
			element = driver.findElement(By.className(expression));
		}
		else
		if(type.equalsIgnoreCase(UI_AUTOMATOR)) {
			element = driver.findElementByAndroidUIAutomator(expression);
		}
		else
		if(type.equalsIgnoreCase(XPATH)) {
			element = driver.findElement(By.xpath(expression));
		}
		return element;
	}
	
	
	public static String getText(String type, String expression) {
		AndroidElement element= getElement(type, expression);
		return element.getText();
	}
	public static void typeValue(String type, String expression ,String value) {
		AndroidElement element = getElement(type, expression);
		element.sendKeys(value);
	}
	
	public static void click(String type, String expression) {
		AndroidElement element = getElement(type, expression);
		element.click();
	}
	
	public static void delay() {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void close() {
		if(driver!=null) {
		driver.quit();
		}
	}
	
}
