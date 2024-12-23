package steps;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class generalSteps {
	
	WebDriver driver;
	
	public void acceptCookies() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  
	    
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
	}
	
	@And("^el usuario escriba (.*)")
	public void escribaAlgo(String algo) {
		driver.findElement(By.id("ikea-search-input")).sendKeys(algo);
	}
	
	@And("el usuario haga clic en el icono de buscar")
	public void usuarioClicIconoBuscar() {
		driver.findElement(By.id("search-box__searchbutton")).click();
	}
	
	@Then("^deben aparecer al menos (.*) numero de productos")
	public void comprobarAlMenosValorProductos(int valor) {
		
	    WebElement productLabel = driver.findElement(By.xpath("//span[contains(text(), 'productos')]"));
	    String text = productLabel.getText();


	    String numberString = text.replaceAll("[^0-9]", ""); // Eliminar todo excepto los números
	    int numberOfProducts = Integer.parseInt(numberString); // Convertir a int

	    assertTrue(numberOfProducts >= valor);
	}
}
