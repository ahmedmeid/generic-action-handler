package com.xpress.rd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Properties;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import com.xpress.rd.beans.Action;
import com.xpress.rd.beans.ActionDef;
import com.xpress.rd.beans.Parameter;
import com.xpress.rd.exceptions.ActionHandlingException;
import com.xpress.rd.utils.ActionHandlerUtils;
import com.xpress.rd.utils.DbmsOutput;

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
public class GenericActionHandler 
{
	Properties genericActionProps = null;
	HashMap<String,ActionDef> actionDefinitions = null;
	
	public static void main(String[] args) 
	{
           GenericActionHandler handler = new GenericActionHandler();

        try {
        	    handler.initialize();
        	    handler.readActionDefinitions();
				handler.start();
			} catch (ValidityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParsingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ActionHandlingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	
	/**
	 * 
	 * @throws ValidityException
	 * @throws ParsingException
	 * @throws IOException
	 * @throws ParseException 
	 */
	protected void start() throws ValidityException, ParsingException, IOException, ActionHandlingException, ParseException
	{
       File inputDir = new File(genericActionProps.getProperty("dir.input"));
       
       //define FilenameFilter for XML files
       FilenameFilter filter = new FilenameFilter() 
       {
    	    public boolean accept(File dir, String name) 
    	    {
    	        return name.endsWith(".xml");
    	    }
    	};
       
    	//loop on files in the input directory
       for(File file: inputDir.listFiles(filter))
       {
    	 //parse input xml file
    	 Builder builder = new Builder();  
    	 Document doc = builder.build(file);
    	 Element root = doc.getRootElement();
    	 ActionDef actionDef = actionDefinitions.get(root.getLocalName());
    	 if(actionDef == null)
    	 {
    		 System.out.println("action is not supported: "+root.getLocalName());
    		 throw new ActionHandlingException("action is not supported: "+root.getLocalName());
    	 }
    	 Action action = new Action();
    	 action.setDefinition(actionDef);
    	 Element parameters = root.getFirstChildElement("parameters");
    	 int noOfParameters = parameters.getAttributeCount();
    
    	 HashMap<String, Object> parametersMap = new HashMap<String, Object>();
    	 for(int i=0;i<noOfParameters;i++)
    	 {
    	   Attribute parameterAttr = parameters.getAttribute(i);
    	   String parameterName = parameterAttr.getLocalName();
    	   String parameterValue = parameterAttr.getValue();
    	   Parameter param = action.getDefinition().getParameters().get(parameterName);
    	   parametersMap.put(parameterName, ActionHandlerUtils.getParameterValue(param,parameterValue));
    	 }
    	 action.setParameters(parametersMap);
    	 handleAction(action);
       }
    	   
    	   
    	   
	}

	/**
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void initialize() throws FileNotFoundException, IOException
    {
       genericActionProps = new Properties();
       FileInputStream confFileInputStream = new FileInputStream(new File("conf/genericActionHandler.properties"));
       genericActionProps.load(confFileInputStream);
       confFileInputStream.close();
       
	}

	/**
	 * 
	 * @throws ValidityException
	 * @throws ParsingException
	 * @throws IOException
	 */
	private void readActionDefinitions() throws ValidityException, ParsingException, IOException 
	{
	   actionDefinitions = new HashMap<String, ActionDef>();
       Builder builder = new Builder();
       Document doc = builder.build(new File("conf/action-definition.xml"));
       Element root = doc.getRootElement();
       Elements actions = root.getChildElements();
       int noOfActions = actions.size();
       for(int i=0;i<noOfActions;i++)
       {
    	 String action_name;
    	 ActionDef actionDef;
    	 Element action = actions.get(i);
    	 actionDef = new ActionDef();
    	 actionDef.setId(Long.parseLong(action.getAttributeValue("id")));
    	 action_name = action.getAttributeValue("name");
    	 actionDef.setName(action_name);
    	 actionDef.setActionType(action.getAttributeValue("type"));
    	
    	 Element procedure = action.getFirstChildElement("action-procedure");
    	 actionDef.setPackageName(procedure.getAttributeValue("package"));
    	 actionDef.setFunctionName(procedure.getAttributeValue("name"));
    	 
    	 HashMap<String,Parameter> actionDefParams = new HashMap<String, Parameter>();
    	 Element parametersElement = procedure.getFirstChildElement("parameters");
    	 Elements parameters = parametersElement.getChildElements();
    	 int noOfParameters = parameters.size();
    	 for(int j=0;j<noOfParameters;j++)
    	 {
    		String parameterName = null;
    		Element parameter = parameters.get(j);
    		Parameter param = new Parameter();
    		param.setSeq(Integer.parseInt(parameter.getAttributeValue("seq")));
    		parameterName = parameter.getAttributeValue("name");
    		param.setName(parameterName);
    		param.setDataType(parameter.getAttributeValue("data-type"));
    		actionDefParams.put(parameterName, param);
    	 }
    	 actionDef.setParameters(actionDefParams);
    	 actionDefinitions.put(action_name, actionDef);
       }  
       
	}
/*
	private void validateInput(String fileName) throws Exception 
	{

	}

	private Object parseInput(String inputFileName) 
	{
		return null;
	}
*/
	/**
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	public Connection getConnection() {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection connection = DriverManager.getConnection(
					genericActionProps.getProperty("database.url"),
					genericActionProps.getProperty("database.username"),
					genericActionProps.getProperty("database.password"));

			return connection;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	/**
	 * 
	 * @param action
	 * @param actionDef
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public int handleAction(Action action) {
		try {
			Connection connection = getConnection();

			StringBuffer preparedCallStringBuffer = new StringBuffer();
			preparedCallStringBuffer.append("{? = call ");
			preparedCallStringBuffer.append(action.getDefinition().getPackageName());
			preparedCallStringBuffer.append(".");
			preparedCallStringBuffer.append(action.getDefinition().getFunctionName());
			preparedCallStringBuffer.append("(");
			int noOfParams = action.getDefinition().getParameters().size();
			for (int i = 0; i < noOfParams; i++) {
				if (i != 0) {
					preparedCallStringBuffer.append(",");
				}
				preparedCallStringBuffer.append("?");
			}
			preparedCallStringBuffer.append(")}");
			CallableStatement cs = connection.prepareCall(preparedCallStringBuffer.toString());

			cs.registerOutParameter(1, Types.NUMERIC);
            
			for (Parameter param: action.getDefinition().getParameters().values()) 
			{	
				ActionHandlerUtils.setXXX(cs, param, action.getParameters().get(param.getName()));
			}

			DbmsOutput dbmsOutput = null;
			try {
				dbmsOutput = new DbmsOutput(connection);
				dbmsOutput.enable(1000000);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("executing: " + preparedCallStringBuffer);
			cs.execute();
			int returnValue = cs.getInt(1);
		    if (dbmsOutput != null) 
		        {
					System.out.println("DBMS_OUTPUT: "+ dbmsOutput.show().trim());
				}
			return returnValue;
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}

}