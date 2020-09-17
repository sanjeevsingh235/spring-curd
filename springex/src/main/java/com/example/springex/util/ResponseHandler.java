package com.example.springex.util;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.springex.constant.ResponseLiteral;



public class ResponseHandler {

	private static final Logger LOGGER= LoggerFactory.getLogger(ResponseHandler.class);
	
	 private ResponseHandler(){

	    }

	 public static ResponseEntity<Object> generateResponse(HttpStatus httpStatus,boolean isSuccess,String message,Object data){
	        Map<String,Object> result=new HashMap<>(5);
	        result.put(ResponseLiteral.TIMESTAMP, Instant.now().toEpochMilli());
	        result.put(ResponseLiteral.STATUS, httpStatus.value());
	        result.put(ResponseLiteral.SUCCESS, isSuccess);
	        result.put(ResponseLiteral.MESSAGE, message);
	        if(Objects.isNull(data)) result.put(ResponseLiteral.DATA, Optional.empty());
	        else result.put(ResponseLiteral.DATA, data);
	        return new ResponseEntity<>(result, httpStatus);
	    }
	 
	
	
}
