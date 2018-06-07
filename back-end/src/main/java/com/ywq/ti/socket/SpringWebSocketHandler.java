package com.ywq.ti.socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SpringWebSocketHandler extends TextWebSocketHandler {

	private static Logger logger = LoggerFactory.getLogger(SpringWebSocketHandler.class);

	private static final Map<String, WebSocketSession> sessions = new HashMap<String, WebSocketSession>(); // 在线用户列表
	private static final String USER_ID = "WEBSOCKET_USERID"; 


	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String userId = session.getUri().getQuery().substring(8);
		System.out.println("用户 "+userId+" 成功建立websocket连接!");
		//String userId = (String) session.getAttributes().get(USER_ID);
		sessions.put(userId, session);
		System.out.println("当前线上用户数量:" + sessions.size());

		// 这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
		TextMessage returnMessage = new TextMessage("成功建立socket连接，来自服务器端的问候");
		session.sendMessage(returnMessage);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		logger.debug("关闭websocket连接");
		//String userId = (String) session.getAttributes().get(USER_ID);
		String userId = session.getUri().getQuery().substring(8);
		System.out.println("用户" + userId + "已退出！");
		sessions.remove(userId);
		System.out.println("剩余在线用户" + sessions.size());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		super.handleTextMessage(session, message);
		System.out.println("服务器收到消息：" + message);
		if (message.getPayload().startsWith("#anyone#")) { // 单发某人
			sendMessageToUser((String) session.getAttributes().get(USER_ID),
					new TextMessage("服务器单发：" + message.getPayload()));
		} else if (message.getPayload().startsWith("#everyone#")) {
			sendMessageToUsers(new TextMessage("服务器群发：" + message.getPayload()));
		} else {
		}

	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		logger.debug("传输出现异常，关闭websocket连接... ");
		String userId = (String) session.getAttributes().get(USER_ID);
		sessions.remove(userId);
	}

	/** 
	 * 给某个用户发送消息 
	 * 
	 * @param userId 
	 * @param message 
	 */
	public void sendMessageToUser(String userId, TextMessage message) {
		for (String id : sessions.keySet()) {
			if (id.equals(userId)) {
				try {
					if (sessions.get(id).isOpen()) {
						sessions.get(id).sendMessage(message);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

	/** 
	 * 给所有在线用户发送消息 
	 * 
	 * @param message 
	 */
	public void sendMessageToUsers(TextMessage message) {
		for (String userId : sessions.keySet()) {
			try {
				if (sessions.get(userId).isOpen()) {
					sessions.get(userId).sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//测试一下普通组建能否调用“我”
	public String hellobaby(){
		return "Hello,world!";
	}

}
