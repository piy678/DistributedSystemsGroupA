package org.example.energyproducer;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnergyGeneratorTest {

    @Test
    void testGenerateEnergySendsValidMessage() {
        EnergyProducerSender sender = mock(EnergyProducerSender.class);
        EnergyGenerator generator = new EnergyGenerator(sender);

        // Manuell Felder setzen, die normalerweise von Spring @Value kommen
        ReflectionTestUtils.setField(generator, "type", "PRODUCER");
        ReflectionTestUtils.setField(generator, "association", "COMMUNITY");

        generator.generateEnergy();

        ArgumentCaptor<EnergyMessage> captor = ArgumentCaptor.forClass(EnergyMessage.class);
        verify(sender, times(1)).sendEnergyMessage(captor.capture());

        EnergyMessage message = captor.getValue();

        assertNotNull(message);
        assertEquals("PRODUCER", message.getType()); // Jetzt klappt es!
        assertEquals("COMMUNITY", message.getAssociation());
        assertTrue(message.getKwh() >= 0.001 && message.getKwh() <= 0.005);
        assertNotNull(message.getDatetime());
    }

}
