package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;




class CardDeliveryTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
    }

    @Test
    void shouldValidDataTest() {
        $("[placeholder='Город']").setValue("Москва");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Семенова Наталья");
        $("[name='phone']").setValue("+79640000000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id=notification] .notification__title")
                .waitUntil(visible, 15000)
                .shouldBe(exactText("Успешно!"));
    }

    @Test
    void shouldInvalidCityTest1() {
        $("[placeholder='Город']").setValue("");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
        String requireDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
        String requireDate = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Семенова Наталья");
        $("[name='phone']").setValue("+79640000000");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id=notification] .notification__title")
                .waitUntil(visible, 15000)
                .shouldBe(exactText("Успешно!"));
    }

    @Test
    void shouldInvalidNameSurnameTest1() {
        $("[placeholder='Город']").setValue("Москва");
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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
        String requireDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(requireDate);
        $("[name='name']").setValue("Семенова Наталья");
        $("[name='phone']").setValue("+79640000000");
        $(".button__text").click();
        $("[data-test-id='agreement'].input_invalid").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

}