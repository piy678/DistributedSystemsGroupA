package org.example.energyuser;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.time.ZonedDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnergyGeneratorTest {

    @Test
    void testGenerateEnergySendsRealisticKwh() {
        // Mock erstellen
        EnergyUserSender mockSender = mock(EnergyUserSender.class);
        EnergyGenerator generator = new EnergyGenerator(mockSender);

        // Methode ausführen
        generator.generateEnergy();

        // Message abfangen
        ArgumentCaptor<EnergyMessage> captor = ArgumentCaptor.forClass(EnergyMessage.class);
        verify(mockSender, times(1)).sendEnergyMessage(captor.capture());

        EnergyMessage message = captor.getValue();

        // Assertions
        assertNotNull(message);
        assertEquals("USER", message.getType());
        assertEquals("COMMUNITY", message.getAssociation());
        assertTrue(message.getKwh() >= 0.0005 && message.getKwh() <= 0.005, "kWh muss im realistischen Bereich liegen");
        assertNotNull(message.getDatetime());
        assertTrue(message.getDatetime().isBefore(ZonedDateTime.now().plusSeconds(2).toInstant()));
    }
}
