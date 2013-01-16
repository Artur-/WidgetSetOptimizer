package org.vaadin.artur.widgetsetoptimizer.client.widgetsetoptimizer;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.VDebugConsole;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.ui.ui.UIConnector;

public abstract class AbstractDebugConsoleExtensionConnector extends
		AbstractExtensionConnector {

	protected void addToDebugConsole(Widget widget) {
		UIConnector ui = (UIConnector) getParent();
		VDebugConsole debugConsole = getDebugConsole();
		if (debugConsole == null) {
			return;
		}

		// Would be a lot nicer if there was API for adding functionality to the
		// debug console..
		FlowPanel panel = ((FlowPanel) debugConsole.getWidget());
		HorizontalPanel actions = (HorizontalPanel) panel.getWidget(0);
		actions.insert(widget, 5);
	}

	protected VDebugConsole getDebugConsole() {
		// Would be a lot nicer if there was API for getting a reference to the
		// debug console...
		for (Widget w : RootPanel.get()) {
			if (w instanceof VDebugConsole) {
				return (VDebugConsole) w;
			}
		}
		return null;
	}

	protected void addDebugConsoleButton(String caption, String tooltip,
			ClickHandler clickHandler) {
		Button b = new Button();
		b.setText(caption);
		b.setTitle(tooltip);
		b.addClickHandler(clickHandler);
		addToDebugConsole(b);
	}

}
