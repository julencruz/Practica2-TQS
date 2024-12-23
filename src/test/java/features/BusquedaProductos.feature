Feature: BusquedaProductos

  Scenario Outline: Buscar blahaj
    Given el usuario esta en la pagina principal
    When el usuario haga clic en la barra de busqueda
    And el usuario escriba <algo>
    And el usuario haga clic en el icono de buscar
    Then deben aparecer al menos <valor> numero de productos

    Examples: 
      | algo   | valor |
      | blahaj |     2 |
