package jiaruchun.cangqiongservice.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket服务
 */
@Slf4j
@Component
@ServerEndpoint("/ws/{sid}")
public final class WebSocketServer {

    //存放会话对象
    private static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        log.info("连接建立成功:{}", sid);
        sessionMap.put(sid, session);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        log.info("收到来自窗口{}的信息:{}", sid, message);
    }

    /**
     * 连接关闭调用的方法
     *
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        log.info("连接关闭:{}", sid);
        sessionMap.remove(sid);
    }

    /**
     * 群发
     *
     */
    public void sendToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                // 服务器向客户端发送消息
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                log.error("向客户端发送消息失败，会话ID: {}", session.getId(), e);
                try {
                    session.close();
                    sessionMap.values().remove(session);
                } catch (Exception ex) {
                    log.error("关闭会话失败，会话ID: {}", session.getId(), ex);
                }
            }
        }
    }

}
