package com.text.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.text.demo.model.TextAnalist;
import com.text.demo.service.TextService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TestController {
	@Autowired
	private WebApplicationContext context;

	@InjectMocks
	@Spy
	private TextService textService;

	private MockMvc mvc;

	private TextAnalist textAnalist;

	private TextAnalist TextAnalistFactory(){
		TextAnalist resul = new TextAnalist();
		resul.setId("1");
		resul.setDate(new Date());
		resul.setMostFreqWord("Test");
		resul.setAvgParagrahProcessingTime(0D);
		resul.setAvgParagraphSize(0D);
		return resul;
	}

	public void setupMvc() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.alwaysDo(MockMvcResultHandlers.print())
				.build();
	}

	@Before
	public void setup(){
		setupMvc();
		this.textAnalist = TextAnalistFactory();
		Mockito.doReturn(TextAnalistFactory()).when(textService).getTextAnalyst(1,1,1,1);
	}

	@Test
	public void getTextAnalys(){
		ObjectMapper mapper = new ObjectMapper();
		try {
			this.mvc.perform(
					get("/betvictor/text/p_start/1/p_end/1/w_count_min/1/w_count_max/1")
							.contentType(MediaType.APPLICATION_JSON)
							.content(mapper.writeValueAsString(this.textAnalist)))
					.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
