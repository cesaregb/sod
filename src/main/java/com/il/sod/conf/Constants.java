package com.il.sod.conf;

public class Constants {
	private Constants() {
    }

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_FAST = "fast";
    public static final String SPRING_PROFILE_CLOUD = "cloud";
    public static final String SYSTEM_ACCOUNT = "system";
    
    
    public static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    public static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    public static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    public static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

	public static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "com.il.sod.model.entities";
}
