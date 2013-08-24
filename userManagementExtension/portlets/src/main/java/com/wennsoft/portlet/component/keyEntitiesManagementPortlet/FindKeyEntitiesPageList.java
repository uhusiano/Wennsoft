package com.wennsoft.portlet.component.keyEntitiesManagementPortlet;

import java.util.ArrayList;
import java.util.List;

import org.exoplatform.commons.utils.ListAccess;
import org.exoplatform.commons.utils.ListAccessImpl;
import org.exoplatform.commons.utils.PageListAccess;

public class FindKeyEntitiesPageList extends PageListAccess<KeyEntity, String> 
{
	private static final long serialVersionUID = 1L;

    public FindKeyEntitiesPageList(String state, int pageSize) 
    {
	    super(state, pageSize);
    }

	@Override
	protected ListAccess<KeyEntity> create(String state) throws Exception 
	{	
        List<KeyEntity> listKeyEntities = new ArrayList<KeyEntity>();
//        if (Utils.getAttributeUserProfile(state, "keyEntities") == null)
//        {	
//            if (state.equals("root")) 
//            {
//                Utils.setAttributeUserProfile(state, "keyEntities", "Customer/Customer Number:12-1031;Customer Name:Handy Gloves Inc&Customer/Customer Number:12-1032;Customer Name:eXoplatform&Customer/Customer Number:12-1033;Customer Name:Capgemini&Vendor/Vendor Number:SAV-1200;Vendor Branch:MKE");
//            }
//            else
//            {
//        	    Utils.setAttributeUserProfile(state, "keyEntities", "Customer/Customer Number:12-1031;Customer Name:Handy Gloves Inc&Customer/Customer Number:12-1032;Customer Name:eXoplatform&Vendor/Vendor Number:SAV-1200;Vendor Branch:MKE&Vendor/Vendor Number:SAV-1220;Vendor Branch:MKB");	
//            }
//        }
        String keyEntities = Utils.getAttributeUserProfile(state, "keyEntities");
        KeyEntity keyEntity;
        if (keyEntities != null)
        {
        	String[] splittedKeyEntities = keyEntities.split("&");
        	for (String splittedKeyEntity : splittedKeyEntities)
        	{
        	    if (!splittedKeyEntity.equals(""))
        		{
        		    keyEntity = new KeyEntity(splittedKeyEntity.split("/")[0], splittedKeyEntity.split("/")[1]);
        		    listKeyEntities.add(keyEntity);
        		}
        	}
        }
		return new ListAccessImpl<KeyEntity>(KeyEntity.class, listKeyEntities);
	}
}