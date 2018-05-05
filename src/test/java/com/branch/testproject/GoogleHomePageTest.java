package com.branch.testproject;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.BranchFooters;
import Pages.BranchHomePage;
import Pages.GoogleHomePage;

public class GoogleHomePageTest {

	//import webdriver object	
	WebDriver driver;

	String googleUrl ="https://www.google.com/";
	GoogleHomePage GHomePage;
	
	@BeforeClass
	public void openBrowser() throws InterruptedException{
		System.setProperty("webdriver.gecko.driver", "/Users/nkj/Downloads/geckodriver");
		driver = new FirefoxDriver(); //dependency injection
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		GHomePage=PageFactory.initElements(driver,GoogleHomePage.class );
		
		driver.get(googleUrl);

		//Thread.sleep(2000);

	}


	@Test(priority=1)
	public void testGoogleSearchBar() throws InterruptedException{
		GoogleHomePage gh = new GoogleHomePage(driver);
		PageFactory.initElements(driver, gh);
		gh.searchInGoogle("Branch");
        gh.clickExpectedLink();
		//Thread.sleep(5000);
		WebElement myDynamicElement = (new WebDriverWait(driver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='https://www2.branch.io/request-a-demo/']")));
		System.out.println("********");
		System.out.println(driver.getTitle());

	}

	@Test(priority=2)
	public void navigateBranchTeam() throws InterruptedException{
		BranchHomePage bh= new BranchHomePage(driver);
		PageFactory.initElements(driver, bh);
		//		Assert.assertTrue(bh.checkPageTitle("Branch - A mobile linking platform powering deep links and mobile attribution."));
//		Thread.sleep(3000);
		bh.navigateToBranchTeamPage();
		Thread.sleep(3000);
	}

	@AfterClass
	public void closeBrowser(){
		if(driver !=null)
		{
			driver.close();
		}
	}



}
