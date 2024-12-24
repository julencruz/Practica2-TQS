Feature: BusquedaFiltrada

  Scenario Outline: Buscar producto filtrados
    Given el usuario esta en la pagina principal
    When el usuario haga clic en la barra de busqueda
    And el usuario escriba <algo>
    And el usuario selecciona filtro <filtro>
    And el usuario selecciona opción <opcion>
    Then se ha buscado <algo>
    And deben aparecer al menos <valor> numero de productos
    And muere

    Examples: 
      | algo |
      | smås |
      | hövo |
