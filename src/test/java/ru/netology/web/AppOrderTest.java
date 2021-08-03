package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppOrderTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpBrowserDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUpDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldFillInTheGaps() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Филимонов Илья");
        elements.get(1).sendKeys("+79163273699");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void incorrectUserName() {
        List<WebElement> fields = driver.findElements(By.className("input__control"));
        fields.get(0).sendKeys("Filimonov Ilya");
        fields.get(1).sendKeys("89163273699");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actual = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.trim());
    }

    @Test
    void incorrectTelephoneNumber() {
        List<WebElement> fields = driver.findElements(By.className("input__control"));
        fields.get(0).sendKeys("Филимонов Илья");
        fields.get(1).sendKeys("89163273699");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.trim());
    }

    @Test
    void emptyFieldsValue() {
        List<WebElement> fields = driver.findElements(By.className("input__control"));
        fields.get(0).sendKeys("");
        fields.get(1).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actual.trim());
    }

    @Test
    void insertSymbolInNameField() {
        List<WebElement> fields = driver.findElements(By.className("input__control"));
        fields.get(0).sendKeys("@!#$ Илья");
        fields.get(1).sendKeys("+79163273696");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.trim());
    }

    @Test
    void insertSymbolInPhoneField() {
        List<WebElement> fields = driver.findElements(By.className("input__control"));
        fields.get(0).sendKeys("Филимонов Илья");
        fields.get(1).sendKeys("+№№3273699");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.trim());
    }

    @Test
    void phoneNumberOverMaximum() {
        List<WebElement> fields = driver.findElements(By.className("input__control"));
        fields.get(0).sendKeys("Филимонов Илья");
        fields.get(1).sendKeys("+7916327369999");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.trim());
    }

    @Test
    void phoneNumberUnderMaximum() {
        List<WebElement> fields = driver.findElements(By.className("input__control"));
        fields.get(0).sendKeys("Филимонов Илья");
        fields.get(1).sendKeys("+7916327369999");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.trim());
    }

    @Test
    void insertDoubleSecondName() {
        List<WebElement> fields = driver.findElements(By.className("input__control"));
        fields.get(0).sendKeys("Филимонов-Щедрин Илья");
        fields.get(1).sendKeys("+79163273699");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actual = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual.trim());

    }

    @Test
    void shouldSayNeedPutMark() {
        List<WebElement> fields = driver.findElements(By.className("input__control"));
        fields.get(0).sendKeys("Филимонов Илья");
        fields.get(1).sendKeys("+79163273699");
//        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=\"agreement\"].input_invalid .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", actual.trim());
    }
}