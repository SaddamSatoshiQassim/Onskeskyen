/* package com.example.Onskeskyen.controllers;

import com.example.Onskeskyen.services.BrugerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Locale;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BrugerControllerTest {

    private MockMvc mockMvc;
    private BrugerService brugerService;

    @BeforeEach
    void setup() {
        brugerService = Mockito.mock(BrugerService.class);

        BrugerController controller = new BrugerController(brugerService);

        ViewResolver viewResolver = new ViewResolver() {
            @Override
            public View resolveViewName(String viewName, Locale locale) {
                if (viewName.startsWith("redirect:")) {
                    String redirectUrl = viewName.substring("redirect:".length());
                    return new RedirectView(redirectUrl);
                }

                return new View() {
                    @Override
                    public String getContentType() {
                        return "text/html";
                    }

                    @Override
                    public void render(Map<String, ?> model,
                                       jakarta.servlet.http.HttpServletRequest request,
                                       jakarta.servlet.http.HttpServletResponse response) {
                    }
                };
            }
        };

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void testStartside() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void testLogin() throws Exception {
        mockMvc.perform(post("/login")
                        .param("brugernavn", "imrane")
                        .param("kodeord", "1234"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testVisOnskeliste() throws Exception {
        mockMvc.perform(get("/onskeliste"))
                .andExpect(status().isOk())
                .andExpect(view().name("onskeliste"));
    }
}

 */