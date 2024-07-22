
package com.distna.domain.command;

import org.springframework.util.AutoPopulatingList;

/**
 * @author Eggsy - eggsy _at_ eggsylife.co.uk
 *
 */
public class ClassCommand {
	private String className = null;
	private AutoPopulatingList students = null;
	
	

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public AutoPopulatingList getStudents() {
		return students;
	}

	public void setStudents(AutoPopulatingList students) {
		this.students = students;
	}
}
