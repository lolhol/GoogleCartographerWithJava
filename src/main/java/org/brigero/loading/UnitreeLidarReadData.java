package org.brigero.loading;

import org.brigero.loading.util.OnUnreadabilityDataReceive;
import org.brigero.loading.util.UnitreeLidarReadingData;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class UnitreeLidarReadData {
    public static void main(String[] args) throws IOException {
        if (args == null || args.length < 2) {
            System.out.println("No arguments provided");
            return;
        }

        System.out.println(Arrays.toString(args));
        UnitreeLidarReadingData unitreeLidarReadingData = new UnitreeLidarReadingData(new File(args[0]), new OnUnreadabilityDataReceive() {
            @Override
            public void onPointCloudData(double[][] cloud, double timeStamp) {
                System.out.println(cloud.length);
            }

            @Override
            public void onIMUData(double[] angular_velocity, double[] linear_acceleration, double[] quaternion, double timeStamp) {
                System.out.println(angular_velocity.length);
            }
        },
        Long.parseLong(args[1]));

        unitreeLidarReadingData.start();
    }
}
