package mx.com.gm.jdbc.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;
import mx.com.gm.jdbc.dao.PersonaDao;
import mx.com.gm.jdbc.domain.Persona;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:datasource-test.xml", "classpath:spring-context.xml" })
public class PersonaDaoImplTest {

	private static Log log = LogFactory.getLog(PersonaDaoImplTest.class);

	@Autowired
	public PersonaDao personaDao;

	@Test
	public void deberiaMostrarPersonas() {
		try {
			log.info("Inicio test deberiaMostrarPersonas");

			List<Persona> personas = personaDao.findAllPersonas();

			int contadorPersonas = 0;
			for (Persona persona : personas) {
				log.info(persona.toString());
				contadorPersonas++;
			}

			Assert.assertEquals("Cantidad de personas no coincide con el esperado.", contadorPersonas,
					personaDao.contadorPersonas());
		} catch (Exception e) {
			log.error("Error JDBC", e);
		}
	}

}
