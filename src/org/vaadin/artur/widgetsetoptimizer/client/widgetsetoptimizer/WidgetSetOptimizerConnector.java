package org.vaadin.artur.widgetsetoptimizer.client.widgetsetoptimizer;

import java.util.HashSet;
import java.util.Set;

import org.vaadin.artur.widgetsetoptimizer.WidgetSetOptimizer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.vaadin.client.ApplicationConfiguration;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.VConsole;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.ui.UnknownComponentConnector;
import com.vaadin.client.ui.VNotification;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.Connect;

@Connect(WidgetSetOptimizer.class)
public class WidgetSetOptimizerConnector extends
		AbstractDebugConsoleExtensionConnector {

	WidgetSetOptimizerServerRpc rpc = RpcProxy.create(
			WidgetSetOptimizerServerRpc.class, this);

	@Override
	protected void extend(ServerConnector target) {
		addDebugConsoleButton("SU", "Show used connectors", new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				VConsole.log("Used connectors:");
				VConsole.log("================");
				for (String connectorName : getUsedConnectorNames()) {
					VConsole.log(connectorName);
				}
				VConsole.log("================");
			}
		});
		addDebugConsoleButton("OWS", "Generate optimized widget set",
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent arg0) {

						getRpcProxy(WidgetSetOptimizerServerRpc.class)
								.generateOptimizedWidgetSet(
										getUsedConnectorNames());
						VNotification n = GWT.create(VNotification.class);
						n.setOwner(getConnection().getUIConnector().getWidget());
						n.show("Optimized widgetset configuration files output to server console",
								Position.MIDDLE_CENTER,
								VNotification.STYLE_SYSTEM);
					}
				});
	}

	protected Set<String> getUsedConnectorNames() {
		int tag = 0;
		Set<String> usedConnectors = new HashSet<String>();
		ApplicationConfiguration configuration = getConnection()
				.getConfiguration();
		while (true) {
			String serverSideClass = configuration
					.getServerSideClassNameForTag(tag);
			if (serverSideClass == null)
				break;
			Class<? extends ServerConnector> connectorClass = getConnection()
					.getConfiguration().getConnectorClassByEncodedTag(tag);
			if (connectorClass == null)
				break;

			if (connectorClass != WidgetSetOptimizerConnector.class
					&& connectorClass != UnknownComponentConnector.class)
				usedConnectors.add(connectorClass.getName());
			tag++;
			if (tag > 10000) {
				// Sanity check
				VConsole.error("Search for used connector classes was forcefully terminated");
				break;
			}
		}
		return usedConnectors;
	}

}
