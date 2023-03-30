
Feature: Books module
  As a students, I should be able to borrow book

  @ui @db @US07BT
  Scenario: Student borrow new book
    Given the "student" on the home page
    And the user navigates to "Books" page
    And the user searches for "White Niggers" book
    When the user clicks Borrow Book
    Then verify that book is shown in "Borrowing Books" page
    And  verify logged student has same book in database