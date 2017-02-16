package mx.com.gm.jdbc.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.util.Permutations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import mx.com.gm.jdbc.dao.PersonaDao;
import mx.com.gm.jdbc.domain.Persona;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:datasource-test.xml", "classpath:spring-context.xml" })
public class PersonaDaoImplTest {

	private static final int COINCIDENCIAS_POR_NOMBRE = 2;

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
				log.info(persona);
				contadorPersonas++;
			}

			assertEquals("Cantidad de personas no coincide con el esperado.", contadorPersonas,
					personaDao.contadorPersonas());

			log.info("Fin test deberiaMostrarPersonas");
		} catch (Exception e) {
			log.error("Error JDBC", e);
		}
	}

	@Test
	public void contarPersonasPorNombreTest() {
		try {
			log.info("Inicio test contarPersonasPorNombreTest");

			Persona persona = new Persona();
			persona.setNombre("Juan");

			int nroPersonasEncontradas = personaDao.contadorPersonasPorNombre(persona);

			log.info("Numero de personas encontradas por nombre '"
					.concat(persona.getNombre().concat("': ").concat(String.valueOf(nroPersonasEncontradas))));

			assertEquals("Cantidad de personas no coincide con el esperado.", COINCIDENCIAS_POR_NOMBRE,
					nroPersonasEncontradas);

			log.info("Fin test contarPersonasPorNombreTest");
		} catch (Exception e) {
			log.error("Error JDBC", e);
		}
	}

	@Test
	public void deberiaEncontrarPersonaPorIdTest() {
		try {
			log.info("Inicio test deberiaEncontrarPersonaPorIdTest");

			int idPersona = 1;
			Persona persona = personaDao.findPersonaById(idPersona);

			assertNotNull("No se encontraron coincidencias", persona);

			log.info("Persona recuperada (id='" + idPersona + "'): " + persona);

			assertEquals("El nombre de la persona no coincide con el esperado.", "Admin", persona.getNombre());

			log.info("Fin test deberiaEncontrarPersonaPorIdTest");
		} catch (Exception e) {
			log.error("Error JDBC", e);
		}
	}

	@Test
	public void deberiaEncontrarPersonasPorMailTest() {
		try {
			log.info("Inicio test deberiaEncontrarPersonasPorMailTest");

			Persona persona = new Persona();
			persona.setEmail("jrodriguez@gmail.com");

			persona = personaDao.getPersonaByEmail(persona);

			assertNotNull("No se encontraron coincidencias", persona);

			log.info("Persona recuperada (email='" + persona.getEmail() + "'): " + persona);

			log.info("Fin test deberiaEncontrarPersonasPorMailTest");
		} catch (Exception e) {
			log.error("Error JDBC", e);
		}
	}

	@Test
	public void deberiaAgregarPersonaTest() {
		try {
			log.info("Inicio test deberiaAgregarPersonaTest");

			assertEquals("Cantidad de personas no coincide con el esperado.", 3, personaDao.contadorPersonas());

			Persona persona = new Persona();
			persona.setNombre("Rodrigo");
			persona.setApePaterno("Diaz");
			persona.setApeMaterno("Zuniga");
			persona.setEmail("rediazz83@gmail.com");
			personaDao.createPersona(persona);
			
			persona = personaDao.getPersonaByEmail(persona);

			log.info("Persona insertada: ".concat(persona.toString()));

			assertEquals("Cantidad de personas no coincide con el esperado.", 4, personaDao.contadorPersonas());

			log.info("Fin test deberiaAgregarPersonaTest");
		} catch (Exception e) {
			log.error("Error JDBC", e);
		}
	}
}
