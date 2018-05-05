package Pages;






import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class BranchHomePage {
//$x("//div[@style='display: inline-block;']").length
	WebDriver driver;

	@FindBy(how = How.XPATH, using="//a[@href='https://branch.io/team/#all']")
	public WebElement teamLnk;
	
	public BranchHomePage(WebDriver driver) {
		this.driver=driver;
	}

	public boolean checkPageTitle(String title) {
		System.out.println(driver.getTitle());
		return driver.getTitle().equalsIgnoreCase(title);
	}
	
	public void navigateToBranchTeamPage () {
//		driver = new FirefoxDriver();
//		((JavascriptExecutor) driver).executeScript(document.querySelector('a').href='https://branch.io/team/#all').scrollIntoView(true);");
		 ((JavascriptExecutor) driver)
         .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		 
		

		teamLnk.click();
	}
	
}
