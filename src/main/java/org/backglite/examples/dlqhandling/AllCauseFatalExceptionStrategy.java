package org.backglite.examples.dlqhandling;

import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;

public class AllCauseFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {
	@Override
	protected boolean isUserCauseFatal(Throwable cause) {
		return true;
	}
}
