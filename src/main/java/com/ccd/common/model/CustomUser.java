package com.ccd.common.model;

import java.util.List;

import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

	private String id;
	private String email;
	private String ucode;
	private String isDefaultPass;
	private List<String> grantedAuthoritiesIdList;
	private String uname;
	private String checkDept;
	private String deptName;
	private String role;

	public CustomUser(UserEntity userEntity) {
		super(userEntity.getUserCode(), userEntity.getPassword(), userEntity.getGrantedAuthoritiesList());
		this.id = userEntity.getUserId();
		this.email = userEntity.getEmailId();
		this.isDefaultPass = userEntity.getIsDefaultPass();
		this.grantedAuthoritiesIdList = userEntity.getGrantedAuthoritiesIdList();
		this.ucode = userEntity.getUserCode();
		this.uname = userEntity.getUserName();
		this.checkDept = userEntity.getCheckDept();
		this.deptName = userEntity.getDeptName();
		this.role = userEntity.getRole();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsDefaultPass() {
		return isDefaultPass;
	}

	public void setIsDefaultPass(String isDefaultPass) {
		this.isDefaultPass = isDefaultPass;
	}

	public List<String> getGrantedAuthoritiesIdList() {
		return grantedAuthoritiesIdList;
	}

	public void setGrantedAuthoritiesIdList(List<String> grantedAuthoritiesIdList) {
		this.grantedAuthoritiesIdList = grantedAuthoritiesIdList;
	}

	public String getUcode() {
		return ucode;
	}

	public void setUcode(String ucode) {
		this.ucode = ucode;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getCheckDept() {
		return checkDept;
	}

	public void setCheckDept(String checkDept) {
		this.checkDept = checkDept;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
