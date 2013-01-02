/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.slf4j.impl;

import org.slf4j.spi.MDCAdapter;

import ch.qos.logback.classic.util.LogbackMDCAdapter;

/**
 * Based on org.slf4j.impl.StaticMDCBinder from Grails.
 *
 * @author Graeme Rocher
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
public class StaticMDCBinder {

	public static final StaticMDCBinder SINGLETON = new StaticMDCBinder();

	private StaticMDCBinder() {
		// singleton
	}

	public MDCAdapter getMDCA() {
		return new LogbackMDCAdapter();
	}

	public String getMDCAdapterClassStr() {
		return LogbackMDCAdapter.class.getName();
	}
}
