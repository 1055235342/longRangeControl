package com.wpixel.mode;

public class Image implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer length;
	private byte[] bytes;

	
	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
}
