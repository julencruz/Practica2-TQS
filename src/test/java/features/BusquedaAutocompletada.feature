Feature: BusquedaAutocompletada

  Scenario Outline: Buscar producto autocompletado
    Given el usuario esta en la pagina principal
    When el usuario haga clic en la barra de busqueda
    And el usuario escriba <algo>
    Then deben autocompletarse resultados que empiezan por <algo>
    And muere

    Examples: 
      | algo |
      | smås |
      | hövo |
