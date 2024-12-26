Feature: Comprobar tienda más cercana

  Scenario Outline: Comprobar tienda más cercana
  	Given el usuario esta en la pagina principal
  	When el usuario hace clic en el texto Seleccionar tienda
  	And el usuario hace clic en la barra de ubicación
  	And el usuario teclee la ubicación <ubicacion>
  	And el usuario presiona submit
  	Then comprobar por distancia
  	And muere
  	
  	Examples: 
      | ubicacion |
      | Sabadell  |


