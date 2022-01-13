package com.P1L15.airport.data.external;

import com.P1L15.airport.config.AppConfig;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenSkyClient {

  @Autowired private AppConfig appConfig;

  // Metodo que faz o pedido à API Externa
  public Collection<OpenSkyDto> getFlights(
      String airportCode, LocalDateTime begin, LocalDateTime end) throws RestClientException {
    RestTemplate restTemplate = new RestTemplate();
    UriComponentsBuilder uriComponentsBuilder =
        UriComponentsBuilder.fromUriString(appConfig.getOpenSkyUri())
            // Add query parameter
            .queryParam("airport", airportCode)
            .queryParam("begin", begin.toEpochSecond(OffsetDateTime.now().getOffset()))
            .queryParam("end", end.toEpochSecond(OffsetDateTime.now().getOffset()));

    // Aqui usei o get em vez do exchange, assim retonar um array e depois converto para lista
    OpenSkyDto[] response =
        restTemplate.getForObject(uriComponentsBuilder.build().toUri(), OpenSkyDto[].class);

    return Arrays.asList(response);
  }
}
