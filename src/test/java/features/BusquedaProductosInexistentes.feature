Feature: BusquedaProductosInexistentes

	Scenario Outline: Buscar producto inexistente
	   Given el usuario esta en la pagina principal
	   When el usuario haga clic en la barra de busqueda
	   And el usuario escriba <algo>
	   And el usuario presiona submit
	   Then no deben aparecer resultados
	   And muere
	
	    Examples: 
	      | algo |
	      | TQS  |  
	      | ppp  |