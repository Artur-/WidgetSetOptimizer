package org.vaadin.artur.widgetsetoptimizer.test;

import org.vaadin.artur.widgetsetoptimizer.WidgetSetOptimizer;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Test class for the widget set optimizer
 */
public class WidgetSetOptimizerTest extends UI {

	@Override
	protected void init(VaadinRequest request) {
		new WidgetSetOptimizer().extend(this);

		Panel p = new Panel("Please log in");
		p.setSizeUndefined();
		setContent(p);

		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		p.setContent(layout);

		layout.addComponent(new TextField("Username"));
		layout.addComponent(new PasswordField("Password"));

		Button login = new Button("Login");
		login.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Thank you for logging in"));
			}
		});
		Button goHome = new Button("Go home");
		goHome.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new NativeButton("Went home"));
			}
		});
		HorizontalLayout buttonLayout = new HorizontalLayout(login, goHome);
		layout.addComponent(buttonLayout);

	}

}