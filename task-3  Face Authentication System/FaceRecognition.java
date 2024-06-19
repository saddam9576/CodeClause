// FaceRecognition.java
import org.opencv.core.*;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class FaceRecognition {
    private LBPHFaceRecognizer recognizer;

    public FaceRecognition() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        recognizer = LBPHFaceRecognizer.create();
    }

    public void train(List<Mat> images, List<Integer> labels) {
        recognizer.train(images, new MatOfInt(Converters.vector_int_to_Mat(labels)));
    }

    public int predict(Mat face) {
        int[] label = new int[1];
        double[] confidence = new double[1];
        recognizer.predict(face, label, confidence);
        return label[0];
    }

    public Mat preprocessImage(String imagePath) {
        Mat image = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_GRAYSCALE);
        Imgproc.resize(image, image, new Size(150, 150));
        return image;
    }
}

