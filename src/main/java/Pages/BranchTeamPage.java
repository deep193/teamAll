package Pages;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BranchTeamPage {

	WebDriver driver;

	@FindBy(how = How.XPATH, using="//a[@href='https://branch.io/team/#all']")
	public WebElement teamLnk;

	@FindBy(how = How.XPATH, using="//a[@href='#all']")
	public WebElement teamAll;

	@FindBy(how = How.XPATH, using="//a[@href='#data']")
	public WebElement teamData;

	@FindBy(how = How.XPATH, using="//a[@href='#engineering']")
	public WebElement teamEngineering;

	@FindBy(how = How.XPATH, using="//a[@href='#marketing']")
	public WebElement teamMarketing;

	@FindBy(how = How.XPATH, using="//a[@href='#operations']")
	public WebElement teamOperations;

	@FindBy(how = How.XPATH, using="//a[@href='#partner-growth']")
	public WebElement teamPartnerGrowth;

	@FindBy(how = How.XPATH, using="//a[@href='#product']")
	public WebElement teamProduct;

	@FindBy(how = How.XPATH, using="//a[@href='#recruiting']")
	public WebElement teamRecruiting;

	@FindBy(xpath="//div[@style='display: inline-block;']")
	public List<WebElement> employeesObj;

	@FindBy(xpath="//div[@class='overlay']/p")
	public List<WebElement> employeeAboutInfo;

	public BranchTeamPage(WebDriver driver) {
		this.driver=driver;
	}

	public boolean checkPageTitle(String title) {
		System.out.println(driver.getTitle());
		return driver.getTitle().equalsIgnoreCase(title);
	}

	public HashMap<String,String> getEmployeeInfo(String teamName, String infoType ) {
		HashMap<String,String> employeeInfo=new HashMap<String,String>();  

		switch (teamName) {
		case "all": 
			teamAll.click();
			break;
		case "data": 
			teamData.click();
			break;
		case "engineering": 
			teamEngineering.click();
			break;
		case "marketing": 
			teamMarketing.click();
			break;
		case "operations": 
			teamOperations.click();
			break;
		case "partnerGrowth": 
			teamPartnerGrowth.click();
			break;		
		case "product": 
			teamProduct.click();
			break;
		case "recruiting": 
			teamRecruiting.click();
			break;
		case "default" :
			System.out.println("Error: Invalid option, selecting All Employees.");
			teamAll.click();
			break;
		}

		if (infoType.equals("nameAndDept")) {
			for (WebElement emp : employeesObj)
			{
//				System.out.println(emp.findElement(By.tagName("h2")).getText()+" : "+ emp.findElement(By.tagName("h4")).getText());
				employeeInfo.put(emp.findElement(By.tagName("h2")).getText(), emp.findElement(By.tagName("h4")).getText());
			}
		} else if (infoType.equals("nameAndAbout")) {
			int i = 0;
			for (WebElement emp : employeesObj)
			{
//				System.out.println(emp.findElement(By.tagName("h2")).getText()+" : "+ driver.findElements(By.cssSelector(".hovereffect .overlay p")).get(i).getAttribute("innerText"));
				employeeInfo.put(emp.findElement(By.tagName("h2")).getText(), driver.findElements(By.cssSelector(".hovereffect .overlay p")).get(i).getAttribute("innerText"));
				i++;
			}
		} else if (infoType.equals("nameAndImgUrl")) {
			int i = 0;
			for (WebElement emp : employeesObj)
			{
//				System.out.println(emp.findElement(By.tagName("h2")).getText()+" : "+ driver.findElements(By.cssSelector(".image-block")).get(i).getCssValue("background-image"));
				employeeInfo.put(emp.findElement(By.tagName("h2")).getText(), driver.findElements(By.cssSelector(".image-block")).get(i).getCssValue("background-image"));
				i++;
			}
		}
		else {
			System.out.println("Error: infoType - nameAndDept or nameAndAbout or nameAndImgUrl");
		}

		return employeeInfo;
	}

}
