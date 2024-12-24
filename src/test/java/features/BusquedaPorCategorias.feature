Feature: BusquedaPorCategorias

  Scenario Outline: Buscar producto clicando categorias
    Given el usuario esta en la pagina principal
    When el usuario haga clic en el enlace <categoria>
    When el usuario haga clic en el enlace <categoria-interior>
    Then se ha buscado <categoria-interior>
    And muere
    

    Examples: 
      | categoria   | categoria-interior |
      | Muebles     | Sillas             |
      | Iluminación | Bombillas LED      |
