package com.erp.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class DownloadResponse {
	  public byte[] downloadFile;
      public String fileName;
     
	public DownloadResponse(byte[] downloadFile, String fileName) {
		super();
		this.downloadFile = downloadFile;
		this.fileName = fileName;
	}   
}
