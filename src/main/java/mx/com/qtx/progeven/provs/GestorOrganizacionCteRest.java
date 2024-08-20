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

import mx.com.qtx.progeven.core.IGestorOrganizacion;

@Service
public class GestorOrganizacionCteRest implements IGestorOrganizacion{
	private static Logger bitacora = LoggerFactory.getLogger(GestorOrganizacionCteRest.class); 
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String URI_A = "http://localhost:8082/areas";
	
	public Map<String,String> getMapAreas(){
		Map<String,String> mapAreas = new TreeMap<>();
		try {
			AreaDTO[] arrAreas = restTemplate.getForObject(URI_A, AreaDTO[].class);
			Arrays.asList(arrAreas)
			      .forEach( a -> mapAreas.put(a.getNombre(),a.getCveArea()));
		}
		catch(RestClientResponseException rcrex) {
			bitacora.error("getMapAreas() ha fallado: "+ rcrex.getResponseBodyAsString());
			mapAreas.put("No disponibles", "ND");
		}
		catch(Exception ex) {
			bitacora.error("getMapAreas() ha fallado: "+ ex.getMessage());
			mapAreas.put("No disponibles", "ND");
		}
		return mapAreas;		
	}

}
