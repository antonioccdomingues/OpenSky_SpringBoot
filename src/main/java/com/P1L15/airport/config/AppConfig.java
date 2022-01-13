package com.P1L15.airport.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {
  private String openSkyUri;
  private String airportCode;

  public String getAirportCode() {
    return airportCode;
  }

  public void setAirportCode(String airportCode) {
    this.airportCode = airportCode;
  }

  public String getOpenSkyUri() {
    return openSkyUri;
  }

  public void setOpenSkyUri(String openSkyUri) {
    this.openSkyUri = openSkyUri;
  }
}
