Feature: Iniciar sesión

  Scenario Outline: Iniciar sesión
    Given el usuario esta en la pagina principal
    When el usuario haga clic en el enlace "https://www.ikea.com/es/es/profile/"
    When el usuario haga clic en el enlace "https://www.ikea.com/es/es/profile/login?itm_campaign=wlo-user-slider-logged-out&itm_element=homepage&itm_content=login"
    When el usuario haga clic en el enlace "#"
    And el usuario complete el formulario de login con <email> y <contraseña>
    Then comprobar que aparece IKEA Family
    And muere
    

    Examples: 
      | email                 | contraseña               |
      | tqspractica@gmail.com | TestIQualitatDeSoftware. |
