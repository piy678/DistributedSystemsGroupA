package org.example.dsproject.restapi;

import org.example.dsproject.restapi.api.EnergyApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = EnergyApiApplication.class)

@AutoConfigureMockMvc
public class EnergyApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testUsageEndpoint() throws Exception {
		mockMvc.perform(get("/api/usage"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void testCurrentPercentageEndpoint() throws Exception {
		mockMvc.perform(get("/api/current-percentage"))
				.andExpect(status().isOk());
	}
}

