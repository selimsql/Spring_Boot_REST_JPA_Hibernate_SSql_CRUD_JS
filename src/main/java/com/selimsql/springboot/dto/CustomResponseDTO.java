package com.selimsql.springboot.dto;

public class CustomResponseDTO {

	private Long id;
	private String message;
	private String error;

	public CustomResponseDTO(Long id, String message) {
		this.id = id;
		this.message = message;
		this.error = null;
	}

	public CustomResponseDTO() {
		this(null, null);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CustomResponseDTO; message=" + message + ", id=" + id;
	}
}
