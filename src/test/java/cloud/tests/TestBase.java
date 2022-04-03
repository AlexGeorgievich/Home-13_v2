package cloud.tests;

import cloud.config.ProjectConfig;
import cloud.helps.Attach;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.sleep;
import static java.lang.String.format;


public class TestBase {

    @BeforeAll
    static void beforeAll() {
        ProjectConfig cfg = ConfigFactory.create(ProjectConfig.class,System.getProperties());
        String user = cfg.user(),
                password = cfg.password(),
                baseUrl = cfg.getUrl();



        Configuration.remote = "https://" + user + ":" + password + "@" + System.getProperty("remoteBrowser");


        Configuration.baseUrl = "https://www.t1-consulting.ru/";
        Configuration.browserSize = System.getProperty("size", "1920x1080");
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.pageLoadTimeout = 80000;
        Configuration.browserVersion = System.getProperty("version", "91");
//
//        //password and user for remote browser
//        String user = System.getProperty("user");
//        String password = System.getProperty("password");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Скриншот");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        sleep(5000);
        closeWebDriver();
    }
}