package com.emc.d2.d2fs.examples;

import java.util.List;
import com.emc.d2.d2fs.examples.common.LoginInfo;
import com.emc.d2fs.models.attribute.Attribute;
import com.emc.d2fs.models.context.Context;
import com.emc.d2fs.schemas.models.ModelPort;
import com.emc.d2fs.schemas.models.ModelPortService;
import com.emc.d2fs.services.property_service.SavePropertiesRequest;
import com.emc.d2fs.services.property_service.SavePropertiesResponse;

/**
 *    SavePropertiesExample -- Demonstrate use of saveProperties() service to set object attributes.
 *  Test 2 3 4 5 6fgf
 *  
 *
 */
public class SavePropertiesExample
{

	static ModelPortService	service		= new ModelPortService();

	public static void main(String[] args) throws Exception
	{
		saveProperties();
	}

	public static void saveProperties() throws Exception
	{
		// Modify Setup Here!! 
		String	objectId	= "0934bf158014e912";    // Specify the target object id to modify properties.
		LoginInfo loginInfo = new LoginInfo();       // See LoginInfo.java for Login/Context settings.


		System.out.println("Retrieving the port for service: " + service);
		ModelPort port = service.getModelPortSoap11();

		// Get context and login
		Context context = loginInfo.getContextAndLogin(port);

		// Create attributes to be set via saveProperties
		
		// "title" Attribute
		Attribute title = new Attribute();
		title.setName("title");
		title.setType(2);   // DfType: see AttributeUtils in JavaDoc
		title.setValue("My new title2");

		// "object_name" Attribute
		Attribute object_name = new Attribute();
		object_name.setName("object_name");
		object_name.setType(2);
		object_name.setValue("New Object Name");
		
		// "list" Attribute -- IMPORTANT: This attribute specifies the names of attributes
		//     to be saved.  "list" of attribute names separated by DfUtilEx.SEPARATOR_VALUE
	    final String SEPARATOR_VALUE = "\u00AC";   // Literal "¬";
		Attribute list = new Attribute();
		list.setName("list");
	    list.setValue("title"+SEPARATOR_VALUE+"object_name");
		list.setType(2);
		
		// Create SavePropertiesRequest
		SavePropertiesRequest saveReq = new SavePropertiesRequest();
		saveReq.setContext(context);
		saveReq.setId(objectId);
		List<Attribute> attributes = saveReq.getAttributes(); 
		attributes.add(title);
		attributes.add(object_name);
		attributes.add(list);

		System.out.println("\n\nSaving properties...");
		SavePropertiesResponse saveResp = port.saveProperties(saveReq);
		System.out.println("Properties save complete.");
		
		String responseXml= saveResp.getXmlContent();
		System.out.println("Response: "+ responseXml);
		
	}
}
