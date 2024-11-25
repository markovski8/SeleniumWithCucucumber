package steps;

import Base.BaseUtil;
import io.cucumber.java.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Hook extends BaseUtil {

    private BaseUtil base;

    public Hook(BaseUtil base) {
        this.base = base;
    }

    @Before
    public void InitializeTest(Scenario scenario) {
        // Set up the test
        base.scenarioDef = base.features.createNode(scenario.getName());

        // WebDriver setup
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless"); // Add more options if necessary
        base.Driver = new ChromeDriver(chromeOptions);

        // Set an implicit wait for the WebDriver
        base.Driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void TearDownTest(Scenario scenario) {
        if (scenario.isFailed()) {
            // Take screenshot logic (placeholder)
            System.out.println("Scenario failed: " + scenario.getName());
        }

        // Close the browser
        System.out.println("Closing the browser");
        if (base.Driver != null) {
            base.Driver.quit();
        }
    }

    @BeforeStep
    public void BeforeEveryStep(Scenario scenario) {
        System.out.println("Before every step: " + scenario.getId());
    }

    @AfterStep
    public void AfterEveryStep(Scenario scenario) {
        System.out.println("After every step: " + scenario.getId());
    }
}
