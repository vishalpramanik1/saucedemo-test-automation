Feature: SauceDemo End-to-End Tests

  Scenario: Login with valid credentials
    Given I open the saucedemo login page
    When I login with username "standard_user" and password "secret_sauce"
    Then I should land on the products page

  Scenario: Login with invalid credentials
    Given I open the saucedemo login page
    When I login with username "invalid_user" and password "wrong_pass"
    Then I should see an error message "Epic sadface: Username and password do not match any user in this service"

  Scenario: Login with locked out user
    Given I open the saucedemo login page
    When I login with username "locked_out_user" and password "secret_sauce"
    Then I should see an error message "Epic sadface: Sorry, this user has been locked out."

  Scenario: Logout after login
    Given I am logged in as "standard_user" with password "secret_sauce"
    When I logout from the menu
    Then I should be redirected to login page

  Scenario: Add single product to cart
    Given I am logged in as "standard_user" with password "secret_sauce"
    When I add product "Sauce Labs Backpack" to the cart
    Then the cart badge should show "1"

  Scenario: Add multiple products to cart
    Given I am logged in as "standard_user" with password "secret_sauce"
    When I add product "Sauce Labs Backpack" to the cart
    And I add product "Sauce Labs Bike Light" to the cart
    Then the cart badge should show "2"

  Scenario: Remove product from cart
    Given I am logged in as "standard_user" with password "secret_sauce"
    And I add product "Sauce Labs Backpack" to the cart
    When I remove product "Sauce Labs Backpack" from the cart
    Then the cart badge should not be visible

  Scenario: Checkout complete order
    Given I am logged in as "standard_user" with password "secret_sauce"
    And I add product "Sauce Labs Backpack" to the cart
    When I checkout with first name "John" last name "Doe" postal code "12345"
    Then I should see a confirmation message "THANK YOU FOR YOUR ORDER"

  Scenario: Sort products by Price low to high
    Given I am logged in as "standard_user" with password "secret_sauce"
    When I sort products by "Price (low to high)"
    Then the first product price should be lowest

  Scenario: Product details page navigation
    Given I am logged in as "standard_user" with password "secret_sauce"
    When I open the product details for "Sauce Labs Backpack"
    Then the product page should show title "Sauce Labs Backpack"