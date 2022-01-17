package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {


    @Test
    public void shouldSendTrueFullForm()   {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Саратов");
        String dateOfMeeting = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(dateOfMeeting);
        $("[data-test-id='name'] input").setValue("Малыш Барбоскин");
        $(byName("phone")).setValue("+77777777777"); // просто что бы попробовать поиск по имени
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(exactText("Успешно! " +
                "Встреча успешно забронирована на "
                + dateOfMeeting), Duration.ofSeconds(15));
    }


    @Test
    public void shouldSendTrueFullFormWithPopUpLineAndCalendar()   {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Са");
        $(withText("Саратов")).click();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(date);
        $(".calendar").sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
        $("[data-test-id='name'] input").setValue("Малыш Барбоскин");
        $(byName("phone")).setValue("+77777777777"); // просто что бы попробовать поиск по имени
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        String dateOfMeetingExpected = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='notification']").shouldBe(exactText("Успешно! " +
                "Встреча успешно забронирована на "
                + dateOfMeetingExpected), Duration.ofSeconds(15));
    }

}
