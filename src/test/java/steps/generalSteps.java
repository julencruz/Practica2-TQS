package steps;

import static org.testng.Assert.assertTrue;

import java.text.Normalizer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	        System.out.println("No se encontró el botón de cookies. Error: " + e.getMessage());
	    }
	}
	
	@And("muere")
	public void muere() {
		driver.quit();
	}
	
	@And("^el usuario selecciona filtro (.*)")
	public void elUsuarioSeleccionaFiltro(String filtro) throws InterruptedException {
		System.out.println("Se selecciona filtro " + filtro);
		Thread.sleep(2000);
		WebElement filtroBoton = driver.findElement(By.xpath("//span[text()='" + filtro + "']/ancestor::button"));
        filtroBoton.click();
	}
	
	@And("^el usuario selecciona opción (.*)")
	public void seleccionarOpcionFiltro(int opcion) {
	    // Esperar a que el popup sea visible
		System.out.println("Se seleccionará la " + (opcion+1) +"ª opción del popup");
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
	@Given("el usuario esta en la pagina principal")
	public void elUsuarioEstaEnLaPaginaPrincipal() {
		System.setProperty("webdriver.gecko.driver", "Drivers/geckodriver.exe");
		driver = new FirefoxDriver();
		driver.navigate().to("https://www.ikea.com/es/es/");
		acceptCookies();
	}
	
	@When("el usuario haga clic en la barra de busqueda")
	public void clicBarraBusqueda() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.id("ikea-search-input")).click();
		System.out.println("Barra de busqueda clicada");
	}
	
	@And("^el usuario escriba (.*)")
	public void escribaAlgo(String algo) {
	    WebElement searchBox = driver.findElement(By.id("ikea-search-input"));
	    
	    // Escribir en la barra de búsqueda
	    searchBox.sendKeys(algo);
	    System.out.println("Se ha escrito " + algo);
	    
	    // Esperar a que el texto deje de cambiar
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.textToBePresentInElementValue(searchBox, algo)
	    );
	}
	
	@And("el usuario haga clic en el icono de buscar")
	public void usuarioClicIconoBuscar() {
		driver.findElement(By.id("search-box__searchbutton")).click();
		System.out.println("Icono de busqueda clicado");
	}
	
	@When("^el usuario haga clic en el enlace (.*)$")
	public void usuarioClicEnlace(String nombre) {
	    clicAEnlace(nombre);
	}

    public void clicAEnlace(String nombre) {
    	System.out.println("Clicando enlace");
        // Normalizar el nombre: quitar espacios y reemplazar por guiones, todo en minúsculas
    	String nombreNormalizado = Normalizer.normalize(nombre.trim().toLowerCase().replace(" ", "-"), Normalizer.Form.NFD);
    	nombreNormalizado = nombreNormalizado.replaceAll("[^\\p{ASCII}]", "");

        // Buscar el enlace cuyo href comience con la palabra clave normalizada
        WebElement enlace = driver.findElement(By.xpath("//a[starts-with(@href, 'https://www.ikea.com/es/es/cat/" + nombreNormalizado + "')]"));

        // Hacer clic en el enlace
        enlace.click();
    }
	
	
	//Comprobaciones para los tests
	
    @Then("^se ha buscado (.*)")
    public void comprobarSeHaBuscadoAlgo(String algo) {
        System.out.println("comprobarSeHaBuscadoAlgo: ");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Intentar encontrar el h1 con el atributo aria-label
        WebElement element = null;
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1[aria-label*='" + algo + "']")));
        } catch (Exception e) {
            // Si no se encuentra el h1 con aria-label, buscar el h1 con la clase plp-page-title__title
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.plp-page-title__title")));
        }
        
        // Obtener el texto y comprobar si contiene lo que se esperaba
        String text = element.getText();
        System.out.print("Texto encontrado: " + text + "\n");
        Assert.assertTrue(text.contains(algo));
    }
    
	@And("^deben aparecer al menos (.*) numero de productos")
	public void comprobarAlMenosValorProductos(int valor) {
		System.out.println("comprobarAlMenosValorProductos: ");

	    String text = driver.findElement(By.cssSelector("a.plp-btn .plp-btn__label")).getText();
	    System.out.print("1: " + text);

	    int numberOfProducts = Integer.parseInt(text.split(" ")[0]);
	    System.out.print(" 2: " + numberOfProducts + "\n");
	    
	    Assert.assertTrue(numberOfProducts >= valor);
	}
	
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
	
	@Then("^no deben aparecer resultados")
	public void comprobarNoDebenAparecerResultados() {
		System.out.println("comprobarNoDebenAparecerResultados: ");
		wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
		String text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.plp-text--heading-l.search-summary__heading--zero"))).getText();
        System.out.print("1: " + text + "\n");
        
        Assert.assertTrue(text.contains("No hay resultados para"));
	}
}
