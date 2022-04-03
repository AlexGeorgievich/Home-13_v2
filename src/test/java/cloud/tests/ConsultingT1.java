package cloud.tests;

import cloud.helps.Attach;
import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class ConsultingT1 extends TestBase {
    @Owner("Курс  QA.GURU")
    @Link(value = "Тестирование UI сайта", url = "https://www.t1-consulting.ru/")
    @Feature("Задачи в репозитории")
    @Tag("smoke")
    @Test
    @DisplayName("Проверка наличия заголовка на главной странице сайта ")
    void controlTitleTest() {
        step("Открытие сайта по адресу - " + baseUrl, () -> {
            open(baseUrl);
        });
        step("Заголовок страницы должен содержать название компании '«Т1 Консалтинг»'", () -> {
            String expectedTitle = "«Т1 Консалтинг»";
            String actualTitle = title();
            assertThat(actualTitle).isEqualTo(expectedTitle);
        });
        step("Проверка наличия меню не главной странице ", () -> {
            $$("div.menu-top")
                    .shouldBe(texts("О компании Услуги Карта решений Продукты Контакты"));
        });
    }

    @Tag("smoke")
    @Test
    @Description("Проверка доступности сайта и переход в раздел -О компании- и Девиз компании")
    @DisplayName("Проверка раздела о компании  ")
    void whatMottoCompanyIsTest() {
        step("Открытие сайта по адресу - " + baseUrl, () -> {
            open(baseUrl);
        });
        step(" Позиционирование по меню сайта в раздел - О компании", () -> {
            $(byLinkText("О компании")).click();
            $x("//h1[contains(text(),'О компании')]").shouldBe(visible);
        });
        step("Проверка декларации Миссии и Девиз компании ", () -> {
            $$("div.l-about")
                    .shouldHave(texts("Быть доверенным партнером и проводником в цифровой мир для компаний, стремящихся стать лучшими в своем деле."));
        });
    }

    // Проверка главного меню сайта с переменными названиями разделов меню, их наполнение страниц разделов
    @Owner("Курс  QA.GURU")
    @Link(value = "Тестирование UI сайта", url = "https://www.t1-consulting.ru/")
    @Tag("properties")
    @ValueSource(strings = {"О компании", "Услуги", "Карта решений", "Продукты", "Контакты"})
    @ParameterizedTest(name = "Проверяем наличие пункта - \"{0}\" в меню навигации по сайту")
    void mainMenuTest(String subject) {
        step("Открытие сайта по адресу -  " + baseUrl, () -> {
            openPageOfSite(baseUrl);
        });
        step("Позиционируем по меню, пункт  - " + subject, () ->
                $(byLinkText(subject)).click());
        step("проверка наличия раздела ", () ->
                $x("//h1[contains(text(),subject)]").shouldBe(visible));
    }

    @Owner("Курс  QA.GURU")
    @Link(value = "Тестирование UI сайта", url = "https://www.t1-consulting.ru/")
    @Test
    @DisplayName("Позиционирование по разделу <О компании> и определение наличия и достоверности меню раздела")
    void menuAboutCompanyTest() {
        step("Открытие  сайта по адресу - " + baseUrl, () -> {
            openPageOfSite(baseUrl);
        });
        step("Позиционируем по меню, пункт  - ", () ->
                $(byLinkText("О компании")).click());
        step("Проверка наличия меню в разделе 'О компании' ", () -> {
            $$("div.menu-inner")
                    .shouldBe(texts("Пресс-центр Достижения Команда Партнеры Группа компаний Кодекс деловой этики"));
        });
    }

    //    Проверка  меню, переход по меню и контроль наполнения разделов
    @Owner("Курс  QA.GURU")
    @Link(value = "Тестирование UI сайта", url = "https://www.t1-consulting.ru/")
    @ValueSource(strings = {"Пресс-центр", "Достижения", "Команда", "Партнеры", "Группа компаний", "Кодекс деловой этики"})
    @ParameterizedTest(name = "Проверяем наличие пункта - \"{0}\" в меню навигации по сайту в разделе <О компании>")
    void whatWeKnowAboutCompanyTest(String subject) {
        step("Открытие  сайта по адресу - " + baseUrl, () -> {
            open(baseUrl + "about/");
        });
        step("Позиционируем по меню, пункт  - " + subject, () ->
                $(byLinkText(subject)).click());
        step("проверка наличия раздела и его наполнении", () ->
                $x("//h1[contains(text(),subject)]").shouldBe(visible));
    }

    @Owner("Курс  QA.GURU")
    @Link(value = "Тестирование UI сайта", url = "https://www.t1-consulting.ru/")
    @Test
    @DisplayName("Проверка наличия и доступности к скачиванию инструкции по эксплуатации системы <T1 Watchman>")
    void upLoadAndParsePdfTest() throws Exception {
        step("Открытие раздела содержащего материалы к скачиванию", () -> {
            Selenide.open("https://www.t1-consulting.ru/services/middl/tsc-business-monitoring/");
        });
        step("Скачивание инструкции по эксплуатации системы  <T1 Watchman>", () -> {
            File pdfDownload = Selenide.$("div.txt_leftcol").$(byText("Инструкция по эксплуатации")).download();
            PDF pdf = new PDF(pdfDownload);
            assertThat(pdf.author).contains("Elena M. Zhemerova"); //Проверка файла PDF на автора-разработчика инструкции
            assertThat(pdf.numberOfPages).isEqualTo(77);           //число страниц инструкции
            assertThat(pdf.text).contains("T1 Watchman", "Инструкция по эксплуатации"); //содержание текста в инструкции
        });
    }

    @Owner("Курс  QA.GURU")
    @Link(value = "Тестирование UI сайта", url = "https://www.t1-consulting.ru/")
    @Story("Ошибки в консоли")
    @Test
    @Description("Проверка на ошибки")
    @DisplayName("Проверка на наличие ошибок в console log ")
    void consoleShouldNotHaveErrorsTest() {
        step("Открытие сайта по адресу - " + baseUrl, () -> {
            open(baseUrl);
        });
        step("Консольные логи не должны содержать значение -  'SEVERE'", () -> {
            String consoleLogs = Attach.getConsoleLogs();
            String errorText = "SEVERE";
            assertThat(consoleLogs).doesNotContain(errorText);
        });
    }

    @Owner("Курс  QA.GURU")
    @Link(value = "Тестирование UI сайта", url = "https://www.t1-consulting.ru/")
    @Test
    @Tag("smoke")
    @DisplayName("Проверка поиска по сайту")
    void successfulSearchTest() {
        step("Открытие сайта по адресу - " + baseUrl, () -> {
            open(baseUrl);
        });
        step("Формирование запроса на поиск ключевого слова по сайту", () -> {
            $(".but-s").click();
            $("[name=q]").setValue("Watchman").pressEnter();
        });
        step(" Контроль наличия результатов поиска по сайту", () -> {
            $("div.t-achiement").shouldHave(text("Watchman"));
        });
    }

    void openPageOfSite(String urlSite) {
        step("Открытие сайта - 'https://www.t1-consulting.ru/'", () ->
                open(baseUrl));
    }

}


