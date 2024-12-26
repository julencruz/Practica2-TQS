Feature: Cambiar idioma

  Scenario Outline: Cambiar el idioma a inglés
  	Given el usuario esta en la pagina principal
  	When el usuario clique en enlace con aria-label Cambia de idioma
  	And el usuario hace clic en el texto English
  	Then comprobar que aparece Products
  	And muere


