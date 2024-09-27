package com.example.demo.controller;

import com.example.demo.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class GameControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private GameService gameService;

    @Test
    void playTest() throws Exception {
        Map<String, String> expectedResult = new HashMap<>();
        expectedResult.put("result", "Win");

        when(gameService.playGame("rock")).thenReturn(expectedResult);
                    //Здесь мы эмулируем HTTP GET запрос на адрес /play
        mvc.perform(get("/play")
                        //Мы передаем параметр в запросе с именем choice и значением "rock"
                        .param("choice", "rock"))
                //Мы ожидаем, что статус ответа будет 200 OK,
                // что означает, что запрос был успешно обработан.
                .andExpect(status().isOk())
                //Мы проверяем, что в JSON-ответе, возвращаемом сервером,
                // есть поле result, и его значение должно быть "Win"
                .andExpect(jsonPath("$.result").value("Win"));

    }
}
