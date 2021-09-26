package telemach.rest.controller;

import telemach.rest.message.AppMessages;
import telemach.rest.model.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import telemach.rest.model.Address;
import telemach.rest.model.annotation.InsertCheckNotNull;
import telemach.rest.model.annotation.InsertCheckNotNullOrEmpty;
import telemach.rest.model.nativequery.JdbcTempQuery;

@Controller
@RequestMapping(path = "/rest")
public class RestController {
	
	static final Logger logger = Logger.getLogger(RestController.class);
	
	@Autowired
	private IAddressRepository iAddressRepository;
	
	@Autowired
	private IServiceRepository iServiceRepository;
	
	@Autowired
	private JdbcTempQuery jdbcTempQuery; 
	
	HashMap<String, Boolean> servicesMap = new HashMap<String, Boolean>();
	
	{
		servicesMap.put("Internet", true);
		servicesMap.put("Telefon", true);
		servicesMap.put("Televizija", true);
	}
	
	@PostMapping(path="/findAddressByFields", consumes={"text/xml", "application/xml", "application/json"}, produces={"application/json", "text/xml", "application/xml"})
	public @ResponseBody Response getAddressesByAnyField(@RequestBody PartialAddress partialAddress) {
		
		logger.info(AppMessages.REQ_ADDRESS_SELECTBYFIELD);
		Response resp = new Response();
		resp.setStatus(Response.Status.FAIL);
		try {
			List<Address> addresses = jdbcTempQuery.getAddressesByFields(partialAddress);
			
			for(int i=0;i<addresses.size();++i) {
				Address currAddress = addresses.get(i);
				List<Service> servicesByAddressId = iServiceRepository.findAllByAddressId(currAddress.getId());
				currAddress.setServices(servicesByAddressId);
			}
						
			resp.setResult(addresses);
			resp.setStatus(Response.Status.SUCCESS);
			
		}catch(Exception e) {
			resp.setResult("Exception: "+e.getMessage());
		}
		
		return resp;
	}
	
	@GetMapping(path="/findAddressById", produces={"application/json", "text/xml", "application/xml"})// json - response
	public @ResponseBody Response getAddressById(@RequestParam(name="addressId", required=true) Integer addressId) {
		
		logger.info(AppMessages.REQ_ADDRESS_SELECTBYID);
		
		Response resp = new Response();
		resp.setStatus(Response.Status.FAIL);
		Address address = iAddressRepository.findOneById(addressId);
		
		if(address != null) {
			resp.setResult(address);
			resp.setStatus(Response.Status.SUCCESS);
		}else {
			resp.setResult(AppMessages.REQ_ADDRESS);
		}
		
		return resp;
		
	}

	@PostMapping(path="/findAllAddressesByIds", consumes={"text/xml", "application/xml", "application/json"}, produces={"application/json", "text/xml", "application/xml"})
	public @ResponseBody Response getAddressesByIds(@RequestBody(required=true) List<Integer> addressIds) {
			
		logger.info(AppMessages.REQ_ADDRESS_SELECTBYID);
		Response resp = new Response();
		resp.setStatus(Response.Status.FAIL);
		
		List<Address> addresses = iAddressRepository.findAllByIdIn(addressIds);
		
		if(addresses != null) {
			resp.setResult(addresses);
			resp.setStatus(Response.Status.SUCCESS);
		}else
			resp.setResult(AppMessages.REQ_ADDRESS);
		
		
		return resp;
	
	}
	
	@PostMapping(path="/insertAddress",consumes={"text/xml", "application/xml", "application/json"}, produces={"application/json", "text/xml", "application/xml"})
	public @ResponseBody Response insertAddress(@RequestBody Address address) {
		
		logger.info(AppMessages.REQ_ADDRESS_INSERT);
		Response resp = new Response();
		resp.setStatus(Response.Status.FAIL);
		try {
			ValidationResult validationResult = this.validateObjectForInsert(address);
			if(!validationResult.validated) {			
				resp.setResult(replacePlaceholder(AppMessages.REQ_ADDRESS_FIELD, validationResult.fieldName));
				return resp;
			}
			
			Address existingAddress = iAddressRepository.findOneById(address.getId());
			if(existingAddress != null) {
				resp.setResult(AppMessages.ADDRESS_EXISTS);
				return resp;
			}
			iAddressRepository.save(address); 
			resp.setResult(AppMessages.ADDRESS_INSERTED);
			resp.setStatus(Response.Status.SUCCESS);
		}catch(Exception e) {
			resp.setResult("Exception: "+e.getMessage());			
		}
		return resp;
		
	}
	
	@PutMapping(path="/updateAddress",consumes={"text/xml", "application/xml", "application/json"}, produces={"application/json", "text/xml", "application/xml"})
	public @ResponseBody Response updateAddress(@RequestBody Address address) {
		
		logger.info(AppMessages.REQ_ADDRESS_UPDATE);
		Response resp = new Response();
		resp.setStatus(Response.Status.FAIL);
		
		Address existingAddress = iAddressRepository.findOneById(address.getId());
		if(existingAddress == null ) {
			resp.setResult(AppMessages.REQ_ADDRESS);
		
			return resp;
		} 
		try {
			Address addressUpdate = Address.builder()
				.id(address.getId())
				.streetNo(address.getStreetNo()  != null && address.getStreetNo().length()>0 ? address.getStreetNo() : existingAddress.getStreetNo())
				.street(address.getStreet() 	 != null && address.getStreet().length() > 0 ? address.getStreet() 	: existingAddress.getStreet())
				.city(address.getCity() 		 != null && address.getCity().length() > 0 ? address.getCity() 	: existingAddress.getCity())
				.post(address.getPost() 		 != null && address.getPost().length() > 0 ? address.getPost() 	: existingAddress.getPost())
				.postNo(address.getPostNo() 	 != null ? address.getPostNo() 	: existingAddress.getPostNo())
				.services(address.getServices()  != null && address.getServices().size() > 0 ?  address.getServices() : existingAddress.getServices())
				.build();
			iAddressRepository.save(addressUpdate);
			resp.setResult(AppMessages.ADDRESS_UPDATED);
			resp.setStatus(Response.Status.SUCCESS);
		}catch(Exception e) {
			resp.setResult("Exception: "+e.getMessage());
		}
		
		return resp;
		
	}
	@PutMapping(path="/updateService",consumes={"text/xml", "application/xml", "application/json"}, produces={"application/json", "text/xml", "application/xml"})
	public @ResponseBody Response updateService(@RequestBody Service service) {
		
		logger.info(AppMessages.REQ_SERVICE_UPDATE);
		Response resp = new Response();
		resp.setStatus(Response.Status.FAIL);
		
		Service existingService = iServiceRepository.findOneById(service.getId());
	
		if(existingService == null ) {
			resp.setResult(AppMessages.REQ_SERVICE);
			return resp;
		}
		
		Address address = iAddressRepository.findOneById(service.getAddressId());	
		
		if(address == null ) {
			resp.setResult(AppMessages.REQ_ADDRESS);
			return resp;
		} 
			//lets create a new Service instance based on the provided service object		
		
		try	{	
		Service updatedService = Service.builder()		
				.id(existingService.getId())
				.addressId(service.getAddressId())
				.service(service.getService() != null && service.getService().length() > 0 ? service.getService() : existingService.getService())
				.value(service.getValue() 	  != null ? service.getValue() 	 : existingService.getValue())
				.comment(service.getComment() != null && service.getComment().length() > 0 ? service.getComment() : existingService.getComment())
				.build();
		
		iServiceRepository.save(updatedService);//update
		resp.setResult(AppMessages.SERVICE_UPDATED);
		resp.setStatus(Response.Status.SUCCESS);
		}catch(Exception e) {
			resp.setResult("Exception: "+e.getMessage());
			
		}
		
		return resp;	
		
	}
	
	@PostMapping(path="/insertService", consumes={"text/xml", "application/xml", "application/json"}, produces={"application/json", "text/xml", "application/xml"})
	public @ResponseBody Response  insertService( @RequestBody Service service) {
		
		logger.info(AppMessages.REQ_SERVICE_INSERT);
			
		Response resp = new Response();
		resp.setStatus(Response.Status.FAIL);
		try {
			ValidationResult validationResult = this.validateObjectForInsert(service);
			if(!validationResult.validated) {			
				resp.setResult(replacePlaceholder(AppMessages.REQ_SERVICE_FIELD, validationResult.fieldName));
				return resp;
			}
			
			Service existingService = iServiceRepository.findOneByAddressIdAndService(service.getAddressId(), service.getService());
			if(existingService != null) {
				resp.setResult(AppMessages.SERVICE_EXISTS);
				return resp;
			}
			
			if(!servicesMap.containsKey(service.getService())) {
				resp.setResult(AppMessages.SERVICE_INVALID_NAME);
				return resp;
			}
				
			iServiceRepository.save(service);
			resp.setResult(AppMessages.SERVICE_INSERTED);
			resp.setStatus(Response.Status.SUCCESS);
		}catch(Exception e) {
			resp.setResult("Exception: "+e.getMessage());
			return resp;
		}
		return resp;	
	}

	//@Transactional(rollbackOn=Exception.class)
	@DeleteMapping(path="/deleteAddressById", produces={"application/json", "text/xml", "application/xml"})
	public @ResponseBody Response deleteAddressByid(@RequestParam(name="addressId", required=true)Integer addressId) {
		
		logger.info(AppMessages.REQ_ADDRESS_DELETE);
		Response resp = new Response();
		resp.setStatus(Response.Status.FAIL);
		try {
			Address existingAddress = iAddressRepository.findOneById(addressId);
			if(existingAddress == null) {
				resp.setResult(AppMessages.REQ_ADDRESS);
				return resp;		
			}
			//delete corresponding services first
			List<Service> services = iServiceRepository.findAllByAddressId(addressId);
			for(Service service : services)
				iServiceRepository.delete(service);
			existingAddress.setServices(null);
			iAddressRepository.deleteById(addressId);
			resp.setResult(AppMessages.ADDRESS_DELETED);
			resp.setStatus(Response.Status.SUCCESS);
		}catch(Exception e) {
			resp.setResult("Exception: "+e.getMessage());
			
		}
		
		return resp;
		
	}
	@DeleteMapping(path="/deleteServiceById", produces={"application/json", "text/xml", "application/xml"})
	public @ResponseBody Response deleteServiceByid(@RequestParam(name="serviceId", required=true)Integer serviceId) {
		
		logger.info(AppMessages.REQ_SERVICE_DELETE);
		Response resp = new Response();
		resp.setStatus(Response.Status.FAIL);
		try {
			Service existService = iServiceRepository.findOneById(serviceId);
			if(existService == null) {
				resp.setResult(AppMessages.REQ_SERVICE);
				return resp;		
			}
			iServiceRepository.deleteById(serviceId);
			resp.setResult(AppMessages.SERVICE_DELETED);
		}catch(Exception e) {
			resp.setResult("Exception: "+e.getMessage());
		}
		resp.setStatus(Response.Status.SUCCESS);
		return resp;
	}
	@GetMapping(path="/info")
	public @ResponseBody Response getAppInfo() {
		
		Response resp = new Response();
		resp.setStatus(Response.Status.SUCCESS);
		resp.setResult(AppMessages.APP_INFO);
		return resp;
	}
	private ValidationResult validateObjectForInsert(Object obj) throws IllegalArgumentException, IllegalAccessException {
		
		Field[] fields = null;
		if(obj instanceof Address) {

			fields = Address.class.getDeclaredFields();
		}else if(obj instanceof Service) {
	
			fields = Service.class.getDeclaredFields();
			
		}else {
			return new ValidationResult(false, null);//
		}
		
		for(Field field : fields) {
			field.setAccessible(true);
			if(field.isAnnotationPresent(InsertCheckNotNullOrEmpty.class)) {
				
				String fieldVal = (String)field.get(obj);
				
				if( fieldVal == null || fieldVal.length() == 0)
					return new ValidationResult(false, field.getName());
					
			}else if(field.isAnnotationPresent(InsertCheckNotNull.class)) {
				
				if( field.get(obj) == null )
					return  new ValidationResult(false, field.getName());
			}
		}
		
		
		return  new ValidationResult(true, null);
		
	}
	
	private class ValidationResult{
		
		boolean validated;
		
		String fieldName;
		
		ValidationResult(boolean validated, String fieldName){
			this.validated = validated;
			this.fieldName = fieldName;
		}
	}
	private String replacePlaceholder(String strToFormat, String valueToInsert) {
		
		int position = strToFormat.indexOf("{}");
		
		if(position == -1) {
			return strToFormat;
		}else {
			
			return strToFormat.substring(0, position) + valueToInsert + " " + strToFormat.substring(position + 3, strToFormat.length());
		}
		
	}
}
