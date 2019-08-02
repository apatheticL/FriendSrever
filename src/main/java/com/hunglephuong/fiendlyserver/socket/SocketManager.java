package com.hunglephuong.fiendlyserver.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunglephuong.fiendlyserver.Constant;
import com.hunglephuong.fiendlyserver.model.MessageChatResponse;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class SocketManager {
    private SocketIOServer socketIOServer;
    private ObjectMapper objectMapper = new ObjectMapper();
    private Map<String, SocketIOClient> ioClientMap = new HashMap<>();
    @PostConstruct
    public void inits(){
        Configuration config =  new Configuration();
        config.setHostname(Constant.IP_SERVER);
        config.setPort(9092);
        socketIOServer = new SocketIOServer(config);
        socketIOServer.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("onConnect Test connect..........");
            }
        });
        socketIOServer.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient socketIOClient) {
                System.out.println("onDisconnect Test connect..........");
                for (String s : ioClientMap.keySet()) {
                    if (ioClientMap.get(s) == socketIOClient) {
                        ioClientMap.remove(s);
                    }
                }
            }
        });
        socketIOServer.addEventListener("connected", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                System.out.println("onData Test connect.........." + s);
                ioClientMap.put(s, socketIOClient);
            }
        });

//        socketIOServer.addEventListener("message", String.class, new DataListener<String>() {
//            @Override
//            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
//                System.out.println("onData Test connect.........." + s);
//
//
//            }
//        });
        socketIOServer.addEventListener("insert", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                System.out.println("onData Inserted.........." + s);
            }
        });

        socketIOServer.addEventListener("message", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                System.out.println("onData Test connect.........." + s);

                MessageChatResponse message = objectMapper.readValue(s, MessageChatResponse.class);
                int receiverId = message.getReceiverId();
                if (ioClientMap.keySet().contains(receiverId+"")){
                    ioClientMap.get(receiverId+"").sendEvent("message", s);
                }

            }
        });
        socketIOServer.start();
    }
}
