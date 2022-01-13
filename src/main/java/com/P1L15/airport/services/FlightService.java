package com.P1L15.airport.services;

import com.P1L15.airport.config.AppConfig;
import com.P1L15.airport.data.external.OpenSkyClient;
import com.P1L15.airport.data.external.OpenSkyDto;
import com.P1L15.airport.data.persistance.entity.FlightEntity;
import com.P1L15.airport.data.persistance.repository.FlightRepository;
import com.P1L15.airport.services.models.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.ofInstant;

@Service
public class FlightService {
  @Autowired private OpenSkyClient openSkyClient;
  @Autowired private AppConfig appConfig;
  @Autowired private FlightRepository flightRepository; // passo para a db

  public Collection<Flight>
      getLast24HoursFlights() { // cada vez que este metodo é chamado, as ultimas 24h vão para a bd
    return getFlightsForTimeInterval(now().minusHours(24), now());

    // return
    // openSkyDtos.stream().map(this::mapFromOpenSkyDtoToFlight).collect(Collectors.toList());
  }

  public Collection<Flight> get7DaysFlightsFromDb() {
    ArrayList<Flight> flights = new ArrayList<>();
    Collection<FlightEntity> flightEntityList =
        flightRepository.findByLastSeenBetween(now().minusDays(7), now());
    for (FlightEntity entity : flightEntityList) {
      Flight flight = mapFromEntityToModel(entity);
      flights.add(flight);
    }
    return flights;
  }

  public Collection<Flight> getFlightsForTimeInterval(LocalDateTime start, LocalDateTime end) {
    Collection<OpenSkyDto> openSkyDtos =
        openSkyClient.getFlights(appConfig.getAirportCode(), start, end);
    ArrayList<Flight> flights = new ArrayList<>();
    for (OpenSkyDto dto : openSkyDtos) {
      Flight flight = mapFromOpenSkyDtoToFlight(dto);
      flights.add(flight);
    }
    saveFlights(flights);
    return flights;
  }

  private void saveFlights(Collection<Flight> flightsToSave) {

    Collection<FlightEntity> flightEntities = new ArrayList<>();

    for (Flight flight : flightsToSave) {
      FlightEntity f = new FlightEntity();
      f.setCallsign(flight.getCallsign());
      f.setEstArrivalAirport(flight.getEstArrivalAirport());
      f.setLastSeen(flight.getLastSeen());
      f.setIcao24(flight.getIcao24());
      f.setEstDepartureAirport(flight.getEstDepartureAirport());
      flightEntities.add(f);
    }

    flightRepository.saveAll(flightEntities);
  }

  // apenas para popular a bd
  public void createInitialData() {
    Collection<OpenSkyDto> openSkyDtos =
        openSkyClient.getFlights(
            appConfig.getAirportCode(), now().minusDays(6).minusHours(20), now().minusDays(1));
    ArrayList<Flight> flights = new ArrayList<>();
    for (OpenSkyDto dto : openSkyDtos) {
      Flight flight = mapFromOpenSkyDtoToFlight(dto);
      flights.add(flight);
    }
    System.out.println("Base de dados populada\n");
    saveFlights(flights);
  }

  // até aqui

  private Flight mapFromOpenSkyDtoToFlight(OpenSkyDto openSkyDto) {
    Flight flight = new Flight();
    flight.setCallsign(openSkyDto.getCallsign());
    flight.setIcao24(openSkyDto.getIcao24());
    flight.setLastSeen(
        ofInstant(Instant.ofEpochSecond(openSkyDto.getLastSeen()), ZoneId.systemDefault()));
    flight.setFirstSeen(
        ofInstant(Instant.ofEpochSecond(openSkyDto.getFirstSeen()), ZoneId.systemDefault()));
    flight.setEstArrivalAirport(openSkyDto.getEstArrivalAirport());
    flight.setEstDepartureAirport(openSkyDto.getEstDepartureAirport());
    flight.setIcao24(openSkyDto.getIcao24());
    // ....
    return flight;
  }

  private Flight mapFromEntityToModel(FlightEntity flightEntity) {
    Flight flight = new Flight();
    flight.setCallsign(flightEntity.getCallsign());
    flight.setIcao24(flightEntity.getIcao24());
    flight.setLastSeen(flightEntity.getLastSeen());
    flight.setEstDepartureAirport(flightEntity.getEstDepartureAirport());
    flight.setEstArrivalAirport(flightEntity.getEstArrivalAirport());

    return flight;
  }
}
