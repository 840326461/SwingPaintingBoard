import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;


public class DrawListener implements MouseListener, MouseMotionListener {

    private Graphics2D graphics;
    private int x1, y1, x2, y2, ox, oy, x3, y3;
    private DrawType command;
    private ArrayList<Shape> list;
    private boolean flag;
    private int strokesNum;

    private static final Stroke s1 = new BasicStroke(1);
    private static final int lineWidth = 2;

    public DrawListener(Graphics graphics, ArrayList<Shape> list) {
        this.graphics = (Graphics2D) graphics;
        this.list = list;
        flag = true;
        command = DrawType.curve;
        strokesNum = 0;
    }

    public void mousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();

        if (command.equals(DrawType.line)) {
            Shape line = new Line(x1, y1, x2, y2, graphics.getColor(), lineWidth);
            line.Draw(graphics);
            list.add(line);
        } else if (command.equals(DrawType.polygon)) {
            //第一次画直线，设置标志
            if (flag) {
                Shape line = new Line(x1, y1, x2, y2, graphics.getColor(), lineWidth);
                line.Draw(graphics);
                list.add(line);
                flag = false;
                x3 = x2;
                y3 = y2;
                ox = x1;
                oy = y1;
            } else {
                Shape line = new Line(x3, y3, x2, y2, graphics.getColor(), lineWidth);
                line.Draw(graphics);
                list.add(line);
                x3 = x2;
                y3 = y2;
            }
        }
        strokesNum++;
    }

    public void mouseClicked(MouseEvent e) {
        int count = e.getClickCount();
        if (count == 2 && command.equals(DrawType.polygon)) {
            Shape line = new Line(ox, oy, x2, y2, graphics.getColor(), lineWidth);
            line.Draw(graphics);
            list.add(line);
            flag = true;
        }
    }

    public void mouseEntered(MouseEvent e) {
//        graphics.setColor(color);
//        graphics.setStroke(s1);
    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        if (command.equals(DrawType.curve)) {
            Shape line = new Line(x1, y1, x, y, graphics.getColor(), lineWidth);
            line.Draw(graphics);
            list.add(line);
            x1 = x;
            y1 = y;
        }
    }

    public void mouseMoved(MouseEvent e) {

    }

    public void setCommand(DrawType command) {
        this.command = command;
    }

    public int getStrokesNum() {
        return strokesNum;
    }

    public void setStrokesNum(int strokesNum) {
        this.strokesNum = strokesNum;
    }

    public void reset() {
        strokesNum = 0;
    }
}
