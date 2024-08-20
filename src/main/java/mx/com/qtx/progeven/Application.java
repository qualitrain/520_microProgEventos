package mx.com.qtx.progeven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import mx.com.qtx.progeven.core.IPublicadorNotificaciones;
import mx.com.qtx.progeven.messageBroker.rabbitmq.EmisorRabbitmq;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	IPublicadorNotificaciones getPublicadorNotificaciones(Environment env){
		return EmisorRabbitmq.getEmisorNotificacion(
				env.getProperty("qtx.progEventosService.messageBroker.host", "localhost"),
				env.getProperty("qtx.progEventosService.messageBroker.exchangeEvtos", "exchangeDefault")
				);
	}

}
