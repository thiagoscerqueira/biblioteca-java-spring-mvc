package br.edu.fib.bibliotecajavamvc.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DriverLoader {

    public WebDriver load() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("chromedriver.exe");
        File f = new File("Driver");
        if (!f.exists()) {
            f.mkdirs();
        }
        File chromeDriver = new File("Driver" + File.separator + "chromedriver.exe");
        if (!chromeDriver.exists()) {
            chromeDriver.createNewFile();
            org.apache.commons.io.FileUtils.copyURLToFile(resource, chromeDriver);
        }
        System.out.println(chromeDriver.getAbsolutePath());
        System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }
}
