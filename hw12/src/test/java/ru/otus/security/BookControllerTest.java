package ru.otus.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.security.config.services.CustomUserDetailsService;
import ru.otus.security.controller.BookController;
import ru.otus.security.domain.Author;
import ru.otus.security.domain.Book;
import ru.otus.security.domain.Genre;
import ru.otus.security.service.BookBusinessService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookBusinessService bookBusinessService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    private static final long EXPECTED_BOOK_COUNT = 1;
    private static final Author EXISTING_AUTHOR = new Author(1L, "Эдгар Аллан По", new ArrayList<>());
    private static final Genre EXISTING_GENRE = new Genre(1L, "детектив", new ArrayList<>());
    private static final Book NEW_BOOK = new Book(2L, "Чёрный кот", null, EXISTING_AUTHOR, EXISTING_GENRE);
    private static final Book EXISTING_BOOK = new Book(1L, "Убийство на улице Морг", null, EXISTING_AUTHOR, EXISTING_GENRE);

    @Test
    @WithMockUser
    @DisplayName("Юзер получит доступ к книгам")
    public void shouldHandleGetAllRequest() throws Exception {
        given(bookBusinessService.getAll()).willReturn(List.of(EXISTING_BOOK));
        given(bookBusinessService.count()).willReturn(EXPECTED_BOOK_COUNT);

        this.mvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("allBooks"))
                .andExpect(MockMvcResultMatchers.model().attribute("number", EXPECTED_BOOK_COUNT))
                .andExpect(MockMvcResultMatchers.model().attribute("books", List.of(EXISTING_BOOK)));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Анонимного юзера перенаправит на аутентификацию")
    public void shouldRejectGetAllRequest() throws Exception {
        given(bookBusinessService.getAll()).willReturn(List.of(EXISTING_BOOK));
        given(bookBusinessService.count()).willReturn(EXPECTED_BOOK_COUNT);

        this.mvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    @DisplayName("Юзер сможет создать книгу")
    public void shouldHandlePostEditRequest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/books/create")
            .queryParam("title", NEW_BOOK.getTitle())
            .queryParam("authorId", "1")
            .queryParam("genreId", "1")
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}
