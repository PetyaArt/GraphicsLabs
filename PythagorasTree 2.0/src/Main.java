
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Scanner;

import static java.lang.Math.*;

public class Main {

    private static int depthLimit;
    private static double ALPHA;

    public static void main(String[] args) {
        System.out.print("Введите угол ALPHA: ");
        Scanner scanner = new Scanner(System.in);
        ALPHA = toRadians(scanner.nextDouble());

        System.out.print("Введите глубину: ");
        depthLimit = scanner.nextInt();

        JFrame jFrame = getFrame();
        jFrame.add(new MyComponent());
    }

    public static class MyComponent extends JComponent {
        @Override
        public void paint(Graphics g) {
            drawTree((Graphics2D) g, 450, 500, 0, 0, 100, 0, 0);
            ((Runnable) () -> {
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).run();

        }


    }

    private static void drawTree(Graphics2D g, double x, double y, int depth, double fi, double side, double prev_point_x, double prev_point_y) {

        if (depth == depthLimit)
            return;

        double dx = side * sin(fi);
        double dy = side * cos(fi);

        double x1 = x + dy;
        double y1 = y - dx;

        double x2 = x - dx + dy;
        double y2 = y - dy - dx;

        double x3 = x - dx;
        double y3 = y - dy;

        double x4 = x - dx - (side * sin(ALPHA) * sin(fi - ALPHA));
        double y4 = y - dy - (side * sin(ALPHA) * cos(fi - ALPHA));

        double x_c = (x + x1 + x2 + x3) / 4;
        double y_c = (y + y1 + y2 + y3) / 4;

        double x_t = (x2 + x3 + x4) / 3;
        double y_t = (y2 + y3 + y4) / 3;


        if (depth == 0){
            Line2D line = new Line2D.Double(x_c, y_c, x_t, y_t);
            g.setColor(Color.getHSBColor((float) random() + depth * 0.02f, 1, 1));
            g.draw(line);
            drawTree(g, x4, y4,depth + 1, fi - ALPHA, side * cos(ALPHA), x_t, y_t);
            drawTree(g, x3, y3,depth + 1, fi - ALPHA + PI / 2, side * sin(ALPHA), x_t, y_t);
        }

        if (depth >= 1) {
            Line2D line = new Line2D.Double(prev_point_x, prev_point_y, x_c, y_c);
            g.setColor(Color.getHSBColor((float) random() + depth * 0.02f, 1, 1));
            g.draw(line);
            drawTree(g, x4, y4,depth + 1, fi - ALPHA, side * cos(ALPHA), x_c, y_c);
            drawTree(g, x3, y3,depth + 1, fi - ALPHA + PI / 2, side * sin(ALPHA), x_c, y_c);
        }
    }


    private static JFrame getFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width / 2 - 500, dimension.height / 2 - 350, 1000, 700);
        return jFrame;
    }
}
