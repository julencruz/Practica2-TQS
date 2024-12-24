Feature: AñadirCarrito

  Scenario Outline: Añadir producto al carrito
    Given el usuario esta en la pagina <pagina>
    When el usuario añada al carrito el producto <producto>
    And el usuario haga clic en el carrito
    Then debe aparecer en el carrito <total>
    And muere

    Examples: 
      | pagina                                          | producto | total |
      | https://www.ikea.com/es/es/search/?q=termometro | 1        | 5.99  |


