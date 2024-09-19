package com.akdong.we;

import com.akdong.we.invitation.domain.CustomInvitationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class InvitationTest {
    @LocalServerPort
    private int port;

    private WebSocketStompClient stompClient;

    private StompSession stompSession;

    @BeforeEach
    public void setup() throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String url = "ws://localhost:" + port + "/invitation";
        stompSession = stompClient.connect(url, new StompSessionHandlerAdapter() {}).get();
    }

    @Test
    public void testWebSocketMessage() throws Exception {
        // 구독
        stompSession.subscribe("/topic/1", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                Map<String, Map<String,String>> tree = (Map<String, Map<String,String>>) payload;
                assertEquals("blue", tree.get("insu").get("color"));
            }
        });

        // 메시지 전송
        stompSession.send("/app/chat/send",
                CustomInvitationDto
                        .builder()
                        .invitationId(1)
                        .tree(makeTree("insu","color","blue"))
                        .build());

        // 결과를 기다림
        Thread.sleep(2000);
    }

    @Test
    public void testTwoUserRequest() throws Exception {
        // 구독
        stompSession.subscribe("/topic/1", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                Map<String, Map<String,String>> tree = (Map<String, Map<String,String>>) payload;
                assertEquals("blue", tree.get("insu").get("color"));
            }
        });

        // 메시지 전송
        stompSession.send("/app/chat/send",
                CustomInvitationDto
                        .builder()
                        .invitationId(1)
                        .tree(makeTree("insu","color","blue"))
                        .build());

        // 결과를 기다림
        Thread.sleep(2000);
    }

    private Map<String, Map<String,String>> makeTree(String objectId,String property,String value){

        Map<String,String> node = new HashMap<>();
        node.put(property, value);

        Map<String, Map<String,String>> tree = new HashMap<>();
        tree.put(objectId,node);

        return tree;
    }
}
