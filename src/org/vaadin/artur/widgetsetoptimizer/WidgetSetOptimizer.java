package org.vaadin.artur.widgetsetoptimizer;

import java.util.Set;

import org.vaadin.artur.widgetsetoptimizer.client.widgetsetoptimizer.WidgetSetOptimizerServerRpc;

import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.UI;

public class WidgetSetOptimizer extends AbstractExtension {

	private WidgetSetOptimizerServerRpc rpc = new WidgetSetOptimizerServerRpc() {

		@Override
		public void generateOptimizedWidgetSet(Set<String> usedConnectors) {
			System.out
					.println("=================================================");
			System.out
					.println("Add the following to your widgetset.gwt.xml file:");
			System.out
					.println("=================================================");
			System.out.println();
			System.out.println();
			System.out
					.println("<generate-with class=\"OptimizedConnectorBundleLoaderFactory\">");
			System.out
					.println("	<when-type-assignable class=\"com.vaadin.client.metadata.ConnectorBundleLoader\" />");
			System.out.println("</generate-with>");
			System.out.println();
			System.out
					.println("=================================================");

			System.out.println("Add the following java file to your project");
			System.out
					.println("=================================================");
			System.out.println();
			System.out.println();
			System.out.println("import java.util.HashSet;");
			System.out.println("import java.util.Set;");
			System.out.println("");
			System.out
					.println("import com.google.gwt.core.ext.typeinfo.JClassType;");
			System.out.println("import com.vaadin.client.ui.ui.UIConnector;");
			System.out
					.println("import com.vaadin.server.widgetsetutils.ConnectorBundleLoaderFactory;");
			System.out
					.println("import com.vaadin.shared.ui.Connect.LoadStyle;");
			System.out.println("");
			System.out
					.println("public class OptimizedConnectorBundleLoaderFactory extends");
			System.out.println("            ConnectorBundleLoaderFactory {");
			System.out
					.println("    private Set<String> eagerConnectors = new HashSet<String>();");
			System.out.println("    {");
			for (String s : usedConnectors) {
				System.out.println("            eagerConnectors.add(" + s
						+ ".class.getName());");
			}
			System.out.println("    }");
			System.out.println("");
			System.out.println("    @Override");
			System.out
					.println("    protected LoadStyle getLoadStyle(JClassType connectorType) {");
			System.out
					.println("            if (eagerConnectors.contains(connectorType.getQualifiedBinaryName())) {");
			System.out.println("                    return LoadStyle.EAGER;");
			System.out.println("            } else {");
			System.out
					.println("                    // Loads all other connectors immediately after the initial view has");
			System.out.println("                    // been rendered");
			System.out
					.println("                    return LoadStyle.DEFERRED;");
			System.out.println("            }");
			System.out.println("    }");
			System.out.println("}");

			System.out
					.println("=================================================");

			System.out.println("Recompile the widget set");
			System.out
					.println("=================================================");
			System.out.println();
		}
	};

	public void extend(UI target) {
		super.extend(target);
	};

	public WidgetSetOptimizer() {
		registerRpc(rpc);
	}

}
