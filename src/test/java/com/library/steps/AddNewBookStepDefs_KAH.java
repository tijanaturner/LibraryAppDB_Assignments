package com.library.steps;

import com.library.pages.BasePage;
import com.library.pages.BookPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class AddNewBookStepDefs_KAH extends BasePage {
    LoginPage loginPage = new LoginPage();
    BookPage bookPage = new BookPage();
    String expectedBookName;



    @Given("the {string} on the home page")
    public void the_on_the_home_page(String userType) {
        loginPage.login(userType);

    }
    @Given("the user navigates to {string} page")
    public void the_user_navigates_to_page(String moduleName) {
        navigateModule(moduleName);

    }
    @When("the librarian click to add book")
    public void the_librarian_click_to_add_book() {
        bookPage.addBook.click();


    }
    @When("the librarian enter book name {string}")
    public void the_librarian_enter_book_name(String bookName) {
        bookPage.bookName.sendKeys(bookName);
        this.expectedBookName=bookName;


    }
    @When("the librarian enter ISBN {string}")
    public void the_librarian_enter_isbn(String isbn) {
        bookPage.isbn.sendKeys(isbn);


    }
    @When("the librarian enter year {string}")
    public void the_librarian_enter_year(String year) {
        bookPage.year.sendKeys(year);


    }
    @When("the librarian enter author {string}")
    public void the_librarian_enter_author(String author) {
        bookPage.author.sendKeys(author);


    }
    @When("the librarian choose the book category {string}")
    public void the_librarian_choose_the_book_category(String bookCategory) {
        BrowserUtil.selectOptionDropdown(bookPage.categoryDropdown,bookCategory);


    }
    @When("the librarian click to save changes")
    public void the_librarian_click_to_save_changes() {
        bookPage.saveChanges.click();


    }
    @Then("verify {string} message is displayed")
    public void verify_message_is_displayed(String toastMessage) {
        Assert.assertTrue(bookPage.toastMessage.isDisplayed());


    }
    @Then("verify {string} information must match with DB")
    public void verify_information_must_match_with_db(String actualBookName) {
        String query = "select name from books\n" +
                "where name='"+expectedBookName+"'";
        DB_Util.runQuery(query);
        actualBookName=DB_Util.getFirstRowFirstColumn();
        Assert.assertEquals(expectedBookName,actualBookName);
        System.out.println("expectedBookName = " + expectedBookName);
        System.out.println("actualBookName = " + actualBookName);

    }


}
