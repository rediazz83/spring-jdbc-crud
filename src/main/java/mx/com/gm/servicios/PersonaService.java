package mx.com.gm.servicios;

import java.util.List;

import mx.com.gm.jdbc.domain.Persona;

public interface PersonaService {
	
	List<Persona> listarPersonas();
	
	Persona recuperarPersona(Persona persona);
	
	void agregarPersona(Persona persona);
	
	void modificarPersona(Persona persona);

	void eliminarPersona(Persona persona);
	
}
