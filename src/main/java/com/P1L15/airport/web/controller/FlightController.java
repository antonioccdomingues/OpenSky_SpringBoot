package com.P1L15.airport.web.controller;

import com.P1L15.airport.services.FlightService;
import com.P1L15.airport.services.ProducerService;
import com.P1L15.airport.services.models.Flight;
import com.P1L15.airport.web.dto.FlightDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

// Aqui tem de ser RestController em vez de controller. O priemeio é para REST Api e o segundo para
// MVC
@RestController
@EnableScheduling
@RequestMapping("/api")
public class FlightController {
  @Autowired private FlightService flightService;
  @Autowired private ProducerService producerService;

  @GetMapping(path = "/flights")
  @ResponseStatus(HttpStatus.OK)
  @Scheduled(fixedRate = 5000) // 5 em 5 segundos (ms)
  public Collection<FlightDto> getLast24HFlights() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    Collection<Flight> last24HoursFlights = flightService.getLast24HoursFlights();
    // mapear para FlightDTO
    Collection<FlightDto> flightDtos = new ArrayList<>();
    for (Flight flight : last24HoursFlights) {
      FlightDto flightDto = mapFromFlightToFlightDto(flight);
      flightDtos.add(flightDto);
    }
    return flightDtos;
  }


  @PostMapping(path = "/update")
  @ResponseStatus(HttpStatus.OK)
  public void updateBd() {
    flightService.createInitialData();
  }

  @GetMapping(path = "/history")
  @ResponseStatus(HttpStatus.OK)
  public Collection<FlightDto> getLast7DFlights() { // from db
    Collection<Flight> last24HoursFlights = flightService.get7DaysFlightsFromDb();
    // mapear para FlightDTO
    Collection<FlightDto> flightDtos = new ArrayList<>();
    for (Flight flight : last24HoursFlights) {
      FlightDto flightDto = mapFromFlightToFlightDto(flight);
      flightDtos.add(flightDto);
    }
    return flightDtos;
  }

  private FlightDto mapFromFlightToFlightDto(Flight flight) {
    FlightDto flightDto = new FlightDto();
    flightDto.setCallsign(flight.getCallsign());
    flightDto.setLastSeen(flight.getLastSeen());
    flightDto.setFirstSeen(flight.getFirstSeen());
    flightDto.setEstArrivalAirport(flight.getEstArrivalAirport());
    flightDto.setEstDepartureAirport(flight.getEstDepartureAirport());
    flightDto.setIcao24(flight.getIcao24());
    return flightDto;
  }
  // fazer os metodos restantes para todos os atributos
}
