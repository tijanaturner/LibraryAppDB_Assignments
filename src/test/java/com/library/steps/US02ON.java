package com.library.steps;

import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class US02ON {

    LoginPage login = new LoginPage();
    DashBoardPage dbp = new DashBoardPage();
    String actualBBNumber;

  /*  @Given("the {string} on the home page")
    public void the_on_the_home_page(String librarian) {
        login.login(librarian);
    }
*/
    @When("the librarian gets borrowed books number")
    public void the_librarian_gets_borrowed_books_number() {
        actualBBNumber = dbp.borrowedBooksNumber.getText();
        System.out.println("Actual BB Num: " + actualBBNumber);

    }

    @Then("borrowed books number information must match with DB")
    public void borrowed_books_number_information_must_match_with_db() {
        String query = "select count(is_returned) from book_borrow where is_returned = 0";

        DB_Util.runQuery(query);

        String expectedBBN = DB_Util.getFirstRowFirstColumn();
        System.out.println("Expected BB Num: " +expectedBBN);

        Assert.assertEquals(expectedBBN,actualBBNumber);



    }
}
