package telemach.rest.message;

public class AppMessages {
	
	public static final String REQ_ADDRESS = "Address with the passed id does not exist!";
	
	public static final String REQ_SERVICE = "Service with the passed id does not exist!";
	
	public static final String ADDRESS_DELETED = "Address with corresponding services has been deleted!";
	
	public static final String ADDRESS_UPDATED = "Address has been updated!";
	
	public static final String ADDRESS_INSERTED = "Address has been inserted!";
	
	public static final String ADDRESS_EXISTS = "Address with that id already exists!";
	
	public static final String SERVICE_EXISTS = "Service with that address id and service name already exists!";
	
	public static final String SERVICE_DELETED = "Services has been deleted!";
	
	public static final String SERVICE_UPDATED = "Services has been updated!";
	
	public static final String SERVICE_INSERTED = "Services has been inserted!";
	
	public static final String SERVICE_INVALID_NAME = "Service name specified is not allowed!";
	
	public static final String REQ_SERVICE_UPDATE = "Updating service has been requested";
	
	public static final String REQ_ADDRESS_UPDATE = "Updating address has been requested";
	
	public static final String REQ_SERVICE_INSERT = "Inserting service has been requested";
	
	public static final String REQ_SERVICE_DELETE = "Deleting service has been requested";
	
	public static final String REQ_ADDRESS_INSERT = "Inserting address has been requested";
	
	public static final String REQ_ADDRESS_DELETE = "Deleting address has been requested";
	
	public static final String REQ_ADDRESS_SELECTBYID = "Selecting  address(es) by id(s) has been requested";
	
	public static final String REQ_ADDRESS_SELECTBYFIELD = "Selecting addresses by field (other than id) has been requested";
	
	public static final String REQ_ADDRESS_FIELD = "Required Address object field {} has not been set";
	
	public static final String REQ_SERVICE_FIELD = "Required Service object field {} has not been set";
	
	public static final String APP_INFO = "This is a simple Spring Boot application demonstrating REST services!";
	

}
