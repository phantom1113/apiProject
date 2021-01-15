package com.myclass.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	@NotNull
	private String password;

	@Column(name = "fullname")
	@NotNull
	private String fullName;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "role_id")
	private int roleId;

	@ManyToOne
	@JoinColumn(name = "role_id", insertable = false, updatable = false)
	@JsonIgnore
	private Role role;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Project> projects;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Task> tasks;

	public User(int id, String email, String password, String fullName, String avatar, int roleId) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.avatar = avatar;
		this.roleId = roleId;
	}

	public User(int id, String email, String password, String fullName, String avatar, int roleId,
			Role role) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.avatar = avatar;
		this.roleId = roleId;
		this.role = role;
	}
}
