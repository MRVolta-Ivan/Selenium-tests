package com.autorepairportal.autorepairportal;

import com.autorepairportal.autorepairportal.models.days;
import com.autorepairportal.autorepairportal.models.providedservices;
import com.autorepairportal.autorepairportal.models.repairshop;
import com.autorepairportal.autorepairportal.models.workdays;
import com.autorepairportal.autorepairportal.repository.daysRepository;
import com.autorepairportal.autorepairportal.repository.providedservicesRepository;
import com.autorepairportal.autorepairportal.repository.repairshopRepository;
import com.autorepairportal.autorepairportal.repository.workdaysRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class SeleniumTests {
    private String driverName = "webdriver.chrome.driver";
    private String pathToDriver = "D:\\Деятельность\\Новый семестр 2\\ВТ ПО\\chromedriver_win32\\chromedriver.exe";
    private ChromeDriver currentDriver() {
        return new ChromeDriver();
    }

    @Autowired
    private providedservicesRepository servicesRepos;

    @Autowired
    private repairshopRepository repairshopRepos;

    @Autowired
    private workdaysRepository workdaysRepos;

    @Autowired
    private daysRepository daysRepos;

    @Test
    public void checkCountGoodsOnMain() {
        System.setProperty(driverName, pathToDriver);

        ChromeDriver driver = currentDriver();
        driver.get("http://localhost:8080/");

        List<WebElement> elements = driver.findElements(By.className("card"));

        List<providedservices> serv = servicesRepos.findAll();

        Assertions.assertEquals(elements.size(), serv.size());

        driver.close();
    }

    @Test
    public void checkItem() {
        System.setProperty(driverName, pathToDriver);

        providedservices serv = servicesRepos.findAll().get(0);

        long id = serv.getID();

        ChromeDriver driver = currentDriver();
        driver.get("http://localhost:8080/service/" + id);

        repairshop shop = repairshopRepos.findById(serv.getID_RepairShop()).get();
        List<workdays> work = workdaysRepos.findByIDRepairShop(serv.getID_RepairShop());

        Assertions.assertEquals(
                driver.findElements(By.tagName("h1")).get(0).getText(),
                serv.getName()
        );

        Assertions.assertEquals(
                driver.findElements(By.tagName("p")).get(0).getText(),
                "Описание: " + serv.getDescription()
        );

        Assertions.assertEquals(
                driver.findElements(By.tagName("p")).get(1).getText(),
                "Цена: " + serv.getCost() + " руб."
        );

        Assertions.assertEquals(
                driver.findElements(By.tagName("li")).get(7).getText(),
                "Название: " + shop.getName()
        );

        Assertions.assertEquals(
                driver.findElements(By.tagName("li")).get(8).getText(),
                "Адрес: " + shop.getAddress()
        );

        Assertions.assertEquals(
                driver.findElements(By.tagName("li")).get(9).getText(),
                "Рабочие дни: " + work.stream()
                        .map(workdays -> daysRepos.findById(workdays.getID_Days()))
                        .map(op -> op.get().getName())
                        .collect(Collectors.joining(", ", "", ""))
        );

        Assertions.assertEquals(
                driver.findElements(By.tagName("li")).get(10).getText(),
                "Телефон: " + shop.getPhoneNumber()
                );

        driver.close();
    }

    @Test
    public void checkAdd() {
        System.setProperty(driverName, pathToDriver);

        ChromeDriver driver = currentDriver();
        driver.get("http://localhost:8080/");

        List<WebElement> elem = driver.findElements(By.className("card"));

        driver.get("http://localhost:8080/reg");

        driver.findElement(By.name("Name")).sendKeys("Тестовая автомастерская");
        driver.findElement(By.name("Address")).sendKeys("Тестовый адрес");
        driver.findElement(By.name("PhoneNumber")).sendKeys("00000000000");

        driver.findElement(By.name("Shino")).click();
        driver.findElement(By.name("CostShino")).sendKeys("100");
        driver.findElement(By.name("NameShino")).sendKeys("Тестовый шиномонтаж");
        driver.findElement(By.name("DescriptionShino")).sendKeys("Тестовое описание");

        driver.findElement(By.name("Zamena")).click();
        driver.findElement(By.name("CostZamena")).sendKeys("100");
        driver.findElement(By.name("NameZamena")).sendKeys("Тестовая замена масла");
        driver.findElement(By.name("DescriptionZamena")).sendKeys("Тестовое описание");

        driver.findElement(By.name("Remont")).click();
        driver.findElement(By.name("CostRemont")).sendKeys("100");
        driver.findElement(By.name("NameRemont")).sendKeys("Тестовый ремонт двигателя");
        driver.findElement(By.name("DescriptionRemont")).sendKeys("Тестовое описание");

        driver.findElement(By.xpath("//*[contains(text(), 'Зарегистрировать')]")).submit();

        Assertions.assertEquals(
                elem.size() + 3,
                driver.findElements(By.className("card")).size()
        );

        driver.close();
    }

    @Test
    public void checkDelete() {
        System.setProperty(driverName, pathToDriver);

        ChromeDriver driver = currentDriver();
        driver.get("http://localhost:8080/admin");

        List<WebElement> elem = driver.findElements(By.className("card"));

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement elemdelete =  driver.findElement(By.xpath("//*[contains(text(), 'Удалить')]"));

        elemdelete.click();

        Assertions.assertEquals(
                elem.size() - 1,
                driver.findElements(By.className("card")).size()
        );

        driver.close();
    }

    @Test
    public void checkEdit(){
        System.setProperty(driverName, pathToDriver);

        ChromeDriver driver = currentDriver();
        driver.get("http://localhost:8080/admin");

        driver.findElement(By.xpath("//*[contains(text(), 'Изменить')]")).click();

        driver.findElement(By.name("Name")).clear();
        driver.findElement(By.name("Address")).clear();
        driver.findElement(By.name("PhoneNumber")).clear();

        driver.findElement(By.name("Name")).sendKeys("Изменение названия");
        driver.findElement(By.name("Address")).sendKeys("Изменение адреса");
        driver.findElement(By.name("PhoneNumber")).sendKeys("11111111111");

        driver.findElement(By.xpath("//*[contains(text(), 'Принять изменения')]")).submit();

        driver.findElement(By.xpath("//*[contains(text(), 'Ссылка')]")).click();

        Assertions.assertEquals(
                driver.findElements(By.tagName("li")).get(7).getText(),
                "Название: Изменение названия"
        );

        Assertions.assertEquals(
                driver.findElements(By.tagName("li")).get(8).getText(),
                "Адрес: Изменение адреса"
        );

        Assertions.assertEquals(
                driver.findElements(By.tagName("li")).get(10).getText(),
                "Телефон: 11111111111"
        );

        driver.close();
    }
}
