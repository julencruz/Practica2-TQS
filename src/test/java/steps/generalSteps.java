package steps;

import static org.testng.Assert.assertTrue;

import java.text.Normalizer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class generalSteps {
	
	
	WebDriver driver;
	WebDriverWait wait;
	
	public void acceptCookies() {
	    wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    try {
	        // Buscar el botón de aceptación de cookies por su ID
	        WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
	        acceptCookiesButton.click();  // Hacer clic en el botón de aceptar cookies
	        System.out.println("Cookies aceptadas");

	        // Esperar hasta que el div de cookies desaparezca
	        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("onetrust-group-container")));
	        System.out.println("El div de cookies ha desaparecido");

	        // Esperar a que el campo de búsqueda sea clickeable
	        wait.until(ExpectedConditions.elementToBeClickable(By.id("ikea-search-input")));
	        System.out.println("El campo de búsqueda está disponible");

	    } catch (Exception e) {
	        System.out.println("No se encontró el botón de cookies.");
	    }
	}
	
	//Todas la usan, es para que se cierre el navegador abierto
	@And("muere")
	public void muere() {
		driver.quit();
	}
	
	//AplicarDescuento.feature sirve para hacerle focus a la barra donde escribir el codigo de descuento.
	@And("el usuario hace clic en la barra de descuento")
	public void usuarioClicDescuento() {
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement discountBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("discountCode")));
	    discountBar.click();
	}
	
	//AplicarDescuento.feature una vez hecho focus, sirve para escribir el codigo de descuento.
	@And("^el usuario escribe el codigo (.*)")
	public void usuarioEscribeDescuento(String descuento) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement discountBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("discountCode")));
	    discountBar.sendKeys(descuento);
	}
	
	//SesiónGuardaCarrito.feature, sirve para ir a otra pagina sin hacer una nueva ventana para el firefox
	@When("^el usuario vaya a la pagina (.*)")
	public void usuarioVaA(String pagina) {
		driver.get(pagina);
	}
	
	//BusquedaFiltrada.feature, BusquedaOrdenada.feature, sirve para escoger el botón del filtro
	@And("^el usuario selecciona filtro (.*)")
	public void elUsuarioSeleccionaFiltro(String filtro) throws InterruptedException {
		System.out.println("Se selecciona filtro " + filtro);
		Thread.sleep(2000);
		WebElement filtroBoton = driver.findElement(By.xpath("//span[text()='" + filtro + "']/ancestor::button"));
        filtroBoton.click();
	}
	
	//BusquedaFiltrada.feature, BusquedaOrdenada.feature, sirve para escoger una opción dentro del popup del filtro
	@And("^el usuario selecciona opción (.*)")
	public void seleccionarOpcionFiltro(int opcion) {
		System.out.println("Se seleccionará la " + (opcion+1) +"ª opción del popup");
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("plp-drop-down")));
	    WebElement fieldset = popup.findElement(By.cssSelector("fieldset"));

	    // Buscar todos los elementos clicables dentro del fieldset
	    List<WebElement> elements = null;
	    
	    elements = fieldset.findElements(By.cssSelector("label.plp-checkbox__wrapper"));
	    if (elements.isEmpty()) {
	    	elements = fieldset.findElements(By.cssSelector("input"));
	    }
	    elements.get(opcion).click();;
	}
	
	//AñadirCarrito.feature, AñadirProductoFavoritos.feature, 
	//AplicarDescuento.feature, DetallesProducto.feature, 
	//EliminarCarrito.feature, SesionGuardaCarrito.feature, 
	//sirve para abrir un navegador nuevo ya abierto con la pagina que se especifique en el parametro.
	@Given("^el usuario esta en la pagina web (.*)")
	public void elUsuarioEstaEnLaPagina(String pagina) {
		System.setProperty("webdriver.gecko.driver", "Drivers/geckodriver.exe");
		driver = new FirefoxDriver();
		driver.navigate().to(pagina);
		acceptCookies();
	}
	
	//BusquedaAutocompletada.feature, BusquedaFiltrada.feature, 
	//BusquedaOrdenada.feature, BusquedaPorCategorias.feature, 
	//BusquedaProductosExistentes.feature, BusquedaProductosInexistentes.feature, 
	//CambiarIdioma.feature, ComprobarTiendaMasCercana.feature, IniciarSesión.feature
	//Sirve para abrir el navegador abierto con la pagina de ikea.com España
	@Given("el usuario esta en la pagina principal")
	public void elUsuarioEstaEnLaPaginaPrincipal() {
		System.setProperty("webdriver.gecko.driver", "Drivers/geckodriver.exe");
		driver = new FirefoxDriver();
		driver.navigate().to("https://www.ikea.com/es/es/");
		acceptCookies();
	}
	
	//BusquedaAutocompletada.feature, BusquedaFiltrada.feature, 
	//BusquedaOrdenada.feature, BusquedaProductosExistentes.feature, BusquedaProductosInexistentes.feature
	//Sirve para hacer clic en la barra de búsqueda
	@When("el usuario haga clic en la barra de busqueda")
	public void clicBarraBusqueda() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.id("ikea-search-input")).click();
		System.out.println("Barra de busqueda clicada");
	}
	
	//BusquedaAutocompletada.feature, BusquedaFiltrada.feature, 
	//BusquedaOrdenada.feature, BusquedaProductosExistentes.feature, BusquedaProductosInexistentes.feature
	//Sirve para una vez en focus la barra de busqueda, escribir algo
	@And("^el usuario escriba (.*)")
	public void escribaAlgo(String algo) {
	    WebElement searchBox = driver.findElement(By.id("ikea-search-input"));
	    searchBox.sendKeys(algo);
	    System.out.println("Se ha escrito " + algo);
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.textToBePresentInElementValue(searchBox, algo)
	    );
	}
	
	//DetallesProducto.feature, sirve para darle clic al producto e ir a la página de especificacion
	@When("^el usuario haga clic en el producto (.*)")
	public void clicProducto(int indice) throws InterruptedException {
		Thread.sleep(1000);
		List<WebElement> elementos = driver.findElements(By.cssSelector("div.plp-product-list__products div.plp-fragment-wrapper"));
		elementos.get(indice).click();
		System.out.println("Producto clicado");
	}
	
	//ComprobarTiendaMasCercana.feature, sirve para escribir en la barra de ubicación.
	@And("^el usuario teclee la ubicación (.*)")
	public void tecleeUbicación(String ubicacion) {
	    WebElement searchBox = driver.findElement(By.id("hnf-store-search"));
	    searchBox.sendKeys(ubicacion);
	    System.out.println("Se ha escrito " + ubicacion);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.textToBePresentInElementValue(searchBox, ubicacion)
	    );
	}
	
	//BusquedaPorCategorias.feature, sirve para dar clic a la categoria principal
	@When("^el usuario haga clic en la categoria (.*)$")
	public void usuarioClicCategoria(String nombre) {
		System.out.println("Clicando enlace");
        // Normalizar el nombre: quitar espacios y reemplazar por guiones, todo en minúsculas
    	String nombreNormalizado = Normalizer.normalize(nombre.trim().toLowerCase().replace(" ", "-"), Normalizer.Form.NFD);
    	nombreNormalizado = nombreNormalizado.replaceAll("[^\\p{ASCII}]", "");

        // Buscar el enlace cuyo href comience con la palabra clave normalizada
        WebElement enlace = driver.findElement(By.xpath("//a[starts-with(@href, 'https://www.ikea.com/es/es/cat/" + nombreNormalizado + "')]"));

        // Hacer clic en el enlace
        enlace.click();
	}
    
	//IniciarSesion.feature, sirve para, de forma general, seleccionar los enlaces segun su href. 
    @When("^el usuario haga clic en el enlace (.*)$")
    public void clicAEnlace(String enlace) {
    	wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement enlaceElement = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(@href, '" + enlace.replace("\"", "") + "')]")
        ));

        enlaceElement.click();
    }
    
    //AñadirCarrito.feature, AplicarDescuento.feature, 
    //EliminarCarrito.feature, SesionGuardaCarrito.feature
    //Sirve para que en la lista donde salen los productos, puedas escoger el que quieres según su indice y lo añada directamente al carrito
    @When("^el usuario añada al carrito el producto (.*)$")
    public void añadirProductoAlCarrito(int indice) throws InterruptedException {
    	Thread.sleep(3000);
        // Encontrar todos los elementos de los productos en la lista
        List<WebElement> elementos = driver.findElements(By.cssSelector("div.plp-product-list__products div.plp-fragment-wrapper"));
        
        // Seleccionar el producto por el índice y buscar el botón de añadir al carrito dentro de ese producto
        WebElement producto = elementos.get(indice);
        
        // Usar XPath para encontrar el botón "Añadir al carrito" dentro del producto seleccionado
        WebElement botonAñadir = producto.findElement(By.xpath(".//button[contains(@aria-label, 'Añadir')]"));
        
        // Hacer clic en el botón
        botonAñadir.click();
        
        System.out.println("Producto añadido al carrito");
    }
    
    //CambiarIdioma.feature, sirve para escoger enlaces segun su aria-label (intento de generalizar aunque solamente 1 feature la necesitaba)
    @When("^el usuario clique en enlace con aria-label (.*)")
    public void hacerClickEnEnlacePorAriaLabel(String ariaLabel) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement enlace = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@aria-label, '" + ariaLabel + "')]")));
        enlace.click();
    }

    //IniciarSesion.feature, sirve para completar el formulario de login
    @And("^el usuario complete el formulario de login con (.*) y (.*)$")
    public void completarFormulario(String email, String password) {
        WebElement formulario = driver.findElement(By.name("Login"));

        List<WebElement> inputs = formulario.findElements(By.tagName("input"));

        if (inputs.size() >= 2) {
            escribirConRetraso(inputs.get(0), email);
            escribirConRetraso(inputs.get(1), password);

            formulario.findElement(By.cssSelector("button[type='submit']")).click();

            System.out.println("Formulario completado con éxito.");
        } else {
            System.out.println("No se encontraron suficientes campos de entrada en el formulario.");
        }
    }
    

    //Funcion hecha porque sino pone que utilizamos bots
    private void escribirConRetraso(WebElement input, String texto) {
        for (char c : texto.toCharArray()) {
            input.sendKeys(String.valueOf(c));
            try {
                Thread.sleep((long) (Math.random() * 200 + 100));  // Retraso aleatorio entre 100ms y 400ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    //AplicarDescuento.feature, ComprobarTiendaMásCercana
    //BusquedaFiltrada.feature, BusquedaOrdenada.feature
    //BusquedaProductosExistentes.feature, BusquedaProductosInexistentes.feature
    //Sirve para presionar el botón de submit directamente.
    @And("el usuario presiona submit")
    public void usuarioPresionaSubmit() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Esperar hasta que el botón de submit sea clicable
        WebElement botonSubmit = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        // Hacer clic en el botón de submit
        botonSubmit.click();
    }

    //AñadirCarrito.feature, AplicarDescuento.feature, 
    //EliminarCarrito.feature, SesionGuardaCarrito.feature 
    //Cuando añades un producto al carrito sale un popup con un botón, está funcion sirve tanto para darle a ese popup, como si no aparece ir
    //desde la barra navegadora
    @And("el usuario haga clic en el carrito")
    public void usuarioClicACarrito() {
    	try {
    		//Probamos primero el boton de ir al carrito del popup
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement irAlCarrito = wait.until(ExpectedConditions.visibilityOfElementLocated(
            	    By.xpath("//button[contains(@class, 'hnf-btn') and .//span[contains(@class, 'hnf-btn__label') and text()='Ir al carrito']]")));
            irAlCarrito.click();
            System.out.println("Botón 'Ir al carrito' clickeado correctamente.");
        } catch (Exception e) {
        	//Probamos el boton de ir al carrito normal del navbar
        	driver.findElement(By.cssSelector("span.js-shopping-cart-icon")).click();
        	System.out.println("Botón 'Ir al carrito' clickeado correctamente.");
        }
    }
    
    //EliminarCarrito.feature, sirve para eliminar del carrito usando el simbolo de restar o el cubo de la basura.
    @When("^el usuario elimine producto del carrito (.*)")
    public void eliminarProductoCarrito(int opcion) {
    	switch(opcion) {
    	case 1:
    		//Lo quitamos con el simbolo de restar
    		driver.findElement(By.cssSelector("button.cart-ingka-quantity-stepper__decrease")).click();
    		break;
    	case 2:
    		//Se elimina totalmente el producto con el simbolo del cubo de la basura
    		driver.findElement(By.xpath("//button[contains(@aria-label, 'Eliminar producto')]")).click();
    		break;
    	}
    	
    }
    
    //CambiarIdioma.feature, ComprobarTiendaMasCercana.feature
    //Sirve para encontrar o el botón o el enlace al que pertenece un texto.
    @When("^el usuario hace clic en el texto (.*)$")
    public void hacerClickEnTexto(String textoBoton) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Buscar el span que contiene el texto
        WebElement span = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), '" + textoBoton + "')]")));
        WebElement elemento;
        try {
            // Intentar encontrar el ancestro botón más cercano
            elemento = span.findElement(By.xpath("./ancestor::button"));
        } catch (Exception e) {
        	// Intentar encontrar el ancestro enlace más cercano
        	elemento = span.findElement(By.xpath("./ancestor::a"));
        }
        elemento.click();
        Thread.sleep(2000);
    }

    //AñadirProductoFavoritos.feature, AplicarDescuento.feature, encontrar elemento clicable según su clase.
    @When("^el usuario hace clic en el boton con clase (.*)$")
    public void hacerClickEnBotonPorClase(String claseBoton) throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("." + claseBoton)));
        boton.click();
        Thread.sleep(2000);
    }
    
    //ComprobarTiendaMasCercana.feature, sirve para darle clic a su barra de busqueda de ubicacion.
    @And("el usuario hace clic en la barra de ubicación")
    public void buscarPorUbicacion() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement campoBusqueda = wait.until(ExpectedConditions.elementToBeClickable(By.id("hnf-store-search")));
        campoBusqueda.click();
    }
    
 	// Aunque las funciones buscarPorUbicacion() y clicBarraBusqueda() podrían combinarse en una función genérica hacerClicPorId(String id),
 	// hemos decidido priorizar la legibilidad sobre la generalización. 
    
    
    
    
    
	
// -----------------------------------------------------------------------------
// ------------------- [Comprobaciones para los tests] -------------------------
// -----------------------------------------------------------------------------
    
    //ComprobarTiendaMasCercana.feature, 
    //Obtenemos los elementos de la lista de ubicaciones donde aparezca la distancia, limpiamos el texto y comprobamos que esten en orden ascendente.
    @Then("comprobar por distancia")
    public void verificarDistanciaOrden() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> elementos = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".hnf-choice__list-item")));
        
        List<Double> distanciasOriginales = new ArrayList<>();
        
        for (WebElement elemento : elementos) {
            List<WebElement> distanciaSpan = elemento.findElements(By.className("hnf-choice-item__caption"));
            String textoDistancia = distanciaSpan.get(1).getText();
            double distancia = Double.parseDouble(textoDistancia.replaceAll("[^\\d,]", "").replace(',', '.'));
            distanciasOriginales.add(distancia);
        }

        List<Double> distanciasOrdenadas = new ArrayList<>(distanciasOriginales);
        Collections.sort(distanciasOrdenadas);
        Assert.assertEquals(distanciasOrdenadas, distanciasOriginales);
        System.out.println("Las distancias están ordenadas correctamente de menor a mayor.");
    }

    //AñadirFavoritos.feature, BusquedaFiltrada.feature, BusquedaPorCategorias.feature, 
    //BusquedaProductosExistentes.feature, CambiarIdioma.feature, IniciarSesion.feature
    //Sirve para comprobar que en algún punto de la página aparece lo que queremos
    @Then("^comprobar que aparece (.*)$")
    public void comprobarQueAparece(String texto) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> elementos = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//*[contains(text(), '" + texto + "')]"), 0));

        boolean textoEncontrado = elementos.size() > 0;

        if (textoEncontrado) {
            System.out.println("El texto '" + texto + "' fue encontrado en la página.");
        } else {
            System.out.println("El texto '" + texto + "' no fue encontrado en la página.");
        }

        Assert.assertTrue(textoEncontrado);
    }

    
    //DetallesProducto.feature, sirve para comprobar que esten todos las especificaciones del producto:
    //Precio, descripción, imagen y reseñas
    @Then("deben aparecer los detalles del producto")
    public void comprobarDetallesDeProducto() throws InterruptedException {
    	Thread.sleep(2000);
        // Verificar que el nombre del producto esté visible
        WebElement productNameElement = driver.findElement(By.cssSelector(".pip-header-section__description-text"));
        Assert.assertNotNull(productNameElement);

        // Verificar que el precio esté visible
        WebElement priceElement = driver.findElement(By.cssSelector(".pip-price-module__primary-currency-price"));
        Assert.assertNotNull(priceElement);

        // Verificar que la imagen del producto esté visible
        WebElement imageElement = driver.findElement(By.cssSelector(".pip-image"));
        Assert.assertNotNull(imageElement);

        // Verificar que las reseñas estén visibles
        WebElement ratingElement = driver.findElement(By.cssSelector(".pip-rating"));
        Assert.assertNotNull(ratingElement);

        // Verificar que la descripción del producto esté visible
        WebElement descriptionElement = driver.findElement(By.cssSelector(".pip-product-summary__description"));
        Assert.assertNotNull(descriptionElement);
    }
    
    //BusquedaFiltrada.feature, BuscarProductosExistentes.feature
    //Sirve para asegurarnos de que se filtran y encuentran los productos necesarios
	@And("^deben aparecer al menos (.*) numero de productos")
	public void comprobarAlMenosValorProductos(int valor) {
		System.out.println("comprobarAlMenosValorProductos: ");

	    String text = driver.findElement(By.cssSelector("a.plp-btn .plp-btn__label")).getText();
	    System.out.print("1: " + text);

	    int numberOfProducts = Integer.parseInt(text.split(" ")[0]);
	    System.out.print(" 2: " + numberOfProducts + "\n");
	    
	    Assert.assertTrue(numberOfProducts >= valor);
	}
	
	//BusquedaAutocompletada.feature, sirve para asegurarnos que se autocompleta correctamente
	@Then("^deben autocompletarse resultados que empiezan por (.*)")
	public void debenAutocompletarseResultadosQueEmpiezanPor(String busqueda) throws InterruptedException {
		System.out.println("Comprobando que los resultados empiezan por " + busqueda + " : ");
		
		Thread.sleep(1000);
		
		List<WebElement> listItems = driver.findElements(By.className("universal-item__text"));
		boolean encontrado = false;
		for (WebElement element : listItems) {
			if (element.getText().toLowerCase().startsWith(busqueda.toLowerCase())) {
				System.out.print("Elemento encontrado: " + element.getText());
				encontrado = true;
				break;
			}
		}
		Assert.assertEquals(encontrado, true);
	}
	
	//BusquedaOrdenada.feature, se comprueba que los precios esten ordenados ascendiente o descendientemente, segun la opción
	@Then("^deben estar ordenados (.*)")
	public void comprobarResultadosOrdenados(int opcion) {
		System.out.println("comprobarResultadosOrdenados: ");
	    List<WebElement> products = driver.findElements(By.className("plp-price-module__price"));
	    List<Float> prices = new ArrayList<>();  // Inicializa la lista de precios
	    for (WebElement product : products) {
	    	String precioTexto = product.getText().replaceAll("[^\\d\\, ]","").split(" ")[0].replace(',', '.');
	    	float precio = Float.parseFloat(precioTexto);
	    	
	    	System.out.println("Precio: " + precio);

	        prices.add(precio);
	    }

	    // Copia la lista de precios para comparar con la lista ordenada
	    List<Float> pricesOrdenados = new ArrayList<>(prices);

	    // Ordena según la opción
	    switch (opcion) {
	        case 1: // Ordenar de menor a mayor
	            Collections.sort(pricesOrdenados);
	            break;
	        case 2: // Ordenar de mayor a menor
	            Collections.sort(pricesOrdenados, Collections.reverseOrder());
	            break;
	        default:
	            throw new IllegalArgumentException("Opción no válida");
	    }

	    // Compara la lista original con la lista ordenada
	    Assert.assertEquals(pricesOrdenados, prices);
	}
	
	//BusquedaProductosInexistentes.feauture, sirve para asegurarnos de que no aparecen productos.
	@Then("^no deben aparecer resultados")
	public void comprobarNoDebenAparecerResultados() {
		System.out.println("comprobarNoDebenAparecerResultados: ");
		wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
		String text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.plp-text--heading-l.search-summary__heading--zero"))).getText();
        System.out.print("1: " + text + "\n");
        
        Assert.assertTrue(text.contains("No hay resultados para"));
	}
	
	//AñadirCarrito.feature, AplicarDescuento.feature, EliminarCarrito.feature, SesionGuardaCarrito.feature  
	//Sirve para asegurarnos que en el total del carrito con iva aparezca el valor seleccionado.
	@Then("^debe aparecer en el carrito (.*)$")
	public void comprobarCarrito(float valor) throws InterruptedException {
	    Thread.sleep(1000); // Espera explícita (aunque es preferible usar WebDriverWait en lugar de Thread.sleep)
	    wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
	    System.out.print("comprobarCarrito: ");
	    String priceText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.notranslate"))).getText();
	    String cleanedPrice = priceText.replaceAll("[^\\d,]", "").replace(',', '.');
	    float total = Float.parseFloat(cleanedPrice);
	    Assert.assertEquals(total, valor); 
	    System.out.print("Correcto! \n");
	}
}
