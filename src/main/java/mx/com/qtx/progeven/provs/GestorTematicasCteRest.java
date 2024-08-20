package mx.com.qtx.progeven.provs;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import mx.com.qtx.progeven.core.IGestorTematicas;

@Service
public class GestorTematicasCteRest implements IGestorTematicas{
	private static Logger bitacora = LoggerFactory.getLogger(GestorTematicasCteRest.class); 
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String URI_TXP = "http://localhost:8081/tematicas/{numPersona}";
	
	public Map<String,Integer> getMapTematicas(int numPersona){
		Map<String,Integer> mapTematicas = new TreeMap<>();
		try {
			TematicaDTO[] arrTematicas = restTemplate.getForObject(URI_TXP, TematicaDTO[].class, numPersona);
			Arrays.asList(arrTematicas)
			      .forEach( t -> mapTematicas.put(t.getNombre(),t.getIdTematica()));
		}
		catch(RestClientResponseException rcrex) {
			bitacora.error("getMapTematicas("+numPersona+") ha fallado: "+ rcrex.getResponseBodyAsString());
			mapTematicas.put("No disponibles", -1);
		}
		catch(Exception ex) {
			bitacora.error("getMapTematicas("+numPersona+") ha fallado: "+ ex.getMessage());
			mapTematicas.put("No disponibles", -1);
		}
		return mapTematicas;
	}

}
