import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

public class MainFrame extends JFrame {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel functionAreaPanel;
    private Graphics2D graphics;
    private DrawListener drawListener;
    private ItemListener operandListener;
    private ToolMenuListener toolMenuListener;
    private ArrayList<Shape> list = new ArrayList<>();
    private JLabel result;


    public MainFrame() {
        init();
    }

    private void init() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        Font font = new Font("微软雅黑", Font.PLAIN, 25);        //字号：像素=3：4
        Border border2 = BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK);

        mainPanel = new JPanel() {
            public void paint(Graphics g1) {
                graphics = (Graphics2D) g1;                //画船体
                super.paint(graphics);
                for (int i = 0; i < list.size(); i++) {
                    Shape shape = (Shape) list.get(i);
                    shape.Draw(graphics);
                }
            }
        };

        functionAreaPanel = new JPanel();

        // 创建窗体
        frame = new JFrame();
        int frameWidth = 960;
        int frameHeight = 720;
        frame.setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight+10);
        frame.setLayout(null);
        String title = "Kerry Wang's Construction homework";
        frame.setTitle(title);
        //设置JPanel
        int mainPanelHeight = (frameHeight / 6) * 5;
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, frameWidth, mainPanelHeight);
        mainPanel.setBorder(border2);

        int functionAreaHeight = frameHeight - mainPanelHeight;
        functionAreaPanel.setLayout(null);
        functionAreaPanel.setBounds(0, mainPanelHeight, frameWidth, functionAreaHeight+100);

        JMenuBar menuBar = new JMenuBar();
        BoxLayout bl = new BoxLayout(menuBar, BoxLayout.X_AXIS);
        menuBar.setLayout(bl);
        menuBar.setBackground(Color.lightGray);
        MenuListener menuListener = new MenuListener();
        operandListener = new ItemListener(this, mainPanel, list);
        //File菜单
        JMenu fileMenu = new JMenu("文件");
        fileMenu.setFont(font);
        JMenuItem newMenuItem = new JMenuItem("新建");
        newMenuItem.setFont(font);
        newMenuItem.setActionCommand("new");
        fileMenu.add(newMenuItem);
        JMenuItem openMenuItem = new JMenuItem("打开");
        openMenuItem.setFont(font);
        openMenuItem.setActionCommand("open");
        fileMenu.add(openMenuItem);
        JMenuItem saveMenuItem = new JMenuItem("保存");
        saveMenuItem.setFont(font);
        saveMenuItem.setActionCommand("save");
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        JMenuItem exitMenuItem = new JMenuItem("关闭");
        exitMenuItem.setFont(font);
        exitMenuItem.setActionCommand("exit");
        fileMenu.add(exitMenuItem);
        fileMenu.addMouseListener(menuListener);
        menuBar.add(fileMenu);

        //Edit菜单
        JMenu EditMenu = new JMenu("编辑");
        EditMenu.setFont(font);
        JMenuItem resetMenuItem = new JMenuItem("重置");
        resetMenuItem.setFont(font);
        resetMenuItem.setActionCommand("reset");
        resetMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl a"));
        EditMenu.add(resetMenuItem);
        JMenuItem identificationMenuItem = new JMenuItem("识别");
        identificationMenuItem.setFont(font);
        identificationMenuItem.setActionCommand("identify");
        identificationMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl b"));
        EditMenu.add(identificationMenuItem);
        EditMenu.addMouseListener(menuListener);
        menuBar.add(EditMenu);

        //tools菜单
        toolMenuListener = new ToolMenuListener(this);
        JMenu toolMenu = new JMenu("画笔");
        toolMenu.setFont(font);
        JMenuItem lineMenuItem = new JMenuItem("直线");
        lineMenuItem.setFont(font);
        lineMenuItem.setActionCommand("line");
        toolMenu.add(lineMenuItem);
        JMenuItem polygonMenuItem = new JMenuItem("多边形");
        polygonMenuItem.setFont(font);
        polygonMenuItem.setActionCommand("polygon");
        toolMenu.add(polygonMenuItem);
        JMenuItem curveMenuItem = new JMenuItem("铅笔");
        curveMenuItem.setFont(font);
        curveMenuItem.setActionCommand("curve");
        toolMenu.add(curveMenuItem);
        toolMenu.addMouseListener(menuListener);
        menuBar.add(toolMenu);

        frame.setJMenuBar(menuBar);

        JButton resetButton = new JButton("重置");
        resetButton.setBounds(200, 5, 100, 40);
        resetButton.setFont(font);
        resetButton.setActionCommand("reset");
        JButton identifyButton = new JButton("识别");
        identifyButton.setBounds(400, 5, 100, 40);
        identifyButton.setFont(font);
        identifyButton.setActionCommand("identify");
        result = new JLabel("识别结果 :");
        result.setFont(font);
        result.setBounds(600, 5, 500, 40);
        functionAreaPanel.add(resetButton);
        functionAreaPanel.add(identifyButton);
        functionAreaPanel.add(result);


        newMenuItem.addActionListener(operandListener);
        openMenuItem.addActionListener(operandListener);
        saveMenuItem.addActionListener(operandListener);
        exitMenuItem.addActionListener(operandListener);
        resetMenuItem.addActionListener(operandListener);
        identificationMenuItem.addActionListener(operandListener);
        resetButton.addActionListener(operandListener);
        identifyButton.addActionListener(operandListener);
        lineMenuItem.addActionListener(toolMenuListener);
        polygonMenuItem.addActionListener(toolMenuListener);
        curveMenuItem.addActionListener(toolMenuListener);

        frame.setResizable(false);
        frame.add(mainPanel);
        mainPanel.setBackground(Color.white);
        frame.add(functionAreaPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        graphics = (Graphics2D) mainPanel.getGraphics();

        drawListener = new DrawListener(graphics, list);

        mainPanel.addMouseListener(drawListener);
        mainPanel.addMouseMotionListener(drawListener);
    }


    public void reset() {
        operandListener.reset();
        drawListener.reset();
        result.setText("识别结果 :");
    }

    public void setDrawType(DrawType drawType) {
        drawListener.setCommand(drawType);
        reset();
    }

    public int getStrokesNum() {
        return drawListener.getStrokesNum();
    }

    public void setStrokesNum(int strokesNum) {
        drawListener.setStrokesNum(strokesNum);
    }

    public void setIdentifyResult(String labelText) {
        result.setText(labelText);
    }
}

class MenuListener extends MouseAdapter {
    public void mouseEntered(MouseEvent m) {
        ((JMenu) m.getSource()).setForeground(Color.black);
        ((JMenu) m.getSource()).setOpaque(true);
    }

    public void mouseExited(MouseEvent me) {
        ((JMenu) me.getSource()).setForeground(null);
        ((JMenu) me.getSource()).setOpaque(false);
    }
}
