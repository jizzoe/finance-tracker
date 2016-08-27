package com.swingtech.app.financetracker.service.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.swingtech.app.financetracker.service.error.FinanceTrackerExcepton;
import com.swingtech.app.financetracker.service.service.FinanceTrackerService;

@SpringBootApplication
@ComponentScan({"com.swingtech.app.financetracker.service"})
public class Application implements CommandLineRunner {
	@Autowired
	private FinanceTrackerService financeTrackerService;
	
	public static void main(String[] args) throws FinanceTrackerExcepton {
		// TODO Auto-generated method stub
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		System.out.println("\n\n\n**********************************************\n   Startup Complete! \n**********************************************");
	}
	
	@Override
	public void run(String... strings) throws Exception {
		System.out.println("\n\n\nStart - Initializing Service");
		financeTrackerService.initFinanceTracker();
		System.out.println("End - Initializing Service");
	}
	
	@Bean
	public Module jodaModule() {
	  return new JodaModule();
	}
	
}
