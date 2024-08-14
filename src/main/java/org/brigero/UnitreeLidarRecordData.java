package org.brigero;

import brigero.UnitreeLidar4Java;
import org.brigero.recording.UnitreeLidarRecorder;

import java.io.IOException;

public class UnitreeLidarRecordData {
    static boolean isRunning = true;

    public static void main(String[] args) throws InterruptedException, IOException {

        if (args == null || args.length < 2) {
            System.out.println("No arguments provided");
            return;
        }

        String fileName = args[0];
        System.out.println("File name: " + fileName);
        String lidarPath = args[1];
        System.out.println("Lidar path: " + lidarPath);
        UnitreeLidarRecorder unitreeLidarInstance = new UnitreeLidarRecorder(fileName);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            isRunning = false;
            unitreeLidarInstance.setState(UnitreeLidar4Java.UnitreeLidar4JavaStates.STANDBY);
        }));

        unitreeLidarInstance.setState(UnitreeLidar4Java.UnitreeLidar4JavaStates.NORMAL);

        unitreeLidarInstance.init(lidarPath);

        while (isRunning) {
            unitreeLidarInstance.writeData(unitreeLidarInstance.getCurMessageEnum());
            Thread.sleep(0, 500);
        }
    }
}