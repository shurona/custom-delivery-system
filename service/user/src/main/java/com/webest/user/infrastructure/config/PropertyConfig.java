package com.webest.user.infrastructure.config;

import static com.webest.web.common.CommonStaticVariable.BASE_PACKAGE;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(BASE_PACKAGE + ".user")
public class PropertyConfig {

}

