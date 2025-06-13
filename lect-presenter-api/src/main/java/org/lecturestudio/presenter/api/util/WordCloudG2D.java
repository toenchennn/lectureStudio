package org.lecturestudio.presenter.api.util;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.Word;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.palette.ColorPalette;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordCloudG2D extends WordCloud {

	private final Graphics2D graphics2D;


	public WordCloudG2D(final Dimension dimension, final CollisionMode collisionMode, Graphics2D graphics2D) {
		super(dimension, collisionMode);

		this.graphics2D = graphics2D;
	}

	@Override
	public void build(final List<WordFrequency> wordFrequencies) {
		Collections.sort(wordFrequencies);

		wordPlacer.reset();
		skipped.clear();

		// the background masks all none usable pixels and we can only check this raster
		background.mask(backgroundCollidable);

		int currentWord = 1;
		for (final Word word : buildWords(wordFrequencies, this.colorPalette)) {
			final Point point = wordStartStrategy.getStartingPoint(dimension, word);
			final boolean placed = place(word, point);

			if (placed) {
				System.out.println("placed: " + word.getWord() + " (" + currentWord + "/" + wordFrequencies.size() + ")");
//				if (LOGGER.isInfoEnabled()) {
//					LOGGER.info("placed: {} ({}/{})", word.getWord(), currentWord, wordFrequencies.size());
//				}
			}
			else {
				System.out.println("skipped: " + word.getWord() + " (" + currentWord + "/" + wordFrequencies.size() + ")");
//				if (LOGGER.isInfoEnabled()) {
//					LOGGER.info("skipped: {} ({}/{})", word.getWord(), currentWord, wordFrequencies.size());
//				}
				skipped.add(word);
			}
			currentWord++;
		}

		drawForegroundToBackground();
	}

	@Override
	protected boolean place(final Word word, final Point start) {
		final int maxRadius = computeRadius(dimension, start);
		final Point position = word.getPosition();

		for (int r = 0; r < maxRadius; r += 2) {
			for (int x = Math.max(-start.x, -r); x <= Math.min(r, dimension.width - start.x - 1); x++) {
				position.x = start.x + x;

				final int offset = (int) Math.sqrt(r * r - x * x);

				// try positive root
				position.y = start.y + offset;
				if (position.y >= 0 && position.y < dimension.height && canPlace(word)) {
					collisionRaster.mask(word.getCollisionRaster(), position);
					graphics2D.drawImage(word.getBufferedImage(), position.x, position.y, null);
					return true;
				}

				// try negative root (if offset != 0)
				position.y = start.y - offset;
				if (offset != 0 && position.y >= 0 && position.y < dimension.height && canPlace(word)) {
					collisionRaster.mask(word.getCollisionRaster(), position);
					graphics2D.drawImage(word.getBufferedImage(), position.x, position.y, null);
					return true;
				}
			}
		}

		return false;
	}

	@Override
	protected java.util.List<Word> buildWords(final java.util.List<WordFrequency> wordFrequencies, final ColorPalette colorPalette) {
		final int maxFrequency = maxFrequency(wordFrequencies);

		final List<Word> words = new ArrayList<>();
		for (final WordFrequency wordFrequency : wordFrequencies) {
			// the text shouldn't be empty, however, in case of bad normalizers/tokenizers, this may happen
			if (!wordFrequency.getWord().isEmpty()) {
				words.add(buildWord(wordFrequency, maxFrequency, colorPalette));
			}
		}
		return words;
	}

	private Word buildWord(final WordFrequency wordFrequency, final int maxFrequency, final ColorPalette colorPalette) {
		// set the rendering hint here to ensure the font metrics are correct
		graphics2D.setRenderingHints(Word.getRenderingHints());

		final int frequency = wordFrequency.getFrequency();
		final float fontHeight = this.fontScalar.scale(frequency, 0, maxFrequency);
		final Font font = (wordFrequency.hasFont() ? wordFrequency.getFont() : kumoFont).getFont().deriveFont(fontHeight);
		final FontMetrics fontMetrics = graphics2D.getFontMetrics(font);

		final double theta = angleGenerator.randomNext();
		final Word word = new Word(wordFrequency.getWord(), colorPalette.next(), fontMetrics, this.collisionChecker, theta);

		if (padding > 0) {
			padder.pad(word, padding);
		}
		return word;
	}

	private boolean canPlace(final Word word) {
		final Point position = word.getPosition();
		final Dimension dimensionOfWord = word.getDimension();

		// are we inside the background?
		if (position.y < 0 || position.y + dimensionOfWord.height > dimension.height) {
			return false;
		}
		else if (position.x < 0 || position.x + dimensionOfWord.width > dimension.width) {
			return false;
		}

		switch (collisionMode) {
			case RECTANGLE:
				return !backgroundCollidable.collide(word) // is there a collision with the background shape?
						&& wordPlacer.place(word); // is there a collision with the existing words?
			case PIXEL_PERFECT:
				return !backgroundCollidable.collide(word); // is there a collision with the background shape?
		}
		return false;
	}

	private static int maxFrequency(final List<WordFrequency> wordFrequencies) {
		if (wordFrequencies.isEmpty()) {
			return 1;
		}

		return wordFrequencies.get(0).getFrequency();
	}

	private static int computeRadius(final Dimension dimension, final Point start) {
		final int maxDistanceX = Math.max(start.x, dimension.width - start.x) + 1;
		final int maxDistanceY = Math.max(start.y, dimension.height - start.y) + 1;

		// we use the pythagorean theorem to determinate the maximum radius
		return (int) Math.ceil(Math.sqrt(maxDistanceX * maxDistanceX + maxDistanceY * maxDistanceY));
	}
}
