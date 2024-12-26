Feature: AñadirFavoritos

  Scenario Outline: Añadir producto a lista de favoritos
    Given el usuario esta en la pagina web <pagina>
    When el usuario hace clic en el boton con clase pip-favourite-button
    When el usuario hace clic en el boton con clase hnf-toast__action-message
    Then comprobar que aparece <producto>
    And muere

    Examples: 
      | pagina                                                                           | producto |
      | https://www.ikea.com/es/es/p/klockis-reloj-pequeno-multifuncion-blanco-80277004/ | KLOCKIS  |


