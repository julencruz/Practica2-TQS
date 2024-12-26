Feature: Sesion guarda carrito
	Scenario Outline: Sesión guarda carrito
    Given el usuario esta en la pagina web <pagina>
    When el usuario añada al carrito el producto <producto>
    And el usuario haga clic en el carrito
    Then debe aparecer en el carrito <total>
    When el usuario vaya a la pagina <pagina2>
    When el usuario vaya a la pagina <pagina>
    And el usuario haga clic en el carrito
    Then debe aparecer en el carrito <total>
    And muere

    Examples: 
      | pagina                                          | producto | pagina2                       | total |
      | https://www.ikea.com/es/es/search/?q=termometro | 1        | https://www.google.com        | 5.99  |

