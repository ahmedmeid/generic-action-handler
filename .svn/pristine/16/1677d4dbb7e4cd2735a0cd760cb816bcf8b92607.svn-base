package com.xpress.rd.beans;

import java.util.HashMap;

///////////////////////////////////////////////////////////////////////////////
//Copyright (c) 2011 Xpress Integration
//
//The copyright in this work is vested in XI. The information contained in 
//this work (either in whole or in part) is confidential and must not be 
//modified, reproduced, disclosed or disseminated to others or used for 
//purposes other than that for which it is supplied, without the prior 
//written permission of XI.  If this work or any part hereof is furnished to 
//a third party by virtue of a contract with that party, use of this work by 
//such party shall be governed by the express contractual terms between XI, 
//which is party to that contract and the said party.
//
//The information in this document is subject to change without notice and 
//should not be construed as a commitment by XI. XI assumes no 
//responsibility for any errors that may appear in this document. With the 
//appearance of a new version of this document all older versions become 
//invalid. 
//
//All rights reserved.
///////////////////////////////////////////////////////////////////////////////
/**
 * 
 * @author Ahmed Eid
 *
 */
public class ActionDef 
{
	long id;
	String name;
	String schema;
	String actionType="PLSQL";
	String packageName;
	String functionName;
	HashMap<String,Parameter> parameters;

	public ActionDef()
	{
		
	}
	
	public ActionDef(String pPackageName,String pFunctionName)
	{
		packageName = pPackageName;
		functionName = pFunctionName;
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getPackageName() 
	{
		return packageName;
	}

	public void setPackageName(String packageName) 
	{
		this.packageName = packageName;
	}

	public String getFunctionName() 
	{
		return functionName;
	}

	public void setFunctionName(String functionName) 
	{
		this.functionName = functionName;
	}

	public HashMap<String, Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, Parameter> parameters) {
		this.parameters = parameters;
	}



}
