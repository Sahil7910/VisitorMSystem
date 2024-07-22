package com.distna.domain.calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="calnumbers")
public class CalNumbers 
{
	private int n;
	
	@Id
	@Column(name="n")
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}
	
	
	
}
