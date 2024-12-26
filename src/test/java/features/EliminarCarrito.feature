Feature: Eliminar del carrito
	Scenario Outline: Eliminar productos del carrito
    Given el usuario esta en la pagina web <pagina>
    When el usuario añada al carrito el producto <producto>
    When el usuario añada al carrito el producto <producto>
    And el usuario haga clic en el carrito
    Then debe aparecer en el carrito <total>
    When el usuario elimine producto del carrito 1
    Then debe aparecer en el carrito <total2>
    When el usuario elimine producto del carrito 1
    Then debe aparecer en el carrito <total3>
    And muere

    Examples: 
      | pagina                                          | producto | total  | total2 | total3 |
      | https://www.ikea.com/es/es/search/?q=termometro | 1        | 11.98  | 5.99   | 0      |

