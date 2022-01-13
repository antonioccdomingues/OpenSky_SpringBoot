package com.P1L15.airport.services;

import com.P1L15.airport.data.messaging.KafkaProducer;
import com.P1L15.airport.services.models.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.time.LocalDateTime.now;

@Service
public class ProducerService {

  @Autowired KafkaProducer kafkaProducer;
  @Autowired FlightService flightService;

  public void getNewFlights() {


    try{
      final Collection<Flight> newFlights =
              flightService.getFlightsForTimeInterval(now().minusDays(1), now());  //vou por aqui um dia apenas para o prof ver q está a funcionar

      for (Flight flight : newFlights) {
        kafkaProducer.sendMessage(flight.getEstArrivalAirport(), flight);
      }

    }catch (Exception e){
      Logger logger = LoggerFactory.getLogger(ProducerService.class);
      logger.info("Não chegou nenhum voo ao Aeroporto nos ultimos 5 minutos");
    }
  }
}
