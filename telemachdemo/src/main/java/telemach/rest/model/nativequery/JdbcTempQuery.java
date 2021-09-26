package telemach.rest.model.nativequery;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import telemach.rest.controller.PartialAddress;
import telemach.rest.model.Address;

@Repository
public class JdbcTempQuery {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Address> getAddressesByFields(PartialAddress address){
		
		final NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		final MapSqlParameterSource params = new MapSqlParameterSource();		
		
		
		int streetNoNull = address.getStreetNo()  != null && address.getStreetNo().length() > 0 ? 0 : 1;
		int streetNull   = address.getStreet()    != null && address.getStreet().length() > 0   ? 0 : 1;
		int cityNull	 = address.getCity()  	  != null && address.getCity().length() > 0     ? 0 : 1;
		int postNull	 = address.getPost() 	  != null && address.getPost() .length() > 0     ? 0 : 1;
		int postNoNull	 = address.getPostNo() 	  != null ? 0 : 1;
		
		params.addValue("streetNo", address.getStreetNo(), Types.VARCHAR);
		params.addValue("street", address.getStreet(), Types.VARCHAR);
		params.addValue("city", address.getCity(), Types.VARCHAR);
		params.addValue("post", address.getPost() , Types.VARCHAR);
		params.addValue("postNo", address.getPostNo(), Types.INTEGER);
		params.addValue("streetNoNull", streetNoNull, Types.INTEGER);
		params.addValue("streetNull", streetNull, Types.INTEGER);
		params.addValue("cityNull", cityNull, Types.INTEGER);
		params.addValue("postNull", postNull, Types.INTEGER);
		params.addValue("postNoNull", postNoNull, Types.INTEGER);
		
		
		String query = "select a.* from address a  where (street_no = :streetNo or 1=:streetNoNull) "
					 + " and (street=:street or 1=:streetNull) and (city=:city or 1=:cityNull) and (post=:post or 1=:postNull) "
					 + " and (post_no=:postNo or 1=:postNoNull)";

		 return namedJdbcTemplate.query(query, params, BeanPropertyRowMapper.newInstance(Address.class));
	}

}
