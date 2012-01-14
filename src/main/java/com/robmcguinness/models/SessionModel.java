/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robmcguinness.models;

import org.apache.wicket.Application;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Model that displays whether a session was created yet, and if it was, prints the session id.
 * 
 * @author Eelco Hillenius
 */
public class SessionModel extends AbstractReadOnlyModel<String> {

	boolean stateless = true;

	@Override
	public String getObject() {
		final String msg;
		String sessionId = Application.get().getSessionStore().getSessionId(RequestCycle.get().getRequest(), false);
		if (sessionId == null) {
			msg = "stateless mode";
			stateless = true;
		} else {
			msg = "statefull mode";
			stateless = false;
		}
		return msg;
	}

	public boolean isStateless() {
		return stateless;
	}

}
