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

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import org.lecturestudio.presenter.api.view.CreateQuizFreeTextOptionView;
import org.lecturestudio.swing.event.DefaultDocumentListener;
import org.lecturestudio.swing.util.SwingUtils;
import org.lecturestudio.swing.view.SwingView;
import org.lecturestudio.swing.view.ViewPostConstruct;
import org.lecturestudio.web.api.model.quiz.QuizOption;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@SwingView(name = "quiz-freetext-option")
public class SwingQuizFreeTextOptionView extends SwingQuizOptionView implements CreateQuizFreeTextOptionView {

	private int maxNumberOfAnswers;

	private JCheckBox correctCheckBox;

	private JTextField optionTextField;


	SwingQuizFreeTextOptionView() {
		super();
	}

	@Override
	public void focus() {
		SwingUtils.invoke(() -> optionTextField.requestFocus());
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
	public void setMaxNumberOfAnswers(final int maxNumberOfAnswers) {

		// Ensure that the maxNumberOfAnswers is a positive integer. If not, set it to 1 and throw an error message.
		try {

			// Check if the maxNumberOfAnswers is greater than 0
			if(maxNumberOfAnswers <= 0)
				throw new IllegalArgumentException("maxNumberOfAnswers must be greater than 0");

			this.maxNumberOfAnswers = maxNumberOfAnswers;

		} catch (final IllegalArgumentException e) {

			System.out.println(e.getMessage());
			this.maxNumberOfAnswers = 1;

		} // end of try-catch

	}

	@Override
	public int getMaxNumberOfAnswers() {
		return maxNumberOfAnswers;
	}

	@Override
	public void updateWordCloud() {

	}

	// TODO: Implement further!!!
	@Override
	public void renderWordCloud() {
		final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();

		// TODO: Please adjust the dimension!!!
		final Dimension dimension = new Dimension(600, 600);

		// Creates a rectangular word cloud with the specified dimension
		final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.RECTANGLE);


	}

}
