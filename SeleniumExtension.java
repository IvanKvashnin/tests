package ru.sber.gsz.autotests;

import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.yaml.snakeyaml.Yaml;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static java.lang.System.getProperty;
import static java.lang.System.setProperty;
import static java.util.concurrent.TimeUnit.SECONDS;

public class SeleniumExtension implements ParameterResolver, AfterAllCallback, BeforeEachCallback {
    private final WebDriver driver;

    public SeleniumExtension() {
        driver = new ChromeDriver(new ChromeOptions().addArguments(List.of()));
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return WebDriver.class.isAssignableFrom(parameter.getType());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        addListener("allure", new AllureSelenide());
        setProperty("webdriver.chrome.driver", getProperty("user.dir") + "/src/main/resources/chromedriver");
        driver.manage().window().setSize(new Dimension(1600, 1250));
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        return driver;
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        driver.manage().deleteAllCookies();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        driver.close();
    }

    private List<String> getArguments() {
        Map<String, Object> data = new Yaml().load(this.getClass().getClassLoader().getResourceAsStream("application.yaml"));
        Map<String, Object> driverMap = (Map<String, Object>) data.get("driver");
        return (List<String>) driverMap.get("options");
    }
}
