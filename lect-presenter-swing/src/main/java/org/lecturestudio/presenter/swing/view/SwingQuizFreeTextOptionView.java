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

package org.lecturestudio.presenter.swing.view;

import org.lecturestudio.presenter.api.view.CreateQuizNumericOptionView;
import org.lecturestudio.presenter.api.view.CreateQuizWordCloudOptionView;
import org.lecturestudio.swing.event.DefaultDocumentListener;
import org.lecturestudio.swing.util.SwingUtils;
import org.lecturestudio.swing.view.SwingView;
import org.lecturestudio.swing.view.ViewPostConstruct;
import org.lecturestudio.web.api.model.quiz.QuizOption;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.ParseException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@SwingView(name = "quiz-freetext-option")
public class SwingQuizFreeTextOptionView extends SwingQuizOptionView implements CreateQuizWordCloudOptionView {


	private JCheckBox correctCheckBox;

	private JTextField optionTextField;


	SwingQuizFreeTextOptionView() {
		super();
	}

	@Override
	public void focus() {
		SwingUtils.invoke(() -> {
			optionTextField.requestFocus();
		});
	}

	@Override
	public QuizOption getOption() {
		return new QuizOption(optionTextField.getText(), correctCheckBox.isSelected());
	}

	@Override
	public void setOption(QuizOption option) {
		SwingUtils.invoke(() -> {
			optionTextField.setText(option.optionText());
			correctCheckBox.setSelected(option.correct());
		});
	}

	@Override
	void setOptionTooltip(String tooltip) {
		optionTextField.setToolTipText(tooltip);
	}


	@ViewPostConstruct
	private void initialize() {
		if (isNull(optionTextField)) {
			return;
		}
		if (nonNull(getButtons())) {
			add(getButtons(), BorderLayout.EAST);
		}

		optionTextField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				upDownKeyHandler(e);
			}
		});
//		maxTextField.addKeyListener(new KeyAdapter() {
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//				enterKeyHandler(e);
//			}
//		});
//		maxTextField.addKeyListener(new KeyAdapter() {
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//				tabKeyHandler(e);
//			}
//		});

		DefaultDocumentListener docListener = new DefaultDocumentListener(super::fireChange);

		optionTextField.getDocument().addDocumentListener(docListener);
//		minTextField.getDocument().addDocumentListener(docListener);
//		maxTextField.getDocument().addDocumentListener(docListener);
	}

	@Override
	public void setMaxNumberOfAnswers(int maxNumberOfAnswers) {

	}

	@Override
	public int getMaxNumberOfAnswers() {
		return 0;
	}
}
