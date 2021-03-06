package com.wennsoft.portlet.component.keyEntitiesManagementPortlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.container.component.RequestLifeCycle;
import org.exoplatform.services.organization.OrganizationService;
import org.exoplatform.services.organization.UserProfile;
import org.exoplatform.services.organization.UserProfileHandler;
import org.exoplatform.webui.application.portlet.PortletRequestContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils 
{
    private static List<Object> convertJsonArray(JSONArray jsonArray) throws Throwable
    {
        List<Object> jsonArrayList = new ArrayList<Object>();
        for (int jsonArrayIndex = 0; jsonArrayIndex < jsonArray.length(); jsonArrayIndex++)
        {
            Object jsonArrayObject = jsonArray.get(jsonArrayIndex);
            if (jsonArrayObject.getClass().equals(JSONArray.class))
            {
                jsonArrayList.add(convertJsonArray((JSONArray) jsonArrayObject));
            }
            else if (jsonArrayObject.getClass().equals(JSONObject.class))
            {
                jsonArrayList.add(convertJsonObject((JSONObject) jsonArrayObject));
            }
            else
            {
                jsonArrayList.add(jsonArrayObject);
            }
        }
        return jsonArrayList;
    }
    
    private static Map<String, Object> convertJsonObject(JSONObject jsonObject) throws Throwable
    {
        Map<String, Object> jsonObjectMap = new HashMap<String, Object>();
        if (jsonObject.equals(JSONObject.NULL))
        {
            jsonObjectMap = null;
        }
        else
        {
            Iterator<?> jsonIterator = jsonObject.keys();
            while (jsonIterator.hasNext())
            {
                String key = (String)jsonIterator.next();
                Object value = jsonObject.get(key);
                if (value.equals(JSONObject.NULL))
                {
                    jsonObjectMap.put(key, null);
                }
                else if (value.getClass().equals(JSONArray.class))
                {
                    jsonObjectMap.put(key, convertJsonArray((JSONArray)value));
                }
                else if (value.getClass().equals(JSONObject.class))
                {
                    jsonObjectMap.put(key, convertJsonObject((JSONObject)value));
                }
                else
                {
                    jsonObjectMap.put(key, value);
                }
            }
        }
        return jsonObjectMap;
    }
	
    public static Map<String, Object> convertJsonString(String json) throws Throwable
    {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if (json != null && !json.isEmpty())
        {
            JSONArray jsonArray = null;
            JSONObject jsonObject = null;
            try
            {
                jsonArray = new JSONArray(json);
            }
            catch (JSONException jsonArrayException)
            {
                try
                {
                    jsonObject = new JSONObject(json);
                }
                catch (JSONException jsonObjectException)
                {
                    throw jsonObjectException;
                }
            }
            if (jsonArray != null)
            {
                jsonMap.put("Data", convertJsonArray(jsonArray));
            }
            else if (jsonObject != null)
            {
                jsonMap = convertJsonObject(jsonObject);
            }
        }
        return jsonMap;
    }
	
    public static String getAttributeUserProfile(String userId, String attribute) throws Exception
    {
        OrganizationService organizationService = (OrganizationService) PortalContainer.getInstance().getComponentInstanceOfType(OrganizationService.class);
        UserProfileHandler userProfileHandler = organizationService.getUserProfileHandler();
        UserProfile userProfile = userProfileHandler.findUserProfileByName(userId);
        return userProfile.getAttribute(attribute);
    }
    
    public static String getEntitiesList(String webServiceUri, String controller, String parameters) throws Throwable
    {
        String data = null;
        HttpClient client = new DefaultHttpClient();
        try
        {
            StringBuffer webServiceUriPathBuffer = new StringBuffer();
            if (controller != null && !controller.isEmpty())
            {
                webServiceUriPathBuffer.append(String.format("/%s", controller));

            }
            if (parameters != null)
            {
                webServiceUriPathBuffer.append(String.format("%s", parameters));
            }
            UriBuilder builder = UriBuilder.fromUri(new URI(webServiceUri + webServiceUriPathBuffer.toString()));
            HttpGet request = new HttpGet(builder.build());
            request.addHeader("User", PortletRequestContext.getCurrentInstance().getRemoteUser());
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == 200)
            {   
                BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) 
                {
                    result.append(line);
                }
                data = result.toString();
            }
            else
            {
                JSONObject errorMessageJsonObject = new JSONObject();
                errorMessageJsonObject.put("Error", response.getStatusLine().getStatusCode());
                throw new Exception(errorMessageJsonObject.toString());
            }
        }
        finally
        {
            if (client != null)
            {
                client.getConnectionManager().shutdown();
            }
        }
        return data;
    }

    public static void setAttributeUserProfile(String userId, String attribute, String attributeValue) throws Exception
    {
    	RequestLifeCycle.begin(PortalContainer.getInstance());
    	OrganizationService organizationService = (OrganizationService) PortalContainer.getInstance().getComponentInstanceOfType(OrganizationService.class);
        UserProfileHandler userProfileHandler = organizationService.getUserProfileHandler();
        UserProfile userProfile = userProfileHandler.createUserProfileInstance(userId);
        userProfile.setAttribute(attribute, attributeValue);
        userProfileHandler.saveUserProfile(userProfile, false);
        RequestLifeCycle.end();
    }
}