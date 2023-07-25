@bookCart
Feature: Book Search

  Background:
    Given the user sets the HTTP method "GET"

  Scenario Outline: Find the book id for a book with the name "Soul of the Sword"
    Given the service endpoint "/api/Book"
    When the user searches for the book
    Then I should find the book id

    Examples:
      | bookName |

  Scenario Outline: Find all similar books as "Soul of the Sword"
    When the user searches for the book similar to Soul of the Sword by "Id"
    Then the list of similar books include "<similarName>" with the price "<Price>" and author as "<Author>" and category as "<Category>"

    Examples:
      | similarName          | Price  | Author             | Category |
      | A Dance with Dragons | 412.00 | George R.R. Martin | Fantasy  |