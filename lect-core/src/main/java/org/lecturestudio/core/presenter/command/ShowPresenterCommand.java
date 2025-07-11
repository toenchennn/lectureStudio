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

package org.lecturestudio.core.presenter.command;

import org.lecturestudio.core.presenter.Presenter;

/**
 * Command to show a specific presenter.
 * This class encapsulates the logic required to display the view associated with a presenter.
 *
 * @param <T> The type of presenter, which must extend Presenter<?>.
 */
public class ShowPresenterCommand<T extends Presenter<?>> {

	/** The class object representing the type of presenter to be shown. */
	private final Class<T> cls;


	/**
	 * Create a new {@link ShowPresenterCommand} with the specified presenter class.
	 *
	 * @param cls The presenter class.
	 */
	public ShowPresenterCommand(Class<T> cls) {
		this.cls = cls;
	}

	/**
	 * Get the presenter class.
	 *
	 * @return The presenter class.
	 */
	public Class<T> getPresenterClass() {
		return cls;
	}

	/**
	 * Execute the command on the specified presenter.
	 * May be empty if no extra commands are required to display the corresponding view.
	 *
	 * @param presenter The presenter.
	 */
	public void execute(T presenter) {

	}

}
