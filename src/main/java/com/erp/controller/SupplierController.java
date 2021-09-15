package com.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.service.SupplierService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
	@Autowired
	SupplierService supplierService;
	
	
}
