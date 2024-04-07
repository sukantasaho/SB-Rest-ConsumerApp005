package com.main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class SbRestConsumerApp005Application 
{

	public static void main(String[] args) throws IOException 
	{
		SpringApplication.run(SbRestConsumerApp005Application.class, args);
		 RestTemplate temp = new RestTemplate();
		 //working with exchange method
			/*String url = "http://localhost:8080/api/wish";
			ResponseEntity<String> response = temp.exchange(url, HttpMethod.GET, null, String.class);
			System.out.println(response.getBody());*/
		 
			/* String url = "http://localhost:8080/api/wish/{name}";
			 ResponseEntity<String> response = temp.exchange(url, HttpMethod.GET, null, String.class, "Rajendra");
			 System.out.println(response.getBody());*/
		    
		 
		  // json data sending to rest api by post mode request
			/*String url = "http://localhost:8080/api/register";
			String jsonData = "{\"id\":101,\"docName\":\"Sukanta\",\"income\":8000.0}";
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(jsonData, header);
			
			  ResponseEntity<String> response =  temp.exchange(url, HttpMethod.POST, entity, String.class);
			  System.out.println(response.getBody());
			  System.out.println(response.getStatusCode());*/
		 
		 //Converting model json content to java object using MapperObject methods
		    String url = "http://localhost:8080/api/getDoctor";
		     ResponseEntity<String> response = temp.exchange(url, HttpMethod.GET, null, String.class);
		     System.out.println(response.getBody());
		     
		     //converting json data to java object
		      String jsonBody = response.getBody();
		      ObjectMapper mapper = new ObjectMapper();
		     Doctor d = mapper.readValue(jsonBody, Doctor.class); 
		        System.out.println(d.getId()+" "+d.getDocName()+" "+d.getIncome());
			    System.out.println("***************************************************************");
			    
		      //converting java object into json data
		     mapper.writeValue(new File("target/doctor.json"), d);
		     String d_json = mapper.writeValueAsString(d);
		     System.out.println(d_json);
		     
		     System.out.println("********************************************************************");
		     
		     //getting list of doctors
		     //converting list json data into java list collection object
		     String url1 = "http://localhost:8080/api/getAllDoctors";
		      ResponseEntity<String> res1 = temp.exchange(url1, HttpMethod.GET, null, String.class);
		      System.out.println(res1.getBody());
		      System.out.println(res1.getStatusCodeValue());
		      System.out.println("=========================================================================");
		      ObjectMapper m = new ObjectMapper();
		      List<Doctor> dList = m.readValue(res1.getBody(), new TypeReference<List<Doctor>>(){});
		      dList.forEach(e->System.out.println(e.getId()+"  "+e.getDocName()+"  "+e.getIncome()));
		      System.out.println("*************************************************");
		      
		      //converting list object to json
		      m.writeValue(new File("target/doctor.json"), dList);
		     String list_json = m.writeValueAsString(dList);
		     System.out.println(list_json);
		     
		 System.exit(0);
	}

}
