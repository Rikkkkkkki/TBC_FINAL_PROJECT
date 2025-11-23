package ge.tbc.testautomation.playwright.util;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.playwright.data.Constants;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {
    static {
        File dir = new File(Constants.SCREENSHOT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("Created screenshots directory: " + dir.getAbsolutePath());
        }
    }

    public static void takeScreenshot(Page page, String name) {
        try {
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = Constants.SCREENSHOT_DIR + "/" + name + "_" + timestamp + ".png";

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(fileName))
                    .setFullPage(true));

            System.out.println("Screenshot saved: " + fileName);
        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
        }
    }
}

