package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import services.Services;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BookCartSteps {
    Services services;
    Response response;
    private String id;
    List<String> books;

    @Given("the user sets the HTTP method {string}")
    public void theUserSetsTheHTTPMethod(String method) {
        services.setHTTPMethod(method);
    }

    @And("the service endpoint {string}")
    public void theServiceEndpoint(String endpoint) {
        services.setEndpoint(endpoint);
    }

    @When("the user searches for the book")
    public void theUserSearchesForTheBook() {
        services.build();
        response = services.send();
    }

    @When("the user searches for the book with the name {string}")
    public void theUserSearchesForTheBookWithTheName(String bookName) {
        List<String> bookNames = response.getBody().jsonPath().getList();
        boolean found = false;

        for (String book : bookNames) {
            if (book.equals(bookName)) {
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Found the book: " + bookName);
        } else {
            System.out.println("Book not found in the list.");
        }
    }
    @Then("I should find the book id")
    public void iShouldFindTheBookId() {
        id = response.getBody().jsonPath().get("bookId[0]");
    }

    @When("the user searches for the book similar to Soul of the Sword by {string}")
    public void theUserSearchesForTheBookSimilarToSoulOfTheSwordBy(String arg0) {
        services.setEndpoint("/api/Book/GetSimilarBooks/"+id);
        
    }

    @Then("the list of similar books include {string} with the price {string} and author as {string} and category as {string}")
    public void theListOfSimilarBooksIncludeWithThePriceAndAuthorAsAndCategoryAs(String , String arg1, String arg2, String arg3) {
        boolean bookFound = false;
        for (Book book : searchResults) {
            if (book.getName().equals(expectedBookName)) {
                bookFound = true;

                assertEquals(expectedBookDetails.get(0).getPrice(), book.getPrice());
                assertEquals(expectedBookDetails.get(0).getAuthor(), book.getAuthor());
                assertEquals(expectedBookDetails.get(0).getCategory(), book.getCategory());
                break;
            }
        }
    }


}
