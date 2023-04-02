package com.library.steps;

import com.library.pages.BookPage;
import com.library.pages.BorrowedBooksPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.ConfigurationReader;
import com.library.utility.DB_Util;
import com.library.utility.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class US07BT_StepDefs {

    LoginPage loginPage = new LoginPage();
    BookPage bookPage = new BookPage();

    BorrowedBooksPage borrowedBooksPage = new BorrowedBooksPage();

    @FindBy(xpath = "(//tbody//td/a)")
    public List<WebElement> btn_allReturnBook;

    String bookName;

   /* @Given("the {string} on the home page")
    public void theOnTheHomePage(String user) {
        BrowserUtil.waitForPageToLoad(15);
        loginPage.login("student");

    }

    @And("the user navigates to {string} page")
    public void theUserNavigatesToPage(String module) {
        BrowserUtil.waitForPageToLoad(10);
        bookPage.navigateModule("Books");

    }*/

    @And("the user searches for {string} book")
    public void theUserSearchesForBook(String bookName) {
        BrowserUtil.waitForPageToLoad(10);
        this.bookName = bookName;
        bookPage.search.sendKeys(bookName);
    }

    @When("the user clicks Borrow Book")
    public void theUserClicksBorrowBook() {
        bookPage.borrowBook(bookName).click();
    }

    @Then("verify that book is shown in {string} page")
    public void verifyThatBookIsShownInPage(String borrowModule) {
        bookPage.navigateModule("Borrowing Books");
        BrowserUtil.waitForPageToLoad(10);

        String expected = "";
        for (WebElement eachBook : borrowedBooksPage.allBorrowedBooksName) {
            if (eachBook.getText().equals(bookName)) {
                expected = eachBook.getText();
            }
        }
        Assert.assertEquals(expected, bookName);

    }

    @And("verify logged student has same book in database")
    public void verifyLoggedStudentHasSameBookInDatabase() {

        String query = "select u.full_name,b.name,bb.borrowed_date,bb.planned_return_date,bb.is_returned from users u join book_borrow bb on u.id = bb.user_id join books b on b.id=bb.book_id where u.email='"+ConfigurationReader.getProperty("student_username")+"' and b.name ='"+bookName+"' and bb.is_returned=0";

/*
I made it dynamic, but the original sql page query is:
select u.full_name,b.name,bb.borrowed_date,bb.planned_return_date,bb.is_returned from users u join book_borrow bb on u.id = bb.user_id join books b on b.id=bb.book_id
where  u.email='student5@library' and b.name ='White Niggers' and bb.is_returned=0
*/

        DB_Util.runQuery(query);

        String actualBookData = DB_Util.getCellValue(1, 2);
        String expectedBookData=bookName;

        Assert.assertEquals(expectedBookData,actualBookData);
        System.out.println("actualBookData = " + actualBookData);
        System.out.println("expectedBookData = " + expectedBookData);


        //----- After this point, I'm returning the book to keep the test re-applicable------

        for (int i = 1; i <= borrowedBooksPage.allBorrowedBooksName.size(); i++) {


            if (Driver.getDriver().findElement(By.xpath("(//tbody//td[2])[" + i + "]")).getText().equals(bookName) &&
                    Driver.getDriver().findElement(By.xpath("(//tbody//td[6])[" + i + "]")).getText().contains("NOT RETURNED")) {
                WebElement btn = Driver.getDriver().findElement(By.xpath(" (//tbody//td/a)[" + i + "]"));
                btn.click();

                BrowserUtil.waitForVisibility(bookPage.toastMessage, 10);
                Assert.assertTrue(bookPage.toastMessage.isDisplayed());

            }
        }










    }

}
