package steps;

import Base.BaseUtil;
import io.cucumber.java.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

/**
 * Hook class handles global setup and teardown for the test scenarios.
 * It initializes the WebDriver and ensures the browser is closed after execution.
 */
public class Hook extends BaseUtil {

    private final BaseUtil base;

    // Constructor to initialize BaseUtil
    public Hook(BaseUtil base) {
        this.base = base;
    }

    /**
     * Runs before each scenario to set up the WebDriver and prepare the test environment.
     * 
     * @param scenario The current test scenario.
     */
    @Before
    public void InitializeTest(Scenario scenario) {
        // Log the scenario being executed
        System.out.println("Initializing Scenario: " + scenario.getName());

        // Attach scenario name to the test report (if supported in your framework)
        base.scenarioDef = base.features.createNode(scenario.getName());

        // Set up WebDriverManager for Chrome
        WebDriverManager.chromedriver().setup();

        // Configure ChromeOptions for headless execution
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless"); // Runs the browser in headless mode
        chromeOptions.addArguments("--disable-gpu"); // Disables GPU acceleration (useful for CI pipelines)
        chromeOptions.addArguments("--window-size=1920,1080"); // Ensures proper resolution for headless mode

        // Initialize WebDriver with ChromeDriver
        base.Driver = new ChromeDriver(chromeOptions);

        // Set global implicit wait
        base.Driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Log WebDriver initialization
        System.out.println("WebDriver initialized successfully");
    }

    /**
     * Runs after each scenario to perform cleanup actions such as closing the browser.
     * 
     * @param scenario The current test scenario.
     */
    @After
    public void TearDownTest(Scenario scenario) {
        // If the scenario fails, log it
        if (scenario.isFailed()) {
            System.out.println("Scenario failed: " + scenario.getName());
            // You can add screenshot capture logic here
        }

        // Log and close the browser
        System.out.println("Closing the browser");
        if (base.Driver != null) {
            base.Driver.quit();
        }
    }

    /**
     * Executes before every step in the scenario.
     * 
     * @param scenario The current test scenario.
     */
    @BeforeStep
    public void BeforeEveryStep(Scenario scenario) {
        System.out.println("Before step: " + scenario.getId());
    }

    /**
     * Executes after every step in the scenario.
     * 
     * @param scenario The current test scenario.
     */
    @AfterStep
    public void AfterEveryStep(Scenario scenario) {
        System.out.println("After step: " + scenario.getId());
    }
}
