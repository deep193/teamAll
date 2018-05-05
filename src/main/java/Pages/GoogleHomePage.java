package Pages;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class GoogleHomePage {

	final WebDriver driver;

	public GoogleHomePage(WebDriver driver) {
		this.driver=driver;
	}

	@FindBy(xpath ="//*[@id='lst-ib']")
	public WebElement googleSearchField;

	@FindBy(xpath="//input[@value='Google Search']")
	public WebElement googleSearchButton;
 
	
	@FindBy(xpath="//*[@id='rso']//h3/a")
	public List<WebElement> searchResultLinks;



//Navigate to google search bar and type "branch"
	
	public void searchInGoogle(String searchText)
	{
		
		googleSearchField.sendKeys(searchText);
		googleSearchField.submit();
	}

	public BranchHomePage clickExpectedLink() {
		
        // To make sure page is loaded completely
		WebElement myDynamicElement = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));

		// this are all the links you like to visit
		for (WebElement webElement : searchResultLinks)
		{
			System.out.println(webElement.getAttribute("href"));
			String expectedUrl = "https://branch.io/";
			String currUrl = webElement.getAttribute("href");
			if (currUrl.equals(expectedUrl)) {
				webElement.click();
				return new BranchHomePage(driver);
			}
		}
		return new BranchHomePage(driver);
	}
}
