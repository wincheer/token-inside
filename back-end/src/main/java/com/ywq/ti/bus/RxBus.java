package com.ywq.ti.bus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * <b>版权信息 :</b> 2018，杨文清<br/>
 * <b>功能描述 :</b> 基于 RxJava 的异步事件总线<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年6月23日 下午10:49:27 | 创建
 */
public class RxBus {
	
	private static volatile RxBus defaultInstance;
	private final Subject<Object, Object> bus;

	public RxBus() {
		// 将 PublishSubject 转换为 SerializedSubject，确保线程安全
		bus = new SerializedSubject<>(PublishSubject.create());
	}

	/* 单例 */
	public static RxBus getDefault() {
		if (defaultInstance == null) {
			synchronized (RxBus.class) {
				if (defaultInstance == null) {
					defaultInstance = new RxBus();
				}
			}
		}
		return defaultInstance;
	}

	/**
	 * 发送/发布/广播 事件
	 * RxBus.getDefault().publish(new UserEvent (1, "foo"));
	 * @param event
	 */
	public void publish(Object event) {
		if (bus == null) {
			defaultInstance = new RxBus();
		}
		bus.onNext(event);
	}

	/**
	 * 订阅事件
	 * RxBus.getDefault().toObservable(UserEvent.class).subscribe(evt->{})
	 * @param eventType
	 * @return
	 */
	public <T> Observable<T> toObservable(Class<T> eventType) {
		return bus.ofType(eventType);// ofType可以根据事件类型发送指定数据
	}
	
}
