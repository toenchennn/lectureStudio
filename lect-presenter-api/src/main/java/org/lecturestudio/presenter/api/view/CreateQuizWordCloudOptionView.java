package org.lecturestudio.presenter.api.view;

/**
 * This interface represents a view for creating a quiz option in a word cloud format.
 * In this quiz style, the user can submit free text answers, which are then evaluated in real-time.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Tag_cloud">Word cloud</a>
 */
public interface CreateQuizWordCloudOptionView extends CreateQuizOptionView {

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

} // end of interface CreateQuizWordCloudOptionView