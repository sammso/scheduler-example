package com.sohlman.liferay.examples.scheduler;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;


/**
 * @author sammso
 */
@Component(immediate = true)
public class SchedulerExample {
	
	@Activate
	protected void activate(Map<String, Object> properties) {		
		String eventListenerClass = _exampleMessageListener.getClass().getName();
		String jobName = "SchedulerExample";
		
		String groupName = eventListenerClass;
		Date startDate = null;
		Date endDate = null;
		
		String cronExpression = "";
		int interval = 10;
		TimeUnit timeUnit = TimeUnit.SECOND;
		
		// Trigger trigger = _triggerFactory.createTrigger(jobName, groupName, startDate, endDate, cronExpression)
		
		Trigger trigger = _triggerFactory.createTrigger(jobName, groupName, startDate, endDate, interval, timeUnit);

		
		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(eventListenerClass, trigger);
		_schedulerEngineHelper.register(
				_exampleMessageListener, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
		
		_logService.log(LogService.LOG_ERROR, "Scheduler Job " + trigger.getJobName() + " registered");	

	}

	@Modified
	protected void modify() {
		System.err.println("modify");

	}
	
	@Deactivate
	protected void deactivate() {
		_logService.log(LogService.LOG_INFO, _exampleMessageListener.toString() + " unregistered");
		_schedulerEngineHelper.unregister(_exampleMessageListener);
	}
	
	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private ExampleMessageListener _exampleMessageListener;
	
	@Reference
	private TriggerFactory _triggerFactory;	
	

	@Reference
	private LogService _logService;
}