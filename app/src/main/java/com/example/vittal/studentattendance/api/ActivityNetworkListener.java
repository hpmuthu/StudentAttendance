package com.example.vittal.studentattendance.api;

public interface ActivityNetworkListener {

	public <T> void onResponse(T response, String tagName);
	public void onError(Object error, String tagName);
	
}
