package ca.uqac.liara;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.ID3Custom;
import weka.core.Debug;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;

import javax.swing.*;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * Created by Baptiste on 10/5/2016.
 */
public class ID3Runner implements Runnable {
	private ID3Panel panel;
	private ID3Custom id3;

	public ID3Runner(ID3Panel pane) {
		this.panel = pane;
		this.id3 = new ID3Custom(this.panel.getAlpha());
	}

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	@Override
	public void run() {
		try {

			if (!this.panel.validateFields()) {
				JOptionPane.showMessageDialog(this.panel.getParent(), "Fields does not contains correct value.\n0 < "
						+ "alpha and alpha != 1\n0 < percentage_split < 100", "Fields incorrect", JOptionPane
						.ERROR_MESSAGE);
				return;
			}

			this.panel.resetResultArea();
			System.out.println("Starting ID3 Runner");
			this.panel.writeResult("Starting ID3 Runner" + "\n");

			System.out.println("Loading File");
			this.panel.writeResult("Loading File" + "\n");
			ArffLoader loader = new ArffLoader();
			loader.setFile(new File(this.panel.getDatasetPath()));
			Instances data = loader.getDataSet();
			data.setClassIndex(data.numAttributes() - 1);

			System.out.println("Discretizing data");
			this.panel.writeResult("Discretizing data" + "\n");
			Discretize filter = new Discretize();
			filter.setInputFormat(data);
			data = Filter.useFilter(data, filter);

			System.out.println("Evaluating model");
			this.panel.writeResult("Evaluating model\n");

			Evaluation eval;

			if (this.panel.isCrossValidation()) {
				System.out.println(this.panel.getFoldsNumber());
				Random rand = new Random(1);
				eval = new Evaluation(data);
				eval.crossValidateModel(this.id3, data, this.panel.getFoldsNumber(), rand);
			} else {
				data.randomize(new Debug.Random(1));
				int trainingSize = (int) Math.round(data.numInstances() * this.panel.getPercentageSplitNumber() /
						100d);
				int testingSize = data.numInstances() - trainingSize;

				Instances trainInstances = new Instances(data, 0, trainingSize);
				Instances testingInstances = new Instances(data, trainingSize, testingSize);

				this.id3.buildClassifier(trainInstances);

				if (testingInstances.classIndex() == -1) {
					testingInstances.setClassIndex(testingInstances.numAttributes() - 1);
				}
				eval = new Evaluation(trainInstances);
				eval.evaluateModel(this.id3, testingInstances);
			}

			this.panel.writeResult("\n====================================\n");

			this.panel.writeResult(this.id3.toString() + "\n");

			this.panel.writeResult("\n====================================\n");

			this.panel.writeResult("Detection rate : " + round(eval.pctCorrect(), 2) + "\n");
			this.panel.writeResult("Error rate : " + round(eval.pctCorrect(), 2) + "\n");

			this.panel.writeResult(eval.toMatrixString(""));

            /*System.out.println(eval.toSummaryString());
			this.panel.writeResult(eval.toSummaryString()+"\n");
            System.out.println(eval.toClassDetailsString());
            this.panel.writeResult(eval.toClassDetailsString()+"\n");*/
		} catch (Exception e) {
			System.err.println(e);
		}

	}
}

