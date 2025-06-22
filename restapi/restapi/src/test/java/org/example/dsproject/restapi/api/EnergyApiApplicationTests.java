package org.example.dsproject.restapi.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = EnergyApiApplication.class)
@AutoConfigureMockMvc
public class EnergyApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;


	@Test
	void testHistoricalWithParams() throws Exception {
		mockMvc.perform(get("/api/usage")
						.param("start", "2025-01-10T00:00:00")
						.param("end", "2025-01-10T23:00:00"))
				.andExpect(status().isOk());
	}
}
