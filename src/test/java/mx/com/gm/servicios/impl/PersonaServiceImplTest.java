package mx.com.gm.servicios.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import junit.framework.Assert;
import mx.com.gm.jdbc.dao.PersonaDao;
import mx.com.gm.jdbc.domain.Persona;
import mx.com.gm.servicios.PersonaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:datasource-test.xml","classpath:spring-context.xml" })
public class PersonaServiceImplTest {
	
	private static Log log = LogFactory.getLog(PersonaServiceImplTest.class);
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private PersonaDao personaDao;

	@Test
	@Transactional
	public void deberiaMostrarPersonas() {
		System.out.println();
		try {
			log.info("Inicio test deberiaMostrarPersonas");
			
			int contadorPersonas = this.desplegarPersonas();
			
			Assert.assertEquals(contadorPersonas, personaDao.contadorPersonas());
			
			log.info("fin test deberiaMostrarPersonas");	
		} catch (Exception e) {
			log.error("Error servicio", e);
		}
	}
	
	@Test
	@Transactional
	public void operacionesTest() {
		System.out.println();
		try {
			log.info("Inicio test operacionesTest");
			
			Persona persona = new Persona();
			persona.setNombre("Rodrigo");
			persona.setApePaterno("Diaz");
			persona.setApeMaterno("Zuniga");
			persona.setEmail("rediazz83@gmail.com");
			
			personaService.agregarPersona(persona);
			
			Assert.assertEquals(4, personaDao.contadorPersonas());
			
			persona = personaService.recuperarPersona(new Persona(1));
			persona.setNombre("Administrador");
			
			personaService.modificarPersona(persona);
			
			this.desplegarPersonas();
					
			log.info("fin test operacionesTest");	
		} catch (Exception e) {
			log.error("Error servicio", e);
		}
	}
	
	private int desplegarPersonas() {
		List<Persona> personas = personaService.listarPersonas();
		
		int contadorPersonas = 0;
		for(Persona persona: personas) {
			log.info("Persona: ".concat(persona.toString()));
			contadorPersonas++;
		}
		
		return contadorPersonas;
	}

}
