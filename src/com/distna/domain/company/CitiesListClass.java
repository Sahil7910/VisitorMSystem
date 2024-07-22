package com.distna.domain.company;

import org.springframework.util.AutoPopulatingList;

public class CitiesListClass {
	

		//private String className = null;
		private AutoPopulatingList<Cities> autoPopulatingListCities = null;

		
		public AutoPopulatingList<Cities> getAutoPopulatingListCities() {
			return autoPopulatingListCities;
		}

		public void setAutoPopulatingListCities(
				AutoPopulatingList<Cities> autoPopulatingListCities) {
			this.autoPopulatingListCities = autoPopulatingListCities;
		}

		
		
		
		

		/*public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}*/

		


}
