Feature: BusquedaProductosExistentes

  Scenario Outline: Buscar producto existente
    Given el usuario esta en la pagina principal
    When el usuario haga clic en la barra de busqueda
    And el usuario escriba <algo>
    And el usuario haga clic en el icono de buscar
    Then se ha buscado <algo>
    And deben aparecer al menos <valor> numero de productos
    And muere

    Examples: 
      | algo    | valor |
      | blahaj  |     2 |
      | florero |    61 |
