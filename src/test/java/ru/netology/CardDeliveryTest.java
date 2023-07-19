package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


class CardDeliveryTest {
    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
    }

    @Test
    void shouldValidDataTest() {
        $("[placeholder='Город']").setValue("Москва");
        String requireDate = generateDate(3);
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Семенова Наталья");
        $("[name='phone']").setValue("+79640000000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='notification']").shouldHave(Condition.text("Встреча успешно забронирована на " + requireDate), Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void shouldInvalidCityTest1() {
        $("[placeholder='Город']").setValue("");
        String requireDate = generateDate(3);
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Семенова Наталья");
        $("[name='phone']").setValue("+79640000000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldInvalidCityTest2() {
        $("[placeholder='Город']").setValue("Moscow");
        String requireDate = generateDate(3);
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Семенова Наталья");
        $("[name='phone']").setValue("+79640000000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldInvalidDateTest1() {
        $("[placeholder='Город']").setValue("Москва");
        $("[name='name']").setValue("Семенова Наталья");
        $("[name='phone']").setValue("+79640000000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id=date] .input_invalid").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldInvalidDateTest2() {
        $("[placeholder='Город']").setValue("Москва");
        String requireDate = generateDate(2);
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Семенова Наталья");
        $("[name='phone']").setValue("+79640000000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id=date] .input_invalid").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldDateMoreThan3DaysTest() {
        $("[placeholder='Город']").setValue("Москва");
        String requireDate = generateDate(4);
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Семенова Наталья");
        $("[name='phone']").setValue("+79640000000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='notification']").shouldHave(Condition.text("Встреча успешно забронирована на " + requireDate), Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void shouldInvalidNameSurnameTest1() {
        $("[placeholder='Город']").setValue("Москва");
        String requireDate = generateDate(3);
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("");
        $("[name='phone']").setValue("+79640000000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldInvalidNameSurnameTest2() {
        $("[placeholder='Город']").setValue("Москва");
        String requireDate = generateDate(3);
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Semenova Natalia");
        $("[name='phone']").setValue("+79640000000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldInvalidPhoneTest() {
        $("[placeholder='Город']").setValue("Москва");
        String requireDate = generateDate(3);
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Семенова Наталья");
        $("[name='phone']").setValue("9640000000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldEmptyCheckbox() {
        $("[placeholder='Город']").setValue("Москва");
        String requireDate = generateDate(3);
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Семенова Наталья");
        $("[name='phone']").setValue("+79640000000");
        $(".button__text").click();
        $("[data-test-id='agreement'].input_invalid").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

}