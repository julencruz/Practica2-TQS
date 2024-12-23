import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features={"src/test/java/features/BusquedaProductos.feature"}, glue="steps")
public class RunTests extends AbstractTestNGCucumberTests{

}
