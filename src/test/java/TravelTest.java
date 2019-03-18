import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Ivan Yushin
 * @see #fillForm(By, String)
 * @see #findElementAndClick(String)
 * @see #scrollPage(String)
 */
public class TravelTest extends BaseTest {

    @Test
    public void travel(){
        findElementAndClick("//*/ol/li/a[contains(text(), 'Страхование')]");
        findElementAndClick("//*[contains(text(), 'Выезжающим за рубеж')]");

        scrollPage("//*[contains(@class, 'thumbnail-footer')]/a[contains(text(),'Рассчитать')]"); /* Скроллер */
        findElementAndClick("//*[text()=' ок ']");
        findElementAndClick("//*[contains(@class, 'thumbnail-footer')]/a[contains(text(),'Рассчитать')]");

        scrollPage("//div[@class='page-header']/span [@class ='h1']");

        /* Поиск и сравнение с требуемым текстом */
        compareText(
                "Страхование выезжающих за рубеж",
                drv.findElement(By.xpath("//div[@class='page-header']/span [@class ='h1']")).getText().trim()
        );
        findElementAndClick("//*[contains(@class, 'btn-content-subtitle small')]");

        scrollPage("//*[contains(@class, 'btn-content-subtitle small')]");
        fillForm(By.xpath("//*[contains(@class, 'actual-input')]"), "шенген");  // вводим страну
        findElementAndClick("//*[contains(@class, 'tt-menu tt')]");
        findElementAndClick("//*[contains(@ id, 'ArrivalCountryList')]");
        new Select(drv.findElement(By.name("ArrivalCountryList"))).selectByVisibleText("Испания");
        findElementAndClick("//*[contains(@data-bind, 'value: FirstDepartureDate.')]");

        Itravel dateOfJourney = () -> {
            LocalDate today = LocalDate.now();
            LocalDate date = today.plus(14, ChronoUnit.DAYS);
            String[] split = date.toString().split("-");
            StringBuilder builder = new StringBuilder();
            for (int i = split.length - 1; i >= 0; i--) {
                builder.append(split[i]);
            }
            return builder.toString();
        };

        fillForm(By.xpath("//*[contains(@data-bind, 'value: FirstDepartureDate.')]"), dateOfJourney.function()); // ДАТА ПОЕЗДКИ
        findElementAndClick("//*[contains(@data-bind, 'btnRadioGroupValue: 90')]");

        scrollPage("//*[contains(@class, 'form-control')][contains(@data-bind, 'attr: _params.fullName.attr,')]");
        String temp = "//*[contains(@class, 'form-control')][contains(@data-bind, 'attr: _params.fullName.attr,')]";
        findElementAndClick(temp);
        fillForm(By.xpath(temp), "Yushin Ivan"); // ФАМИЛИЯ ИМЯ
        temp = "//*[contains(@data-bind, 'value: BirthDay.computedView')]";
        findElementAndClick(temp);
        String birthDate = "01111990";
        fillForm(By.xpath(temp), birthDate); // ДАТА РОЖДЕНИЯ

        findElementAndClick("//*[contains(text(), 'активный отдых или спорт')]/ancestor::div[@class=\"calc-vzr-toggle-risk-group\"]//div[@class=\"toggle off toggle-rgs\"]");
        scrollPage("//*[contains(@data-test-name, 'IsProcessingPersonalDataToCalculate')]");
        findElementAndClick("//*[contains(@data-test-name, 'IsProcessingPersonalDataToCalculate')]");
        findElementAndClick("//*[contains(@data-bind, 'disable: Misc.NextButtonDisabled')]");
    }

    /**
     *
     * Скроллер страницы
     */
    public void scrollPage(String xPath){
        ((JavascriptExecutor)drv).executeScript("arguments[0].scrollIntoView();", drv.findElement(By.xpath(xPath)));
    }

    /**
     *
     * @param locator локатор элемента
     * @param fill текст который заполняем в форму
     */
    public void fillForm(By locator, String fill){
        drv.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        drv.findElement(locator).click();
        drv.findElement(locator).clear();
        drv.findElement(locator).sendKeys(fill);
    }

    /**
     *
     * @param xPath параметр принимает xpath
     */
    private void findElementAndClick(String xPath){
        drv.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        drv.findElement(By.xpath(xPath)).click();
    }

    /**
     *
     * @param text первый параметр текст который хотим найти
     * @param line второй который извлечён из страницы drv.findElement
     */
    private void compareText(String text, String line) {
        Assert.assertEquals("Текст не совпадает" ,text, line);
    }
}