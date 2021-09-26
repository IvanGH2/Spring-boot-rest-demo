package telemach.rest.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telemach.rest.model.annotation.InsertCheckNotNull;
import telemach.rest.model.annotation.InsertCheckNotNullOrEmpty;

@Entity
@Table(name = "address")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4349349039402506940L;
	
	@InsertCheckNotNull
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@InsertCheckNotNullOrEmpty
	@Column(name = "street_no", nullable = false)
	private String streetNo;
	
	@InsertCheckNotNullOrEmpty
	@Column(name = "street", nullable = false)
	private String street;
	
	@InsertCheckNotNullOrEmpty
	@Column(name = "city", nullable = false)
	private String city;
	
	@InsertCheckNotNullOrEmpty
	@Column(name = "post", nullable = false)
	private String post;
	
	@InsertCheckNotNull
	@Column(name = "post_no", nullable = false)
	private Integer postNo;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private List<Service> services;
	
	@Override
	public String toString() {
		
		return "Address: id="+id+" , Street No="+streetNo+" , Street="+street+" , "
				+ "City="+city+" , Post="+post+" , Post No="+postNo + "";
	}
}
