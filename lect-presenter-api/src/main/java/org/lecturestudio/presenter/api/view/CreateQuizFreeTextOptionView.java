package org.lecturestudio.presenter.api.view;

/**
 * This interface represents a view for creating a quiz style with free-text answers.
 * In this quiz style, the user can submit free text answers, which are then evaluated in real-time.
 * Optionally, the answers can be visualized as a word cloud, where the size of each word corresponds to its frequency
 * in the answers submitted by users.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Tag_cloud">Word cloud</a>
 */
public interface CreateQuizFreeTextOptionView extends CreateQuizOptionView {

    /**
     * This method sets the maximum number of answers that a user can submit in each quiz.
     *
     * @param maxNumberOfAnswers the maximum number of answers that a user can submit in each quiz. The value must
     *                           be greater than 0.
     */
    void setMaxNumberOfAnswers(final int maxNumberOfAnswers);

    /**
     * This method returns the maximum number of answers that a user can submit in each quiz.
     *
     * @return the maximum number of answers that a user can submit in each quiz.
     */
    int getMaxNumberOfAnswers();

    /**
     * This method updates the word cloud visualization based on the answers submitted by users.
     * Each submitted answer is processed in FIFO (First In, First Out) order,
     * meaning that the first answer arriving is processed first.
     */
    void updateWordCloud();

    /**
     * This method renders the word cloud visualization.
     */
    void renderWordCloud();

} // end of interface