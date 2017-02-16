package mx.com.gm.jdbc.dao;

import java.util.List;

import mx.com.gm.jdbc.domain.Persona;

public interface PersonaDao {
	
	void createPersona(Persona persona);
	
	void updatePersona(Persona persona);
	
	void deletePersona(Persona persona);
	
	Persona findPersonaById(long id);
	
	List<Persona> findAllPersonas();
	
	int contadorPersonasPorNombre(Persona persona);
	
	int contadorPersonas();
	
	Persona getPersonaByEmail(Persona persona);
	
}
