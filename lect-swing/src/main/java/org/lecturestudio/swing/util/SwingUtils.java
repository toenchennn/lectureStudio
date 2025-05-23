/*
 * Copyright (C) 2020 TU Darmstadt, Department of Computer Science,
 * Embedded Systems and Applications Group.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.lecturestudio.swing.util;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.AbstractButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import org.lecturestudio.core.beans.ObjectProperty;
import org.lecturestudio.core.view.Action;
import org.lecturestudio.core.view.ConsumerAction;
import org.lecturestudio.core.view.View;
import org.lecturestudio.swing.beans.Binding;
import org.lecturestudio.swing.beans.ColorButtonProperty;
import org.lecturestudio.swing.beans.ComboBoxProperty;
import org.lecturestudio.swing.beans.SliderProperty;
import org.lecturestudio.swing.beans.ObjectBinding;
import org.lecturestudio.swing.beans.TableProperty;
import org.lecturestudio.swing.beans.TextFieldProperty;
import org.lecturestudio.swing.beans.ToggleButtonProperty;
import org.lecturestudio.swing.beans.ToggleMenuProperty;
import org.lecturestudio.swing.components.ColorChooserButton;

public final class SwingUtils {

	public static boolean isComponent(View view) {
		if (isNull(view)) {
			return false;
		}

		return Component.class.isAssignableFrom(view.getClass());
	}

	public static void bindAction(AbstractButton button, Action action) {
		requireNonNull(button);
		requireNonNull(action);

		button.addActionListener(e -> action.execute());
	}

	public static void bindAction(JCheckBoxMenuItem menuItem, ConsumerAction<Boolean> action) {
		requireNonNull(menuItem);
		requireNonNull(action);

		menuItem.addActionListener(event -> action.execute(menuItem.isSelected()));
	}

	public static void bindAction(JToggleButton toggle, ConsumerAction<Boolean> action) {
		requireNonNull(toggle);
		requireNonNull(action);

		toggle.addActionListener(event -> action.execute(toggle.isSelected()));
	}

	public static void bindAction(JTextField textField, Action action) {
		requireNonNull(textField);
		requireNonNull(action);

		textField.addActionListener(e -> action.execute());
	}

	public static <E> Binding bindBidirectional(ObjectProperty<E> source, ObjectProperty<E> target) {
		return new ObjectBinding<>(source, target);
	}

	public static Binding bindBidirectional(ColorChooserButton chooserButton, ObjectProperty<Color> property) {
		return new ObjectBinding<>(property, new ColorButtonProperty(chooserButton));
	}

	public static Binding bindBidirectional(JCheckBoxMenuItem menuItem, ObjectProperty<Boolean> property) {
		return new ObjectBinding<>(property, new ToggleMenuProperty(menuItem));
	}

	public static Binding bindBidirectional(JToggleButton toggleButton, ObjectProperty<Boolean> property) {
		return new ObjectBinding<>(property, new ToggleButtonProperty(toggleButton));
	}

	public static <E> Binding bindBidirectional(JComboBox<E> comboBox, ObjectProperty<E> property) {
		return new ObjectBinding<>(property, new ComboBoxProperty<>(comboBox));
	}

	public static Binding bindBidirectional(JTextComponent textField, ObjectProperty<String> property) {
		return new ObjectBinding<>(property, new TextFieldProperty(textField));
	}

	public static Binding bindBidirectional(JSlider slider, ObjectProperty<Integer> property) {
		return new ObjectBinding<>(property, new SliderProperty(slider));
	}

	public static <E> Binding bindBidirectional(JTable table, ObjectProperty<E> property) {
		return new ObjectBinding<>(property, new TableProperty<>(table));
	}

	/**
	 * Run this Runnable in the Swing Event Dispatching Thread (EDT). This
	 * method can be called whether or not the current thread is the EDT.
	 *
	 * @param runnable the code to be executed in the EDT.
	 */
	public static void invoke(Runnable runnable) {
		if (isNull(runnable)) {
			return;
		}

		try {
			if (SwingUtilities.isEventDispatchThread()) {
				runnable.run();
			}
			else {
				SwingUtilities.invokeLater(runnable);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setEnabled(boolean enabled, JComponent... components) {
		for (JComponent c : components) {
			setEnabled(c, enabled);
		}
	}

	public static void setEnabled(JComponent component, boolean enabled) {
		component.setEnabled(enabled);

		Component[] components = component.getComponents();

		for (Component c : components) {
			if (c instanceof JComponent) {
				setEnabled((JComponent) c, enabled);
			}

			c.setEnabled(enabled);
		}
	}

	public static List<String> getEmbeddedFontFamilies() {
		String[] supportedFamilies = {
				"Open Sans",
				"Roboto Mono"
		};
		List<String> families = new ArrayList<>();
		List<String> fontFamilies = List.of(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());

		for (String family : supportedFamilies) {
			Optional<String> opt = fontFamilies.stream().filter(s -> s.equals(family)).findFirst();

			if (opt.isPresent()) {
				families.add(family);
			}
		}

		return families;
	}
}
