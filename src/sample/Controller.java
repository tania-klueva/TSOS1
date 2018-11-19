package sample;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Controller {
    public LineChart line1;
    public Pane pane;
    public Label l1;
    public Label l2;
    public Label l3;
    public Label l4;
    public Label l5;

    double f2(double A1, double A2, double f1, double f2, int k, double t) {
        return A1 * Math.cos(2 * Math.PI * f1 * k * t) + A2 * Math.cos(2 * Math.PI * f2 * t);
    }


    public double f1(double t, double A1, double f1) {
//        return A1*Math.cos(2*Math.PI*f1*t)+A2*Math.cos(2*Math.PI*f2*t);
        return (A1 * Math.cos(2 * Math.PI * f1 * t));
    }


    public void tab1(double A1, double f1, double[] x, double[] times) {
//        double dt = 1/(2*Math.max(f2, f1));
//        Map<Double, Double> data= new LinkedHashMap<>();
        double dt = 1 / (2.9 * f1);
        double t = 0;
        for (int i = 0; i < 1024; i++) {
            times[i] = t;
            x[i] = f1(t, A1, f1);
//            data.put(t, x[i]);
            t += dt;
        }
    }

    public void kv(double[] x) {
        for (int i = 0; i < x.length; i++) {
            x[i] = Math.round(x[i]);
        }
    }


    public void writeArrayToFile(double x[]) {
        try {
            FileWriter fw = new FileWriter("file.txt");
            for (int i = 0; i < x.length; i++) {
                fw.write(x[i] + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initialize() {

        double f1 = 400.0;
        double f2 = 200.0;
        double A = 18;
        double A2 = 23;
        double[] x = new double[1024];
        double[] y = new double[1024];
        double[] times = new double[1024];
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        series.setName("(A1 * Math.cos(2 * Math.PI * f1 * t))");
        XYChart.Series<Double, Double> series2 = new XYChart.Series<>();
        series2.setName("A1 * Math.cos(2 * Math.PI * f1 * k * t) + A2 * Math.cos(2 * Math.PI * f2 * t)");
        double v = 2 * f1;
        double dt = 1 / v;
        double t = 0;
        for (int i = 0; i < 1024; i++) {
            times[i] = t;
            x[i] = Math.round(f1(t, A, f1));
            y[i] = Math.round(f2(A, A2, f1, f2, i, t));
            t += dt;
            series.getData().add(new XYChart.Data<>(times[i], x[i]));
            series2.getData().add(new XYChart.Data<>(times[i], y[i]));
        }
        kv(x);
        writeArrayToFile(x);
        l1.setText("Максимальна частота сигналу = " + f1);
        l2.setText("Частота дискретизації = " + v);
        l3.setText("Період дискретизації = " + dt);
        l4.setText("Кількість рівнів квантування = " + "2^16");
        l5.setText("Кількість чисел у створенному сигналі = " + x.length);
        System.out.println("Тривалість = " + t + "s");
        System.out.println("Розмір = " + (1024 * 2) + "byte");

        new ZoomManager(pane, line1, series);

    }

    public XYChart.Series<Double, Double> createSeries(double[] x, double[] times) {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        for (int i = 0; i < x.length; i++) {
            series.getData().add(new XYChart.Data<>(times[i], x[i]));

        }
        return series;
    }


}
