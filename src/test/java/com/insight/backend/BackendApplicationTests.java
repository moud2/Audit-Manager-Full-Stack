package com.insight.backend;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insight.backend.model.Audit;

@SpringBootTest
@AutoConfigureMockMvc

class BackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    // TODO: Temporary code for basic functionality | remove and reimplement properly later
	@Test
	public void testEndpointGetAudits() throws Exception {
        List<Audit> auditList = new ArrayList<>();

        Audit audit1 = new Audit(0, "ISO-2123");
        Audit audit2 = new Audit(1, "ISO-2124");
        Audit audit3 = new Audit(2, "ISO-2125");
        auditList.add(audit1);
        auditList.add(audit2);
        auditList.add(audit3);

		mockMvc.perform(get("/api/v1/audits")).andExpect(status().isOk()).andExpect(content().string(gson.toJson(auditList)));
	}

}
