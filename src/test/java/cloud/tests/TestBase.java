package cloud.tests;

import cloud.config.AuthConfig;
import cloud.helps.Attach;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.sleep;
import static java.lang.String.format;

public class TestBase {
    @Owner("Курс - QA.GURU")
    @Link(value = "Тестирование сайта", url = "https://www.t1-consulting.ru/")
    @Feature("Задачи в репозитории")
    @BeforeAll
    static void setUp() {
        AuthConfig cfg = ConfigFactory.create(AuthConfig.class,System.getProperties());
        String user = cfg.username();
        String password = cfg.password();

        String browser = System.getProperty("browser", "chrome");
        String version = System.getProperty("version", "91");
        String size = System.getProperty("size", "1920x1080");
        String remoteUrl = System.getProperty("remoteUrl", "selenoid.autotests.cloud/wd/hub");
        String url = "https://" + user + ":" + password + "@" + remoteUrl;

        Configuration.baseUrl = "https://www.t1-consulting.ru/";
        Configuration.remote = url;
        Configuration.browser = browser;
        Configuration.browserVersion = version;
        Configuration.browserSize = size;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

        SelenideLogger.addListener("allure", new AllureSelenide());

        Attach.attachAsText("Browser: ", browser);
        Attach.attachAsText("Version: ", version);
        Attach.attachAsText("Remote Url: ", remoteUrl);
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        sleep(5000);
        closeWebDriver();
    }
}


