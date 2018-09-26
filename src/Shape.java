import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public abstract class Shape implements Serializable{

    protected int x1,y1,x2,y2;//绘制图形的坐标
    protected Color color;//画笔颜色
    protected int width;//画笔粗细

    public abstract void Draw(Graphics2D g);

}
