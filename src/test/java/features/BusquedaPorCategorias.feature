Feature: BusquedaPorCategorias

  Scenario Outline: Buscar producto clicando categorias
    Given el usuario esta en la pagina principal
    When el usuario haga clic en la categoria <categoria>
    When el usuario haga clic en la categoria <categoria-interior>
    Then comprobar que aparece <categoria-interior>
    And muere
    

    Examples: 
      | categoria   | categoria-interior |
      | Muebles     | Sillas             |
      | Iluminación | Bombillas LED      |
