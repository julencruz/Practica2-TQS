Feature: DetallesProducto

  Scenario Outline: Detalles del producto
    Given el usuario esta en la pagina web <pagina>
    When el usuario haga clic en el producto <producto>
    Then deben aparecer los detalles del producto
    And muere

    Examples: 
      | pagina                                          | producto |
      | https://www.ikea.com/es/es/search/?q=termometro | 1        |
      | https://www.ikea.com/es/es/search/?q=edredon    | 2        |

