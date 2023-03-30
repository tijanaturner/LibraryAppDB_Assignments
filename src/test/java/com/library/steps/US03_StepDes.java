package com.library.steps;

import com.library.pages.BasePage;
import com.library.pages.BookPage;
import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;

public class US03_StepDes extends BasePage {
    LoginPage loginPage=new LoginPage();
    BookPage bookPage=new BookPage();
    List<String> actualCategoryList;
    DashBoardPage dashBoardPage=new DashBoardPage();
    String actualBook_categories;
    @Given("the {string} on the home page")
    public void the_on_the_home_page(String userType) {

        BrowserUtil.waitFor(4);
        loginPage.login(userType);
        dashBoardPage.accountHolderName.isDisplayed();
        BrowserUtil.waitFor(4);

    }
    @When("the user navigates to {string} page")
    public void the_user_navigates_to_page(String moduleName) {
        new DashBoardPage().navigateModule(moduleName);
        BrowserUtil.waitFor(4);


    }
    @When("the user clicks book categories")
    public void the_user_clicks_book_categories() {
     bookPage.mainCategoryElement.click();

        BrowserUtil.waitFor(4);
    }
    @Then("verify book categories must match book_categories table from db")
    public void verify_book_categories_must_match_book_categories_table_from_db() {

        actualCategoryList=BrowserUtil.getAllSelectOptions(bookPage.mainCategoryElement);
        actualCategoryList.remove(0);

        System.out.println("actualCategoryList = " + actualCategoryList);

        String query="select name from book_categories";
        DB_Util.runQuery(query);

        List<String>expectedCategoryList=DB_Util.getColumnDataAsList(1);

        System.out.println("expectedCategoryList = " + expectedCategoryList);
        Assert.assertEquals(actualCategoryList,expectedCategoryList);



    }
}
