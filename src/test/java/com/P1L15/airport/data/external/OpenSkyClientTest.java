/*package com.P1L15.airport.data.external;

import com.P1L15.airport.config.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OpenSkyClientTest {

    private OpenSkyClient openSkyClient;
    @Mock private AppConfig appConfig;
    @BeforeEach
    void setUp() {
        openSkyClient = new OpenSkyClient(); //inicializar o cliente
        when(appConfig.getOpenSkyUri()).thenReturn("abc");
    }

    @Test
    void getFlights_thenReturnOpenSkyDto() {
        LocalDateTime beginDate = LocalDateTime.of(2020, 4, 2, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 4, 3, 10, 0);
        String airportCode = "abc";

        Collection<OpenSkyDto> flights = openSkyClient.getFlights(airportCode, beginDate, endDate);

        assertEquals(getExpectedResults(), flights);

    }

    private Collection<OpenSkyDto> getExpectedResults(){
        OpenSkyDto openSkyDto = new OpenSkyDto();
        openSkyDto.setCallsign("abc");

        return Collections.singletonList(openSkyDto);
    }
}

 */
