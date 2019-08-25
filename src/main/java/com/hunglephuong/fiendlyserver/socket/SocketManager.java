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
import com.hunglephuong.fiendlyserver.model.Messages;
import com.hunglephuong.fiendlyserver.model.response.MessageChatResponse;
import com.hunglephuong.fiendlyserver.repository.*;
import com.hunglephuong.fiendlyserver.repository.StatusRepository;
import com.hunglephuong.fiendlyserver.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SocketManager {
    private SocketIOServer socketIOServer;
    private ObjectMapper objectMapper = new ObjectMapper();
    private Map<String, SocketIOClient> ioClientMap = new HashMap<>();
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private MessageRepositiory messageRepositiory;

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private StatusRepository statusRepository;
    @PostConstruct
    public void inits(){
        Configuration config =  new Configuration();
        String ip = null;
//        try {
//            ip= InetAddress.getLocalHost().getHostAddress();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
            ip = Constant.IP_SERVER;
//        }
        System.out.println("ip address: " + ip);
        config.setHostname(ip);
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

        socketIOServer.addEventListener("message", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                System.out.println("onData Test connect.........." + s);
                MessageChatResponse message =
                        objectMapper.readValue(s, MessageChatResponse.class);
                int receiverId = message.getReceiverId();
                if (ioClientMap.keySet().contains(receiverId+"")){
                    ioClientMap.get(receiverId+"").sendEvent("message", s);
                }
                saveMessage(message);
            }
        });
//        socketIOServer.addEventListener("status", String.class, new DataListener<String>() {
//            @Override
//            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
//                StatusResponse statusResponse =
//                        objectMapper.readValue(s,StatusResponse.class);
//
//                statusRepository.insertStatus(statusResponse.getUserId(),statusResponse.getContent(),statusResponse.getAttachments());
//            }
//        });
        socketIOServer.start();
    }
    private void saveMessage(MessageChatResponse msg){
        Messages message = new Messages();
        message.setContent(msg.getContent());
        message.setSenderId(msg.getSenderId());
        message.setReceiverId(msg.getReceiverId());
        message.setType(msg.getType());
        message.setId(msg.getId());
        message = messageRepositiory.save(message);
        friendRepository.updateLastMessage(message.getId(), message.getSenderId(), message.getReceiverId());
    }
}
