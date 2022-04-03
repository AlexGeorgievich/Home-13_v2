package cloud.tests;

import cloud.config.ProjectConfig;
import cloud.helps.Attach;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.sleep;
import static java.lang.String.format;


public class TestBase {

    @BeforeAll
    static void beforeAll() {
        ProjectConfig cfg = ConfigFactory.create(ProjectConfig.class,System.getProperties());
        String user = cfg.user(),
                password = cfg.password(),
                baseUrl = cfg.getUrl(),
                browserVersion = cfg.browserVersion(),
                remoteUrl = cfg.remoteUrl();
//                remote = "https://" + user + ":" + password + "@" + System.getProperty("remoteBrowser");

        browser = cfg.browser();
        Configuration.browserVersion = browserVersion;
        Configuration.browserSize = cfg.size();
        Configuration.baseUrl = baseUrl;
        Configuration.remote = "https://" + user + ":" + password + "@" + System.getProperty("remoteBrowser");
//        Configuration.baseUrl = "https://www.t1-consulting.ru/";
//        Configuration.browserSize = System.getProperty("size", "1920x1080");
//        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.pageLoadTimeout = 80000;
        Configuration.browserVersion = System.getProperty("version", "91");

        System.out.println("user - " + user);
        System.out.println("password - " + password);
        System.out.println("baseUrl - " +  baseUrl);
        System.out.println("browser - " + browser);
        System.out.println("remote - " + remote);


//        //password and user for remote browser
//        String user = System.getProperty("user");
//        String password = System.getProperty("password");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
        SelenideLogger.addListener("allure", new AllureSelenide());

        Attach.attachAsText("Browser: ", browser);
        Attach.attachAsText("Version: ", browserVersion);
        Attach.attachAsText("Remote Url: ", remoteUrl);
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