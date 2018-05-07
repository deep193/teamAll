package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.JavascriptExecutor;

public class BranchHomePage {
	WebDriver driver;

	@FindBy(how = How.XPATH, using="//a[@href='https://branch.io/team/#all']")
	public WebElement teamLnk;

	public BranchHomePage(WebDriver driver) {
		this.driver=driver;
	}

	public boolean checkPageTitle(String title) {
//		System.out.println(driver.getTitle());
		return driver.getTitle().equalsIgnoreCase(title);
	}

	public void navigateToBranchTeamPage () {
		((JavascriptExecutor) driver)
		.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		teamLnk.click();
	}

}
