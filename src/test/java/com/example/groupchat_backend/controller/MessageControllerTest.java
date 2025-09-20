package com.example.groupchat_backend.controller;

import com.example.groupchat_backend.models.message.RawGroupMessageDTO;
import com.example.groupchat_backend.models.message.UpdatedGroupMessageResponse;
import com.example.groupchat_backend.models.message.baseClasses.MessagePageResponse;
import com.example.groupchat_backend.services.interfaces.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebFluxTest(
        controllers = MessageController.class,
        excludeAutoConfiguration = {
                ReactiveSecurityAutoConfiguration.class
        }
)
@ContextConfiguration(classes = {MessageController.class})
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        ReactiveSecurityAutoConfiguration.class
})
class MessageControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MessageService messageService;

    @Autowired
    public void setApplicationContext(ApplicationContext context) {
        this.webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void getAllMessages_ReturnsOk() {
        MessagePageResponse mockResponse = MessagePageResponse.builder().build();

        when(messageService.getChannelsWithMessages(anyString(), anyInt(), anyInt()))
                .thenReturn(Mono.just(mockResponse));

        webTestClient.get()
                .uri("/message/all?userId=test&pageSize=10&page=0")
                .exchange()
                .expectStatus().isOk()
                .expectBody(MessagePageResponse.class);
    }

    @Test
    void getAllMessages_returnBadRequest(){
        when(messageService.getChannelsWithMessages(anyString(), anyInt(), anyInt()))
                .thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/message/all")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void postMessageToGroup_savedSuccessfully(){
        RawGroupMessageDTO messageDTO = RawGroupMessageDTO.builder()
                        .messageText("Test")
                                .sentToGroupId("test1234")
                                        .sentByUserId("testUser")
                                                .build();
        UpdatedGroupMessageResponse mockUpdatedMessage = UpdatedGroupMessageResponse.builder().build();
        when(messageService.postMessageIntoGroup(any())).thenReturn(Mono.just(mockUpdatedMessage));

        webTestClient.post()
                .uri("/message/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(messageDTO)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void postMessageToGroup_badRequestErrorWhenBodyNotPassed(){
        UpdatedGroupMessageResponse mockUpdatedMessage = UpdatedGroupMessageResponse.builder().build();
        when(messageService.postMessageIntoGroup(any())).thenReturn(Mono.just(mockUpdatedMessage));

        webTestClient.post()
                .uri("/message/new")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void postMessageToGroup_badRequestErrorWhenRequiredFieldIsMissingInBody(){
        RawGroupMessageDTO messageDTO = RawGroupMessageDTO.builder()
                .sentToGroupId("test1234")
                .sentByUserId("testUser")
                .build();
        UpdatedGroupMessageResponse mockUpdatedMessage = UpdatedGroupMessageResponse.builder().build();
        when(messageService.postMessageIntoGroup(any())).thenReturn(Mono.just(mockUpdatedMessage));

        webTestClient.post()
                .uri("/message/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(messageDTO)
                .exchange()
                .expectStatus().isOk();
    }
}
