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

package org.lecturestudio.core.view;

import org.lecturestudio.core.model.Page;

/**
 * Interface for slide presentation views that can display pages.
 * Extends the base presentation view capabilities with specific page handling.
 *
 * @author Alex Andres
 */
public interface SlidePresentationView extends PresentationView {

	/**
	 * Sets the current page to be displayed in the presentation view.
	 *
	 * @param page The page to be displayed.
	 */
	void setPage(Page page);

	/**
	 * Sets the current page to be displayed with specific presentation parameters.
	 *
	 * @param page      The page to be displayed.
	 * @param parameter The presentation parameters to apply to the page.
	 */
	void setPage(Page page, PresentationParameter parameter);

}
