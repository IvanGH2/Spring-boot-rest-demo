package telemach.rest.types;

import lombok.Getter;

public enum EntityType {
	
	ADDRESS(0),
	SERVICE(1);
	
	@Getter
	private Integer value;
	
	private EntityType(Integer val) {
		value = val;
	}

}
