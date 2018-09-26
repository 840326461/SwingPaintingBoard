import javax.swing.filechooser.FileFilter;
import java.io.File;

public class DrawBoardFilter extends FileFilter {
    private String extention;
    private String description;

    public DrawBoardFilter() {
        extention = "drawboard";
        description = "drawboard(*.drawboard)";
    }

    @Override
    public boolean accept(File f) {
        String fileName = f.getName();
        if (fileName.contains(".")) {
            String[] sep = fileName.split("\\.");
            if (sep.length == 0)
                return false;
            return sep[sep.length - 1].equals(extention);
        }
        return false;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
