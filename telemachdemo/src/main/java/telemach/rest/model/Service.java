package telemach.rest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telemach.rest.model.annotation.InsertCheckNotNull;
import telemach.rest.model.annotation.InsertCheckNotNullOrEmpty;


@Entity
@Table(name = "service")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Service implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 133502610342008770L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@InsertCheckNotNull
	@Column(name = "address_id", nullable = false)
	private Integer addressId;
	
	@InsertCheckNotNullOrEmpty
	@Column(name = "service", nullable = false)
	private String service;
	
	@InsertCheckNotNull
	@Column(name = "value", nullable = false)
	private Boolean value;
	
	@Column(name = "comment", nullable = true)
	private String comment;
	
	@Override
	public String toString() {
		return "Service: id="+id+", addressId="+addressId+" , service="+service+" , value="+value+" ,comment="+comment +"";
	}
	
}
