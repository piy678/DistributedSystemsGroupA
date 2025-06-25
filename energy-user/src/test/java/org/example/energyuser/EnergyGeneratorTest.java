package org.example.energyuser;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnergyGeneratorTest {

    @Test
    void testGenerateEnergySendsRealisticKwh() {
        EnergyUserSender mockSender = mock(EnergyUserSender.class);
        EnergyGenerator generator = new EnergyGenerator(mockSender);

        generator.generateEnergy();

        ArgumentCaptor<EnergyMessage> captor = ArgumentCaptor.forClass(EnergyMessage.class);
        verify(mockSender, times(1)).sendEnergyMessage(captor.capture());

        EnergyMessage message = captor.getValue();

        assertNotNull(message);
        assertEquals("USER", message.getType());
        assertEquals("COMMUNITY", message.getAssociation());
        assertTrue(message.getKwh() >= 0.0005 && message.getKwh() <= 0.005);

        LocalDateTime messageTime = message.getDatetime();
        LocalDateTime now = LocalDateTime.now();

        assertTrue(messageTime.isBefore(now.plusSeconds(2)));
    }

}
