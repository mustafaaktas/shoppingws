package com.shoppingws.model.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.shoppingws.annotations.MyDateFormat;
import com.shoppingws.annotations.SkipSerialisation;
import com.shoppingws.exceptions.HBRuntimeException;
import com.shoppingws.util.BeanValidator;
import com.shoppingws.util.HashGenerator;
import com.shoppingws.util.json.CustomJsonDateSerializer;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public abstract class AbstractBaseObject<T> implements Serializable {

	
	@JsonInclude(Include.NON_NULL)
	@JsonSerialize(using= CustomJsonDateSerializer.class)
	@MyDateFormat(value="dd/MM/yyyy HH:mm:ss")
    private Date    olusturmaTarihi;
	
	@JsonInclude(Include.NON_NULL)
	@JsonSerialize(using=CustomJsonDateSerializer.class)
	@MyDateFormat(value="dd/MM/yyyy HH:mm:ss")
    private Date    guncellemeTarihi;
	
	
	private static final long serialVersionUID = 1L;
	private String objectKey;

	public void generateHashValue(HashFields hashFields) {
		HashGenerator<T> hashGenerator = new HashGenerator<>();
		objectKey = hashGenerator.generateHash(this, hashFields.getHashFields());
	}

	public void generateHashValueAsData(HashFields hashFields) {
		HashGenerator<T> hashGenerator = new HashGenerator<>();
		objectKey = hashGenerator.generateHashForData(this, hashFields.getHashFields());
	}
	
	public void generateHashValueAsData() {
		HashGenerator<T> hashGenerator = new HashGenerator<>();
		objectKey = hashGenerator.generateHashForDataWithToString(this);
	}

	public String getObjectKey() {
		return objectKey;
	}

	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

	@Override
	public String toString() {
		String json ;
		GsonBuilder gsonBuilder= new GsonBuilder();
		gsonBuilder = gsonBuilder.addSerializationExclusionStrategy(new ExclusionStrategy() {

			  @Override public boolean shouldSkipField (FieldAttributes f) {
			    return f.getAnnotation(SkipSerialisation.class) != null;

			  }

			  @Override public boolean shouldSkipClass (Class<?> clazz) {

			    return false;
			  }
			});
		json = gsonBuilder.create().toJson(this);
		return json;
	}

	public void beanValidate() {
		beanValidate(null);
	}
	
	public void beanValidate(ValidationType type) {
		StringBuilder errors = new StringBuilder();
		Set<ConstraintViolation<AbstractBaseObject<T>>> violations = BeanValidator.Instance.getValidator().validate(this);
		for (ConstraintViolation<?> violation : violations) {
			errors.append(violation.getMessage() + System.lineSeparator());

		}
		if (!errors.toString().isEmpty()) {
			throw new HBRuntimeException(errors.toString());
		}
		
		if(type != null) {
			if(type.equals(ValidationType.ALL_VALIDATION)|| type.equals(ValidationType.INSERT_VALIDATION)) {
				//insertValidate();
			}
			
			if(type.equals(ValidationType.ALL_VALIDATION)||type.equals(ValidationType.UPDATE_VALIDATION)) {
				//updateValidate();
			}
		}
	}
	

	


	public Date getOlusturmaTarihi() {
		return olusturmaTarihi;
	}

	public void setOlusturmaTarihi(Date olusturmaTarihi) {
		this.olusturmaTarihi = olusturmaTarihi;
	}

	public Date getGuncellemeTarihi() {
		return guncellemeTarihi;
	}

	public void setGuncellemeTarihi(Date guncellemeTarihi) {
		this.guncellemeTarihi = guncellemeTarihi;
	}

	
}
