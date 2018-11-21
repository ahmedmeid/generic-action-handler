package com.xpress.rd.test;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;

import com.xpress.rd.GenericActionHandler;
import com.xpress.rd.beans.Action;
import com.xpress.rd.beans.Parameter;

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
public class GenericActionHandlerTest extends TestCase
{
  Connection conn = null;
  public void testHandleAction()
  {
	  Parameter param1 = new Parameter(4,"Effective_Date","Date");
	  Parameter param2 = new Parameter(6,"Source_Rate_Plan_Version","Int");
	  Parameter param3 = new Parameter(3,"Rate_Plan_Shdesc","String");
	  Parameter param4 = new Parameter(2,"Rate_Plan_Desc","String");
	  Parameter param5 = new Parameter(5,"Source_Rate_Plan_Shdes","String");
	  
	  ArrayList<Parameter> params = new ArrayList<Parameter>();
	  params.add(param1);
	  params.add(param2);
	  params.add(param3);
	  params.add(param4);
	  params.add(param5);
	  
	  //ActionDef actionDef = new ActionDef("TTM_RATEPLAN", "COPYRATEPLAN");
	  
	 // actionDef.setParameters(params);

	  
	  HashMap<String, Object> parameterValues = new HashMap<String, Object>();
	  parameterValues.put("Effective_Date", Date.valueOf("2011-06-14"));
	  parameterValues.put("Source_Rate_Plan_Version", 4);
	  parameterValues.put("Rate_Plan_Shdesc", "TCRP");
	  parameterValues.put("Rate_Plan_Desc", "TTM Copy rateplan test");
	  parameterValues.put("Source_Rate_Plan_Shdes", "FESI");
	  
	  
	  Action action = new Action();
	  action.setParameters(parameterValues);
	  
	  GenericActionHandler genericTTMHandler = new GenericActionHandler();
	  
	  conn = genericTTMHandler.getConnection();
	  assertNotNull(conn);
	  
	 // int out = genericTTMHandler.handleAction(action, actionDef);
	  //System.out.println("out is:"+out);
  }
  
  @Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		conn.close();
	}
}
