import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

public class ItemListener implements ActionListener {

    private MainFrame mainFrame;
    private JPanel paint;
    private ArrayList<Shape> list;

    public ItemListener(MainFrame mainFrame, JPanel paint, ArrayList<Shape> list) {
        this.mainFrame = mainFrame;
        this.paint = paint;
        this.list = list;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("new")) {
            int value = JOptionPane.showConfirmDialog(null, "是否需要保存当前文件？", "提示信息", JOptionPane.YES_NO_OPTION);
            if (value == 0) {
                saveFile();
            }
            mainFrame.reset();
        } else if (command.equals("reset")) {
            mainFrame.reset();
        } else if (command.equals("open")) {
            //清空容器里面的东西
            mainFrame.reset();
            try {
                //弹出选择对话框，选择需要读入的文件
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new DrawBoardFilter());
                chooser.showOpenDialog(null);
                File file = chooser.getSelectedFile();
                //如果为选中文件
                if (file == null) {
                    JOptionPane.showMessageDialog(null, "没有选择文件");
                } else {
                    //选中了相应的文件，则柑橘选中的文件创建对象输入流
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    //将读出来的对象转换成父类对象的容器进行接收
                    ArrayList<Shape> list = (ArrayList<Shape>) ois.readObject();
                    int strokesNum = (int) ois.readObject();
                    //遍历容器里面的具体对象，将取出来的对象保存到容器里面
                    mainFrame.reset();
                    this.list.addAll(list);
                    paint.repaint();
                    mainFrame.setStrokesNum(strokesNum);
                    identify();
                    ois.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (command.equals("save")) {
            saveFile();
        } else if (command.equals("exit")) {
            int value = JOptionPane.showConfirmDialog(null, "是否需要保存当前文件？", "提示信息", JOptionPane.YES_NO_OPTION);
            if (value == 0) {
                saveFile();
            }
            System.exit(0);
        } else if (command.equals("identify")) {
            identify();
        }
    }

    private void saveFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new DrawBoardFilter());
        chooser.showSaveDialog(null);
        File file = new File(chooser.getSelectedFile().getPath() + ".drawboard");

        if (file == null) {
            JOptionPane.showMessageDialog(null, "没有选择文件");
        } else {

            try {
                FileOutputStream fis = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fis);
                oos.writeObject(list);
                oos.writeObject(mainFrame.getStrokesNum());
                JOptionPane.showMessageDialog(null, "保存成功！");
                oos.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void identify() {
        int strokes = mainFrame.getStrokesNum();
        String result;
        switch (strokes) {
            case 0:
                result = "画板为空";
                break;
            case 1:
                result = "直线";
                break;
            case 2:
                result = "非封闭图形";
                break;
            case 3:
                result = "三角形";
                break;
            case 4:
                result = "矩形";
                break;
            case 5:
                result = "五边形";
                break;
            case 6:
                result = "六边形";
                break;
            default:
                result = "多边形";
                break;
        }
        mainFrame.setIdentifyResult("识别结果 : " + result);
    }

    public void reset() {
        list.removeAll(list);
        paint.repaint();
    }

}

