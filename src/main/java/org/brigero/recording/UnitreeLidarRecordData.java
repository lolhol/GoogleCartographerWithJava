package org.brigero.recording;

//import brigero.UnitreeLidar4Java;
import brigero.UnitreeLidar4Java;

import java.io.IOException;
import java.util.Arrays;

public class UnitreeLidarRecordData {
    static boolean isRunning = true;

    public static void main(String[] args) throws InterruptedException, IOException {
        if (args == null || args.length < 2) {
            System.out.println("No arguments provided");
            return;
        }

        System.out.println(Arrays.toString(args));

        String fileName = "";
        String lidarPath = "";

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-f")) {
                fileName = args[i + 1];
            } else if (args[i].equals("-lp")) {
                lidarPath = args[i + 1];
            }
        }

        if (fileName.isEmpty() || lidarPath.isEmpty()) {
            System.out.println("No arguments provided");
            return;
        }

        UnitreeLidarRecorder unitreeLidarInstance = new UnitreeLidarRecorder(fileName, lidarPath);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            isRunning = false;
            unitreeLidarInstance.setState(UnitreeLidar4Java.UnitreeLidar4JavaStates.STANDBY);
        }));

        unitreeLidarInstance.setState(UnitreeLidar4Java.UnitreeLidar4JavaStates.NORMAL);

        while (isRunning) {
            unitreeLidarInstance.writeData(unitreeLidarInstance.getCurMessageEnum());
            Thread.sleep(0, 500);
        }
    }
}