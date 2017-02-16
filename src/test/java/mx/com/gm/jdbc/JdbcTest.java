package mx.com.gm.jdbc;

import static org.junit.Assert.assertEquals;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:datasource-test.xml"})
public class JdbcTest {
	
	private static final int NRO_ESPERADO_PERSONAS = 3;

	private static Log log = LogFactory.getLog(JdbcTest.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	public void jdbcTest() {
		log.info("Inicio test");
		
		int nroPersonas = jdbcTemplate.queryForInt("select count(*) from persona");
		
		log.info("Numero de personas: " + nroPersonas);
		
		assertEquals("No es el numero esperado de personas.", NRO_ESPERADO_PERSONAS, nroPersonas);
		
		log.info("Fin test");
	}

}
