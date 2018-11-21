package com.xpress.rd.exceptions;

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
public class ActionHandlingException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2292519567174693466L;
	
	private String reason;
	public ActionHandlingException(String reason)
	{
	  this.reason = reason;	
	}

	public String getReason()
	{
		return reason;
	}
}
