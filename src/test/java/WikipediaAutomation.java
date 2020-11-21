import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WikipediaAutomation {

    private WebDriver driver;

    @BeforeClass
    public static void setDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setUp() {
        System.out.println("[INFO] Setting up driver...");
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        System.out.println("[INFO] Quitting driver...");
        driver.quit();
    }

    @Test
    public void searchTest() {
        driver.get("https://www.wikipedia.org/");

        WebElement searchInput = driver.findElement(By.id("searchInput"));
        searchInput.sendKeys("Juraj Dobrila" + Keys.ENTER);

        Assert.assertEquals("Juraj Dobrila", driver.findElement(By.id("firstHeading")).getText());
    }

    @Test
    public void historySearchTest() {
        driver.get("https://hr.wikipedia.org/wiki/Juraj_Dobrila");

        driver.findElement(By.id("ca-history")).click();
        driver.findElement(By.id("mw-history-search")).click();
        driver.findElement(By.id("mw-input-date-range-to")).click();
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.cssSelector(".mw-widget-calendarWidget-labelButton a"))).click();
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='mw-widget-calendarWidget-item mw-widget-calendarWidget-month'][text()='srpanj']"))).click();
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='mw-widget-calendarWidget-item mw-widget-calendarWidget-day'][text()='1']"))).click();
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        int brojRezultata = driver.findElements(By.cssSelector("#pagehistory li")).size();
        Assert.assertTrue(brojRezultata>0);

        String timestamp = driver.findElements(By.cssSelector("#pagehistory li")).get(0).getText();
        Assert.assertTrue(timestamp.contains("21:33") && timestamp.contains("24. travnja 2020."));
    }
}
