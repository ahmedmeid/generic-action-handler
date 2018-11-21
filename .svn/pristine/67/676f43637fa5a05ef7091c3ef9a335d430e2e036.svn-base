package com.xpress.rd.utils;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

import com.xpress.rd.beans.Parameter;

/**
 * 
 * @author Ahmed Eid
 *
 */
public class ActionHandlerUtils 
{

	public static String DATE_FORMAT="dd/mm/yyyy";
	
	/**
	 * 
	 * @param cs
	 * @param param
	 * @param value
	 * @throws SQLException
	 */
	public static void setXXX(CallableStatement cs, Parameter param, Object value) throws SQLException
	{
	   if(param.getDataType().equals("String"))
	   {
		   cs.setString(param.getSeq(),(String) value);
	   }
	   
	   if(param.getDataType().equals("Int"))
	   {
		   cs.setInt(param.getSeq(), (Integer) value);
	   }
	   
	   if(param.getDataType().equals("Date"))
	   {
		   cs.setDate(param.getSeq(), (Date) value);
	   }
	   
	}
	
	/**
	 * 
	 * @param param
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	public static Object getParameterValue(Parameter param, String value) throws ParseException
	{
		String dataType = param.getDataType();
		if(dataType.equals("String"))
		{
			return value;
		}
		if(dataType.equals("Int"))
		{
			return Integer.parseInt(value);
		}
		if(dataType.equals("Date"))
		{
			return new Date(new SimpleDateFormat(DATE_FORMAT).parse(value).getTime());
		}
		return null;
	}
}
