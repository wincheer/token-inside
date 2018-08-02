package com.ywq.ti.socket;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.ywq.ti.common.BcMessage;
import com.ywq.ti.dao.AppUserSubscribeMapper;
import com.ywq.ti.entity.AppUserSubscribe;

public class SpringWebSocketHandler extends TextWebSocketHandler {

	private static Logger log = LoggerFactory.getLogger(SpringWebSocketHandler.class);

	private static final Map<Long, WebSocketSession> online_users = new HashMap<Long, WebSocketSession>(); // 在线用户列表
	private static final String USER_ID = "WEBSOCKET_USERID"; 
	
	@Autowired
	private AppUserSubscribeMapper subscribeDao;


	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Long userId = Long.valueOf(session.getUri().getQuery().substring(8)); 
		log.debug("用户 "+userId+" 成功建立websocket连接!");
		//String userId = (String) session.getAttributes().get(USER_ID);
		online_users.put(userId, session);
		log.debug("当前线上用户数量:" + online_users.size());
		// 这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
		TextMessage returnMessage = new TextMessage("成功建立socket连接，来自服务器端的问候");
		session.sendMessage(returnMessage);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		log.debug("关闭websocket连接");
		//String userId = (String) session.getAttributes().get(USER_ID);
		Long userId = Long.valueOf(session.getUri().getQuery().substring(8));
		log.debug("用户" + userId + "已退出！");
		online_users.remove(userId);
		log.debug("剩余在线用户" + online_users.size());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		super.handleTextMessage(session, message);
		log.debug("服务器收到消息：" + message);
		if (message.getPayload().startsWith("#anyone#")) { // 单发某人
			sendMessageToUser((Long) session.getAttributes().get(USER_ID),
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
		log.debug("传输出现异常，关闭websocket连接... ");
		String userId = (String) session.getAttributes().get(USER_ID);
		online_users.remove(userId);
	}

	/** 
	 * 给某个用户发送消息 
	 * 
	 * @param userId 
	 * @param message 
	 */
	public void sendMessageToUser(Long userId, TextMessage message) {
		for (Long _userId : online_users.keySet()) {
			if (_userId.equals(userId)) {
				try {
					if (online_users.get(_userId).isOpen()) {
						online_users.get(_userId).sendMessage(message);
					}
				} catch (IOException e) {
					log.error(e.getMessage());
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
		for (Long userId : online_users.keySet()) {
			try {
				if (online_users.get(userId).isOpen()) {
					online_users.get(userId).sendMessage(message);
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}

	/**
	 * 根据订阅内容，通知在线用户交易信息
	 * @param msgList
	 * @throws IOException 
	 */
	public void notifyTxInfo(List<BcMessage> msgList) throws IOException {
		//1.遍历每一个在线用户
		//2.如果用户订阅了内容 -> 推送消息 -> 更新订阅的最新日期
		TextMessage message = new TextMessage("itemInMsgList");
		for (Long userId : online_users.keySet()) {
			if (online_users.get(userId).isOpen()) {
				Map<String,String> userSubscribeMap = getSubscribesOf(userId);
				for(BcMessage msg:msgList){
					if(userSubscribeMap.containsKey(msg.getFrom())||userSubscribeMap.containsKey(msg.getTo())){
						online_users.get(userId).sendMessage(message);//推送消息	
						String address = userSubscribeMap.containsKey(msg.getFrom())?msg.getFrom():msg.getTo();
						updateSubscribeDate(userId,address,msg.getTimestamp()); //更新最新订阅时间
					}
				}
			}
		}
	}

	/**
	 * 更新用户订阅信息(更新时间)
	 * @param userId
	 * @param address
	 * @param timestamp
	 */
	private void updateSubscribeDate(Long userId, String address, Date timestamp) {
		AppUserSubscribe subscribe = new AppUserSubscribe();
		subscribe.setUserId(userId);
		subscribe.setAddress(address);
		subscribe.setUpdate(timestamp);
		
		subscribeDao.updateSubscribeDate(subscribe);
	}

	/**
	 * 获取指定用户的订阅内容<地址，主题>
	 * @param userId
	 * @return
	 */
	private Map<String, String> getSubscribesOf(Long userId) {
		Map<String, String> subscribeMap = new HashMap<String, String>();
		
		AppUserSubscribe subscribe = new AppUserSubscribe();
		subscribe.setUserId(userId);
		List<AppUserSubscribe> subscribeList = subscribeDao.selectSubscribeList(subscribe);
		for(AppUserSubscribe _subscribe:subscribeList){
			subscribeMap.put(_subscribe.getAddress(), _subscribe.getTopic());
		}
		return subscribeMap;
	}

}
