package com.branch.testproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import junit.framework.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Pages.BranchHomePage;
import Pages.BranchTeamPage;
import Pages.GoogleHomePage;

public class BranchEmployeeInfoTests {

	WebDriver driver;

	@BeforeClass
	public void openBrowser() throws InterruptedException{
		System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}


	@Test(priority=1)
	public void testGoogleSearchAndNavigateToBranch() throws InterruptedException{
		String googleUrl ="https://www.google.com/";
		String branchPageTitle = "Branch - A mobile linking platform powering deep links and mobile attribution.";
		driver.get(googleUrl);
		GoogleHomePage gh = new GoogleHomePage(driver);
		PageFactory.initElements(driver, gh);
		gh.searchInGoogle("Branch");
		gh.clickBranchLink();
		Assert.assertTrue("FAIL: Page title for Branch doesnt match", driver.getTitle().equals(branchPageTitle));
	}

	@Test(priority=2)
	public void testNavigateBranchTeam() throws InterruptedException{
		String branchTeamPageTitle = "Branch Metrics - The Team";
		BranchHomePage bh= new BranchHomePage(driver);
		PageFactory.initElements(driver, bh);
		bh.navigateToBranchTeamPage();
		Assert.assertTrue("FAIL: page title of Branch Team page doesn't match", driver.getTitle().equals(branchTeamPageTitle));
	}

	@Test(priority=3)
	public void testEmployeesCountAllandOtherDepartments() throws InterruptedException{
		BranchTeamPage bth = new BranchTeamPage(driver);
		PageFactory.initElements(driver, bth);
		int allEmployeesStrength;
		int sumOfAllTeamsStrength = 0;
		List<Integer> teamGroupSizes = new ArrayList<Integer>();
		String[] teamGroups = {"data", "engineering", "marketing", "operations", "partnerGrowth", "product", "recruiting"}; 
		allEmployeesStrength = bth.getEmployeeInfo("all", "nameAndDept").size();

		for (int i=0; i<teamGroups.length; i++) {
			teamGroupSizes.add(bth.getEmployeeInfo(teamGroups[i], "nameAndDept").size());
		}

		for (int j=0; j<teamGroupSizes.size(); j++) {
			sumOfAllTeamsStrength += teamGroupSizes.get(j);
		}

		Assert.assertEquals("FAIL: All employees and sum of employees of all teams doesn't match", allEmployeesStrength, sumOfAllTeamsStrength);
	}

	@Test(priority=4)
	public void testEmployeeNamesAllandOtherDepartments() throws InterruptedException{
		BranchTeamPage bth = new BranchTeamPage(driver);
		PageFactory.initElements(driver, bth);
		String[] teamGroups = {"data", "engineering", "marketing", "operations", "partnerGrowth", "product", "recruiting"}; 

		HashMap<String, String> allEmployeesInfo = new HashMap<String, String>();
		List<String> empNamesDoesntMatch = new ArrayList<String>();

		allEmployeesInfo = bth.getEmployeeInfo("all", "nameAndDept");

		HashMap<String, String> employeeInfoFromOtherTabs = new HashMap<String, String>();

		for(String groupName : teamGroups){
			employeeInfoFromOtherTabs.putAll(bth.getEmployeeInfo(groupName, "nameAndDept"));
		}

		if (!allEmployeesInfo.equals(employeeInfoFromOtherTabs)) {
			for (String empName: allEmployeesInfo.keySet()) {
				if (employeeInfoFromOtherTabs.keySet().contains(empName)) {
				} else {
					empNamesDoesntMatch.add(empName);
				}
			}
		} 

		Assert.assertTrue("FAIL: employee names doesn't match in All and other departments. Details: "+empNamesDoesntMatch.toString(), empNamesDoesntMatch.size() == 0);
	}

	@Test(priority=5)
	public void testEmployeesDepartmentsAllGroups() throws InterruptedException{
		BranchTeamPage bth = new BranchTeamPage(driver);
		PageFactory.initElements(driver, bth);
		String[] teamGroups = {"data", "engineering", "marketing", "operations", "partnerGrowth", "product", "recruiting"}; 

		HashMap<String, String> allEmployeesInfo = new HashMap<String, String>();
		List<String> empDeptsDoesntMatch = new ArrayList<String>();

		allEmployeesInfo = bth.getEmployeeInfo("all", "nameAndDept");

		System.out.println(allEmployeesInfo.keySet().size());

		HashMap<String, String> employeeInfoFromOtherTabs = new HashMap<String, String>();

		for(String groupName : teamGroups){
			employeeInfoFromOtherTabs.putAll(bth.getEmployeeInfo(groupName, "nameAndDept"));
		}

		if (!allEmployeesInfo.equals(employeeInfoFromOtherTabs)) {
			for (String empName: allEmployeesInfo.keySet()) {
				if (employeeInfoFromOtherTabs.keySet().contains(empName)) {
					String empDeptInAll = allEmployeesInfo.get(empName);
					String empDeptInOther = employeeInfoFromOtherTabs.get(empName);
					if (!empDeptInAll.equals(empDeptInOther)) {
						empDeptsDoesntMatch.add(empName+"DeptAll_"+empDeptInAll+"DeptOther_"+empDeptInOther);
					} 
				}
			}
		}

		Assert.assertTrue("FAIL: employee Departments doesn't match in All and other. Details: "+empDeptsDoesntMatch.toString(), empDeptsDoesntMatch.size() == 0);

	}


	@Test(priority=6)
	public void testEmployeesAboutInTeamAll() throws InterruptedException{
		BranchTeamPage bth = new BranchTeamPage(driver);
		PageFactory.initElements(driver, bth);

		HashMap<String, String> allEmployeesInfo = new HashMap<String, String>();
		allEmployeesInfo = bth.getEmployeeInfo("all", "nameAndAbout");

		List<String> empWithNoAbout = new ArrayList<String>();
		int count = 0;
		for (String empName: allEmployeesInfo.keySet()) {
			String empAboutInfo = allEmployeesInfo.get(empName);
			if (empAboutInfo.trim().length() == 0) {
				empWithNoAbout.add(empName);
				count++;
			}
		}

		Assert.assertEquals("FAIL: Employees doesn't have About text:"+empWithNoAbout.toString(), 0, count);
	}

	@Test(priority=7)
	public void testImageExistsForAllEmployees() throws InterruptedException{
		BranchTeamPage bth = new BranchTeamPage(driver);
		PageFactory.initElements(driver, bth);

		HashMap<String, String> allEmployeesInfo = new HashMap<String, String>();
		allEmployeesInfo = bth.getEmployeeInfo("all", "nameAndImgUrl");

		List<String> empWithNoAbout = new ArrayList<String>();
		int count = 0;
		for (String empName: allEmployeesInfo.keySet()) {
			String empAboutInfo = allEmployeesInfo.get(empName);
			if (empAboutInfo.trim().length() < 10) {
				empWithNoAbout.add(empName);
				count++;
			}
		}

		Assert.assertEquals("FAIL: Employees doesn't have Images:"+empWithNoAbout.toString(), 0, count);
	}

	@AfterClass
	public void closeBrowser(){
		if(driver !=null)
		{
			driver.close();
		}
	}

}
