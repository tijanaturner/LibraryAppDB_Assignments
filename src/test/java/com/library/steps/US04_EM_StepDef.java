package com.library.steps;

import com.library.pages.BasePage;
import com.library.pages.BookPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import com.library.utility.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class US04_EM_StepDef extends BasePage {
    LoginPage loginPage = new LoginPage();
    BookPage bookPage = new BookPage();

    String bookName;


  /*  @Given("the {string} on the home page")
    public void the_on_the_home_page(String userType) {

        BrowserUtil.waitFor(4);
        loginPage.login(userType);

    }

    @Given("the user navigates to {string} page")
    public void the_user_navigates_to_page(String page) {
        //navigate to books page
        BrowserUtil.waitFor(2);
        navigateModule(page);
     }


    @When("the user searches for {string} book")
    public void the_user_searches_for_book(String BookName) {
        BrowserUtil.waitFor(2);
        bookName = BookName;
        bookPage.search.sendKeys(BookName);
    }*/
    @When("the user clicks edit book button")
    public void the_user_clicks_edit_book_button() {
        BrowserUtil.waitFor(2);
        bookPage.editBook(bookName).click();
        BrowserUtil.waitFor(2);
    }
    @Then("book information must match the Database")
    public void book_information_must_match_the_database() {
        String query="select * from books where name='"+bookName+"'";

        DB_Util.runQuery(query);
        String expectedName = DB_Util.getCellValue(1, "name");
        BrowserUtil.waitFor(2);
        String actualName=bookPage.bookName.getAttribute("value");
        System.out.println("expectedName = " + expectedName);
        System.out.println("book name= "+actualName);
        Assert.assertEquals(expectedName,actualName);

    }
}
