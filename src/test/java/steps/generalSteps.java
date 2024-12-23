package steps;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
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
	        System.out.println("El div de cookies desapareció");
	    } catch (Exception e) {
	        System.out.println("No se encontró el botón de cookies o ya se aceptaron.");
	    }
	}
	
	@Given("el usuario esta en la pagina principal")
	public void elUsuarioEstaEnLaPaginaPrincipal() {
		System.setProperty("webdriver.gecko.driver", "Drivers/geckodriver.exe");
		driver = new FirefoxDriver();
		driver.navigate().to("https://www.ikea.com/es/es/");
		acceptCookies();
	}
	
	@When("el usuario haga clic en la barra de busqueda")
	public void clicBarraBusqueda() {
		driver.findElement(By.id("ikea-search-input")).click();
		System.out.println("Barra de busqueda clicada");
	}
	
	@And("^el usuario escriba (.*)")
	public void escribaAlgo(String algo) {
		driver.findElement(By.id("ikea-search-input")).sendKeys(algo);
		System.out.println("Se ha escrito "+ algo);
	}
	
	@And("el usuario haga clic en el icono de buscar")
	public void usuarioClicIconoBuscar() {
		driver.findElement(By.id("search-box__searchbutton")).click();
		System.out.println("Icono de busqueda clicado");
	}
	
	
	//Comprobaciones para los tests
	
	@Then("^se ha buscado (.*)")
	public void comprobarSeHaBuscadoAlgo(String algo) {
		System.out.println("comprobarSeHaBuscadoAlgo: ");
		wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Espera hasta 10 segundos
	    String text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1[aria-label*='" + algo + "']"))).getText();
		System.out.print("1: " + text + "\n");
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
	
	@Then("^no deben aparecer resultados")
	public void comprobarNoDebenAparecerResultados() {
		System.out.println("comprobarNoDebenAparecerResultados: ");
		wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Espera hasta 10 segundos
		String text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.plp-text--heading-l.search-summary__heading--zero"))).getText();
        System.out.print("1: " + text + "\n");
        
        Assert.assertTrue(text.contains("No hay resultados para"));
	}
}
