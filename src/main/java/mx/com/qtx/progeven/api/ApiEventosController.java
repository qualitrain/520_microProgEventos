package mx.com.qtx.progeven.api;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import mx.com.qtx.progeven.core.IGestorOrganizacion;
import mx.com.qtx.progeven.core.IGestorTematicas;
import mx.com.qtx.progeven.core.IPublicadorNotificaciones;

@RestController
public class ApiEventosController {
	private static Logger bitacora = LoggerFactory.getLogger(ApiEventosController.class); 
	
	@Autowired
	private IGestorTematicas gestorTematicas;
	@Autowired
	private IGestorOrganizacion gestorOrganizacion;
	@Autowired
	private IPublicadorNotificaciones notificador;
	
	@GetMapping(path = "/eventos/programacion", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getUiProgEventos() {
		bitacora.info("getUiProgEventos()");
		int numPersona = 2;
		
		Map<String, Integer> tematicas = gestorTematicas.getMapTematicas(numPersona);
		bitacora.info("Temáticas de persona num(" + numPersona
				+ "):" + tematicas.keySet());
		
		Map<String, String> areas = gestorOrganizacion.getMapAreas();

		// 	Coreografía: enviar eventoProgramado a MessageBroker	
		String objJsonEvento = getEventoJSon();
		this.notificador.emitirNotificacion(objJsonEvento);
		
		return tematicas.keySet().toString() + ", " + areas.keySet().toString() + ", " + objJsonEvento;
		
	}

	private String getEventoJSon() {
		JsonObjectBuilder evtoProgJsonBuilder = Json.createObjectBuilder();
		JsonObject evtoJson = evtoProgJsonBuilder
		            .add("numPersonaPropietario", 1201)
		            .add("nombre", "Ramiro López Angulo")
		            .add("objetivo", "Revisar avances proyecto Midas-2020")
		            .add("fechaProg", "2020-07-05 11:15 CST")
		            .add("duracionProgMin", 90)
		            .add("estado", 0)
		            .add("numEvento", 301)
		            .add("participantes",Json.createArrayBuilder()
		            		                 .add(Json.createObjectBuilder()
		            		                		  .add("numParticipante", 1)
		            		                          .add("numEmpleado", 501)
		            		                          .add("nombre","José Miguel Torres Aragón")
		            		                          .add("correoElectronico", "jmtorres@laempresa.com")
		            		                          .add("telefono", "55-11-34-11-22")
		            		                          .build())
		            		                 .add(Json.createObjectBuilder()
		            		                		  .add("numParticipante", 2)
		            		                          .add("numEmpleado", 3421)
		            		                          .add("nombre","Mariana Valdés Forlán")
		            		                          .add("correoElectronico", "mvaldes@laempresa.com")
		            		                          .add("telefono", "77-12-33-91-45")
		            		                          .build())
		            		                 .add(Json.createObjectBuilder()
		            		                		  .add("numParticipante", 3)
		            		                          .add("numEmpleado", 552)
		            		                          .add("nombre","Juan Manuel Tinoco Morales")
		            		                          .add("correoElectronico", "jmtinoco@laempresa.com")
		            		                          .add("telefono", "33-19-99-01-03")
		            		                          .build())
		                                    .build())
		         .build();
		return evtoJson.toString();
	}
}
