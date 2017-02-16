package mx.com.gm.jdbc.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import mx.com.gm.jdbc.dao.PersonaDao;
import mx.com.gm.jdbc.domain.Persona;
import mx.com.gm.jdbc.rowmappers.PersonaRowMapper;

@Repository
public class PersonaDaoImpl implements PersonaDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;

	private static final String SQL_INSERT_PERSONA = "INSERT INTO PERSONA (nombre, ape_paterno, ape_materno, email) "
			.concat("values (:nombre,:apePaterno, :apeMaterno, :email)");

	private static final String SQL_UPDATE_PERSONA = "UPDATE PERSONA set nombre = :nombre, ape_paterno = :apePaterno, "
			.concat("ape_materno = :apeMaterno, email = :email WHERE id_persona = :idPersona");

	private static final String SQL_DELETE_PERSONA = "DELETE FROM PERSONA WHERE id_persona = :idPersona";
	private static final String SQL_SELECT_PERSONA = "SELECT id_persona, nombre, ape_paterno, ape_materno, email FROM PERSONA";
	private static final String SQL_SELECT_PERSONA_BY_ID = SQL_SELECT_PERSONA + " WHERE id_persona = ?";
	private static final String SQL_SELECT_PERSONA_BY_EMAIL = SQL_SELECT_PERSONA + " WHERE email = :email";
	private static final String SQL_COUNT_PERSONA = "SELECT count(*) FROM PERSONA";
	private static final String SQL_COUNT_BY_NAME = SQL_COUNT_PERSONA + " WHERE nombre = :nombre";

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public void createPersona(Persona persona) {
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(persona);
		this.namedParameterJdbcTemplate.update(SQL_INSERT_PERSONA, namedParameters);
	}

	@Override
	public void updatePersona(Persona persona) {
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(persona);
		this.namedParameterJdbcTemplate.update(SQL_UPDATE_PERSONA, namedParameters);
	}

	@Override
	public void deletePersona(Persona persona) {
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(persona);
		this.namedParameterJdbcTemplate.update(SQL_DELETE_PERSONA, namedParameters);
	}

	@Override
	public Persona findPersonaById(long idPersona) {
		Persona persona = null;
		try {
			persona = this.jdbcTemplate.queryForObject(SQL_SELECT_PERSONA_BY_ID, new PersonaRowMapper(), idPersona);
		} catch (EmptyResultDataAccessException e) {
			persona = null;
		}

		return persona;
	}

	@Override
	public List<Persona> findAllPersonas() {
		RowMapper<Persona> personaRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(Persona.class);
		return this.jdbcTemplate.query(SQL_SELECT_PERSONA, personaRowMapper);
	}

	@Override
	public int contadorPersonasPorNombre(Persona persona) {
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(persona);
		return this.namedParameterJdbcTemplate.queryForInt(SQL_COUNT_BY_NAME, namedParameters);
	}

	@Override
	public int contadorPersonas() {
		return this.jdbcTemplate.queryForInt(SQL_COUNT_PERSONA);
	}

	@Override
	public Persona getPersonaByEmail(Persona persona) {
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(persona);
		return this.namedParameterJdbcTemplate.queryForObject(SQL_SELECT_PERSONA_BY_EMAIL, namedParameters,
				new PersonaRowMapper());
	}

}
