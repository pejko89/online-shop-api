package com.alpey.shop.service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpey.shop.entity.Admin;
import com.alpey.shop.repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	AdminRepository adminRepository;

	public Admin create(Admin admin) {
		try {
			return adminRepository.save(admin);
		} catch (EntityExistsException e) {
			return null;
		}
	}

	public Admin update(String oldUsername, Admin updatedAdmin) {
		try {
			Admin storedAdmin = adminRepository.findByUsername(oldUsername);
			copyNotNullProperties(updatedAdmin, storedAdmin);
			return adminRepository.save(storedAdmin);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public List<Admin> findAll() {
		return (List<Admin>) adminRepository.findAll();
	}

	public Admin findByUsername(String username) {
		try {
			return adminRepository.findByUsername(username);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public Admin findByEmail(String email) {
		try {
			return adminRepository.findByEmail(email);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public String delete(String username) {
		try {
			Admin admin = adminRepository.findByUsername(username);
			adminRepository.delete(admin);
			return "Admin " + username + " deleted!";
		} catch (EntityNotFoundException e) {
			return "Admin " + username + " doesn't exist!";
		}
	}

	public String changeMasterPassword(String oldMasterPassword, String newMasterPassword) {
		return Admin.changeMasterPassword(oldMasterPassword, newMasterPassword);
	}

	public boolean loginValidation(String username, String password) {
		Admin admin = findByUsername(username);
		if (admin != null) {
			return admin.getPassword().equals(password);
		} else {
			return false;
		}
	}
	
	public Admin copyNotNullProperties(Admin src, Admin dest) {
		String username = src.getUsername();
		String password = src.getPassword();
		String email = src.getEmail();
		
		if(hasValue(username))
			dest.setUsername(username);
		if(hasValue(password))
			dest.setPassword(password);
		if(hasValue(email))
			dest.setEmail(email);
		
		return dest;
	}
	
	public boolean hasValue(String str) {
		if(str.equals("") || str.equals(null)) {
			return false;
		} else {
			return true;
		}
	}

}
