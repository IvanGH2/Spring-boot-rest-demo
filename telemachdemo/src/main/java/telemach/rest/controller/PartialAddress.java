package telemach.rest.controller;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartialAddress {
	
	private String streetNo;
	
	private String street;
	
	private String city;
	
	private String post;
	
	private Integer postNo;
	
	@Override
	public String toString() {
		
		return "Address: Street No="+streetNo+" , Street="+street+" , "
				+ "City="+city+" , Post="+post+" , Post No="+postNo + "";
	}

}
