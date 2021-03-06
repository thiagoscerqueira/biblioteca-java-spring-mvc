package br.edu.fib.bibliotecajavamvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Locale;

@SpringBootApplication
public class BibliotecajavamvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecajavamvcApplication.class, args);
	}

	@Autowired
	public void exposeBeanToView (InternalResourceViewResolver internalResourceViewResolver) {
		internalResourceViewResolver.setExposedContextBeanNames("dadosComunsViews");
	}
}
