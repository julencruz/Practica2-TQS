Feature: BusquedaFiltrada

  Scenario Outline: Buscar producto filtrados
    Given el usuario esta en la pagina principal
    When el usuario haga clic en la barra de busqueda
    And el usuario escriba <algo>
    And el usuario presiona submit
    And el usuario selecciona filtro <filtro>
    And el usuario selecciona opción <opcion>
    Then comprobar que aparece <algo>
    And deben aparecer al menos <valor> numero de productos
    And muere

    Examples: 
      | algo | filtro | opcion | valor |
      | vaso | Color  | 2      | 5     |
      | vaso | Precio | 0      | 8     |
