import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features={
		"src/test/java/features/BusquedaProductosExistentes.feature",
		"src/test/java/features/BusquedaProductosInexistentes.feature",
		"src/test/java/features/BusquedaAutocompletada.feature",
		"src/test/java/features/BusquedaPorCategorias.feature",
		"src/test/java/features/BusquedaFiltrada.feature",
		"src/test/java/features/BusquedaOrdenada.feature"
		}, glue="steps")
public class RunTests extends AbstractTestNGCucumberTests{

}
