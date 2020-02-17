package com.massango.background;

public class HomeSettings {
 private int refID;
 private String name;
 
public HomeSettings() {
	super();
}
public HomeSettings(int refID, String name) {
	super();
	this.refID = refID;
	this.name = name;
}
/**
 * @return the refID
 */
public int getRefID() {
	return refID;
}
/**
 * @param refID the refID to set
 */
public void setRefID(int refID) {
	this.refID = refID;
}
/**
 * @return the name
 */
public String getName() {
	return name;
}
/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}

}
