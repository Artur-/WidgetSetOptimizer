package org.vaadin.artur.widgetsetoptimizer.client.widgetsetoptimizer;

import java.util.Set;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;

public interface WidgetSetOptimizerServerRpc extends ServerRpc {

	public void generateOptimizedWidgetSet(Set<String> usedConnectors);

}
