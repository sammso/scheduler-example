package com.sohlman.liferay.examples.scheduler;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;


@Component(immediate=true, service=ExampleMessageListener.class)
public class ExampleMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		_logService.log(LogService.LOG_ERROR, "doReceive(Message message)  Scheduler has been exectued " + System.currentTimeMillis());
	}

	@Reference
	private LogService _logService;
}
