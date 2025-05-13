package ru.buzynnikov.inventory_service.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link ReceiptLogController}
 */
@SpringBootTest
@Sql(scripts = {"/schema.sql", "/test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/clean-up.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@AutoConfigureMockMvc
public class ReceiptLogControllerTest {

    @Autowired
    private MockMvc mockMvc;



    @Test
    public void saveReceiptLog() throws Exception {
        String ingredients = """
                [
                    {
                        "ingredientId": 1,
                        "amount": 25
                    }
                ]""";

        mockMvc.perform(post("/api/v1/receipt/{1}", "1002745")
                        .content(ingredients)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getAllReceiptFromDateToDate() throws Exception {
        mockMvc.perform(get("/api/v1/receipt")
                        .param("fromDate", "2025-05-13")
                        .param("toDate", "2025-05-14"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
