package schedulingtasks;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Application {

	public static void main(String[] args) throws Exception {
		new AnnotationConfigApplicationContext(CleanOutUnactivatedAccounts.class);
	}
	
}
