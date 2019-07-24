/*******************************************************************************
 * Copyright (c) 2019 Ace Resource Advisory Services Sdn. Bhd., Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Ace Resource Advisory Services Sdn. Bhd. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Ace Resource Advisory Services Sdn. Bhd.
 * 
 * Ace Resource Advisory Services Sdn. Bhd. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. Ace Resource Advisory Services Sdn. Bhd. SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 ******************************************************************************/
package com.rgei.kpi.dashboard.constant;

public class DatasourceConfigConstants {
	//no-arg constructor
	private DatasourceConfigConstants() {
	}
	public static final String BASE_PACKAGE="com.rgei.kpi.dashboard";
	public static final String ENTITIES_PACKAGE="com.rgei.kpi.dashboard.entities";
	public static final String CONFIG_PACKAGE="com.rgei.kpi.dashboard.config";
	public static final String HIBERNATE_DIALECT="hibernate.dialect";
	public static final String HIBERNATE_DIALECT_POSTGRESQL="org.hibernate.dialect.PostgreSQLDialect";
	public static final String SHOW_SQL="show-sql";
	public static final String HIBERNATE_FORMAT_SQL="hibernate.format_sql";
	public static final String HIBERNATE_CONTEXTUAL_CREATION="hibernate.jdbc.lob.non_contextual_creation";
	public static final String TRUE_VALUE="true";
	public static final String FALSE_VALUE="false";
	public static final String PROFILE_VALUE="active";
	public static final String SPRING_JPA_PROPERTIES="spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults";
	public static final String REPOSITORY_BASEPACKAGE="com.rgei.kpi.dashboard.repository";
	public static final String SPRING_DATASOURCE="spring.datasource";
	
}
