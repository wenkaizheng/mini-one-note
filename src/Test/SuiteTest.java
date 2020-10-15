
package Test;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.*;
import org.junit.runner.RunWith;

/**
 * This is a test suite. If you cannot run it, please check if you have every
 * component of JUNIT 5 (including at least jupiter-api, jupiter-engine,
 * jupiter-params, platform-commons, platform-engine, platform-launcher,
 * platform-runner, platform-suite, and vintage-engine) and JUNIT4 (JUNIT5
 * does not have test suite yet, so it has to work with junit4. For more
 * information please check JUNIT5 document or google). In addition, JUNIT4
 * depends on hamcrest, so you also need to check if you have hamcrest in
 * your library.
 *
 * *** you can also comment this class and run other test class one by one.
 */
@RunWith(JUnitPlatform.class)
@SelectPackages("Test")
public class SuiteTest  {
}