package com.projektinnovatif.aosaaa.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the permissioninfo database table.
 * 
 */
@Entity
@Table(name="permissioninfo")
@NamedQuery(name="Permissioninfo.findAll", query="SELECT p FROM Permissioninfo p")
public class Permissioninfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Long roleid;

	private String description;

	private byte isactive;

	private byte isobjectspecific;

	private String title;

	public Permissioninfo() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte getIsactive() {
		return this.isactive;
	}

	public void setIsactive(byte isactive) {
		this.isactive = isactive;
	}

	public byte getIsobjectspecific() {
		return this.isobjectspecific;
	}

	public void setIsobjectspecific(byte isobjectspecific) {
		this.isobjectspecific = isobjectspecific;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}