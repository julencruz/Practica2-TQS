Feature: Aplicar descuento al carrito

  Scenario Outline: Aplicar descuento
    Given el usuario esta en la pagina web <pagina>
    When el usuario añada al carrito el producto <producto>
    And el usuario haga clic en el carrito
    Then debe aparecer en el carrito <totalSin>
    And el usuario hace clic en el boton con clase cart-ingka-accordion__heading
    And el usuario hace clic en la barra de descuento
    And el usuario escribe el codigo <codigo>
    And el usuario presiona submit
    Then debe aparecer en el carrito <totalCon>
    And muere

    Examples: 
      | pagina                                          | producto | totalSin | totalCon | codigo     |
      | https://www.ikea.com/es/es/search/?q=escritorio | 0        | 79.99    | 74.99    | IKEA2512PF |


