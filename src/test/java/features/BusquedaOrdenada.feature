Feature: Busqueda ordenada

  Scenario Outline: Buscar producto ordenada
    Given el usuario esta en la pagina principal
    When el usuario haga clic en la barra de busqueda
    And el usuario escriba <algo>
    And el usuario presiona submit
    And el usuario selecciona filtro <filtro>
    And el usuario selecciona opción <opcion>
    Then deben estar ordenados <opcion>
    And muere

    Examples: 
      | algo       | filtro  | opcion |
      | termometro | Ordenar | 1      | 
      | vino       | Ordenar | 2      |
