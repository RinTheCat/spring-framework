package ru.otus.docker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileCopyUtils;
import ru.otus.docker.controller.BookController;
import ru.otus.docker.domain.Author;
import ru.otus.docker.domain.Book;
import ru.otus.docker.domain.Genre;
import ru.otus.docker.service.BookBusinessService;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ResourceLoader resourceLoader;
    @MockBean
    private BookBusinessService bookBusinessService;

    @Value("classpath:responses/getAllBooks.json")
    private Resource getAllBooksResponse;
    @Value("classpath:requests/createBook.json")
    private Resource createBookRequest;

    private static final Author EXISTING_AUTHOR = new Author(1L, "Эдгар Аллан По", new ArrayList<>());
    private static final Genre EXISTING_GENRE = new Genre(1L, "детектив", new ArrayList<>());
    private static final Book NEW_BOOK = new Book(2L, "Чёрный кот", new ArrayList<>(), EXISTING_AUTHOR, EXISTING_GENRE);
    private static final Book EXISTING_BOOK = new Book(1L, "Убийство на улице Морг", new ArrayList<>(), EXISTING_AUTHOR, EXISTING_GENRE);

    @Test
    public void shouldHandleGetAllRequest() throws Exception {
        given(bookBusinessService.getAll()).willReturn(List.of(EXISTING_BOOK));

        String data = FileCopyUtils.copyToString(new InputStreamReader(getAllBooksResponse.getInputStream(), UTF_8));

        this.mvc.perform(get("/api/book")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(data));
    }

    @Test
    public void shouldHandlePostRequest() throws Exception {
        String json = FileCopyUtils.copyToString(new InputStreamReader(createBookRequest.getInputStream(), UTF_8));

        this.mvc.perform(post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void shouldHandleDeleteRequest() throws Exception {
        String url = "/api/book/" + EXISTING_BOOK.getId();
        this.mvc.perform(delete(url)).andExpect(status().isOk());
    }

    @Test
    public void shouldHandlePatchRequest() throws Exception {
        String url = "/api/book/" + EXISTING_BOOK.getId();
        this.mvc.perform(patch(url)
                .queryParam("title", NEW_BOOK.getTitle())
        )
                .andExpect(status().isOk());
    }
}
