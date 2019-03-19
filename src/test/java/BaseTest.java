import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class BaseTest {
    public WebDriver drv;

    @Before
    public void initTest(){
        System.out.println("Travel");
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        drv = new ChromeDriver();
        drv.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        drv.manage().window().maximize();
        drv.get("https://www.rgs.ru/");
    }

    @After
    public void close(){
        drv.quit();
    }
}