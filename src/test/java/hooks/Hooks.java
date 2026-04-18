package hooks;

import base.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks extends BaseTest{

	@Before
    public void beforeScenario() {
        setup();
        System.out.println("----- Test Started -----");
    }

    @After
    public void afterScenario() {
        System.out.println("----- Test Finished -----");
    }
	
}
